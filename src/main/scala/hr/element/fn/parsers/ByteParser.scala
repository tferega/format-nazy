package hr.element.fn.parsers

import scala.util.parsing.combinator._
import hr.element.util.ByteReader

import hr.element.fn.proxies._
import ByteParticles._
import LineBreakParticles._
import Implicits._

import java.io.File
import org.apache.commons.io.FileUtils





object ByteParser extends Parsers {
  type Elem = Option[Byte]

  // Elements
  private def elemAny: Parser[Elem] = elem("anyElem", e => (e != Some(LF)) & (e != Some(CR)) & (e != None))
  private def elemLF:  Parser[Elem] = elem("lfElem",  _ == Some(LF)) // \n
  private def elemCR:  Parser[Elem] = elem("crElem",  _ == Some(CR)) // \r
  private def elemEOF: Parser[Elem] = elem("anyElem", _ == None)

  // Blocks
  private def blockWindowsNL: Parser[Windows.type] = elemCR ~ elemLF ^^ (_ => Windows)
  private def blockUnixNL:    Parser[Unix.type]    = elemLF          ^^ (_ => Unix)
  private def blockMacNL:     Parser[Mac.type]     = elemCR          ^^ (_ => Mac)
  private def blockEOF:       Parser[EOF.type]     = elemEOF         ^^ (_ => EOF)

  // Sequences
  private def seqByte: Parser[List[Option[Byte]]] = rep(elemAny)
  private def seqLine: Parser[List[Line]] = rep(breakLine)

  // Compositions
  private def lineList: Parser[List[Line]] =
    seqLine ~ opt(endline) ~ blockEOF ^^ (e =>
      e._1._1 ::: e._1._2.toList)
  private def breakLine: Parser[Line] =
    seqByte ~ break ^^ (e =>
      new Line(e._1.map(_.get), Some(e._2)))
  private def endline: Parser[Line] =
    seqByte ~ blockEOF ^^ (e =>
      new Line(e._1.map(_.get), None))
  private def break: Parser[LineBreakParticle] =
    blockWindowsNL | blockUnixNL | blockMacNL



  def parse(br: ByteReader): Option[List[Line]] = {
    try {
      val r = lineList(br)
      r.successful match {
        case true  => Some(r.get)
        case false => None
      }
    } catch {
      case t: Throwable => None
    }
  }


  def parseArray(data: Array[Byte]): Option[Document] = {
    val br = new ByteReader(data)
    parse(br).map(new Document(_))
  }


  def parseFile(file: File): Option[FileDocument] = {
    val data = FileUtils.readFileToByteArray(file)
    val br = new ByteReader(data)
    parse(br).map(new FileDocument(file, _))
  }


  def parseFile(path: String): Option[FileDocument] = {
    val file = new File(path)
    parseFile(file)
  }
}
