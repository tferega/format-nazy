package hr.element.fn.parsers
import hr.element.fn.Imports._

import scala.util.parsing.combinator.Parsers




object ByteParser extends Parsers {
  type Elem = Option[Byte]
  type ElemPredicate = (Elem) => Boolean


  // Parser predicate generator functions
  def eqFun(l: Seq[Byte]): ElemPredicate =
    (e: Elem) => e match {
      case Some(x) => l.contains(e.get)
      case None => false
    }
  def neFun(l: Seq[Byte]): ElemPredicate =
    (e: Elem) => e match {
      case Some(x) => !l.contains(e.get)
      case None => false
    }


  // Parser generator functions
  def getElemOne[T <: BaseData](b: T): Parser[T] =
    acceptSeq(b.v map(Some(_))) ^^ { _ => b }

  def getElemOr[T <: BaseData](bl: Seq[T]): Parser[T] =
    bl.map(getElemOne).reduceLeft(_ | _)

  def getElemCustom(description: String, bl: Seq[BaseData], predicateGenerator: (Seq[Byte]) => ElemPredicate): Parser[Elem] =
    elem(description, predicateGenerator(bl.flatMap(_.v)))


  // Elements
  private def elemEOF: Parser[Elem] = elem("EOF elem", _ == None)
  private def elemAny: Parser[Elem] = getElemCustom("any elem", C.NewlineList.values.toSeq, neFun _)


  // Element Lists
  private def elemListNewline: Parser[LineBreakData] = getElemOr(C.NewlineList.values.toSeq)


  // Sequences
  private def seqByte: Parser[List[Elem]] = rep(elemAny)
  private def seqLine: Parser[List[Line]] = rep(breakLine)


  // Compositions
  lazy val lineList: Parser[List[Line]] =
    seqLine ~ opt(endline) <~ elemEOF ^^ (e =>
      e._1 ::: e._2.toList)
  lazy val breakLine: Parser[Line] =
    seqByte ~ break ^^ (e =>
      new Line(e._1.map(_.get), Some(e._2)))
  lazy val endline: Parser[Line] =
    seqByte <~ elemEOF ^^ (e =>
      new Line(e.map(_.get), None))
  lazy val break: Parser[LineBreakData] =
    elemListNewline



  // Entry functions
  def parse(br: ByteReader): Option[List[Line]] = {
    try {
      val r = lineList(br)
      r match {
        case Success(result, _) => Some(result)
        case NoSuccess(msg, _) => None
      }
    } catch {
      case t: Throwable => None
    }
  }


  def parse(data: Array[Byte]): Option[Document] = {
    val br = new ByteReader(data)
    parse(br).map(new MemoryDocument(getUID("MemoryDocument"), _))
  }


  def parse(file: File): Option[FileDocument] = {
    val data = FileUtils.readFileToByteArray(file)
    val br = new ByteReader(data)
    parse(br).map(new FileDocument(file, _))
  }


  def parse(path: String): Option[FileDocument] = {
    val file = new File(path)
    parse(file)
  }



  // Helpers
  private def getUID(prefix: String) =
    "%s-%X".format(prefix, System.currentTimeMillis)
}
