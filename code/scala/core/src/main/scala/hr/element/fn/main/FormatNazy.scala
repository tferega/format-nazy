package hr.element.fn.main

import hr.element.fn.parsers.Line



object SC {
  def init[B: InfractionType](r: B): SC[B] = new SC(r, Seq.empty[InfractionBase[B]])
}

class SC[B: InfractionType](val body: B, val infractionList: Seq[InfractionBase[B]]) {
  def apply(newSC: SC[B]) =
    new SC(newSC.body, infractionList ++ newSC.infractionList)
}



sealed trait ScrutinizatorLike
abstract class ScrutinizatorBase[B: InfractionType](scrutinizator: (B) => Seq[InfractionBase[B]]) extends ScrutinizatorLike {
  def apply(in: SC[B]): SC[B] = {
    val newInfractionList = scrutinizator(in.body)
    new SC(in.body, in.infractionList ++ newInfractionList)
  }
}

class LineScrutinizator(val scrutinizator: (Line) => Seq[InfractionBase[Line]]) extends ScrutinizatorBase(scrutinizator)
class DocumentScrutinizator(val scrutinizator: (Document) => Seq[InfractionBase[Document]]) extends ScrutinizatorBase(scrutinizator)



object FormatNazy {
  private def scIdentity[B: InfractionType]: SC[B] => SC[B] = (sc) => sc
  private def combineScrutinizators[B: InfractionType](scrutinizatorList: Seq[ScrutinizatorBase[B]]): SC[B] => SC[B] = {
    scrutinizatorList
      .map(_.apply _)
      .foldLeft(scIdentity[B]) { _ andThen _ }
  }


  private def scrutinizeLine(line: Line, scrutinizatorList: Seq[LineScrutinizator]): Seq[InfractionBase[Line]] =
    combineScrutinizators(scrutinizatorList).
      apply(SC.init(line)).
      infractionList

  private def scrutinizeDocument(document: Document, scrutinizatorList: Seq[DocumentScrutinizator]): Seq[InfractionBase[Document]] =
    combineScrutinizators(scrutinizatorList).
      apply(SC.init(document)).
      infractionList
}



class FormatNazy(scrutinizatorList: ScrutinizatorLike*) {
  import FormatNazy._

  val lineScrutinizatorList: Seq[LineScrutinizator] =
    scrutinizatorList collect {
      case x: LineScrutinizator => x
    }

  val documentScrutinizatorList: Seq[DocumentScrutinizator] =
    scrutinizatorList collect {
      case x: DocumentScrutinizator => x
    }


  private def processLine(line: Line): Seq[InfractionBase[Line]] =
    scrutinizeLine(line, lineScrutinizatorList)

  private def processLineList(lineList: Seq[Line]): Seq[InfractionBase[Line]] =
    lineList flatMap processLine

  private def processDocument(document: Document): Seq[InfractionBase[Document]] =
    scrutinizeDocument(document, documentScrutinizatorList)

  private def scrutinizeAll(document: Document): Seq[InfractionBase[_]] = {
    val dI = processDocument(document)
    val lI = processLineList(document.body)
    dI ++ lI
  }


  def scrutinize(document: Document) = {
    val infractionList = scrutinizeAll(document)
    new Report(document, infractionList)
  }
}
