package hr.element.fn.main

import hr.element.fn.parsers.Line



object SC {
  type L = SC[Line]
  type D = SC[Document]

  def init[B](r: B): SC[B] = new SC(r, Seq.empty[InfractionBase])
}

class SC[B](val body: B, val infractionList: Seq[InfractionBase]) {
  def apply(newSC: SC[B]) =
    new SC(newSC.body, infractionList ++ newSC.infractionList)
}



sealed trait ScrutinizatorLike
abstract class ScrutinizatorBase[B](scrutinizator: (B) => Seq[InfractionBase]) extends ScrutinizatorLike {
  def apply(in: SC[B]): SC[B] = {
    val newInfractionList = scrutinizator(in.body)
    new SC(in.body, in.infractionList ++ newInfractionList)
  }
}

class LineScrutinizator(val scrutinizator: (Line) => Seq[InfractionBase]) extends ScrutinizatorBase(scrutinizator)
class DocumentScrutinizator(val scrutinizator: (Document) => Seq[InfractionBase]) extends ScrutinizatorBase(scrutinizator)



object FormatNazy {
  private def scIdentity[B]: SC[B] => SC[B] = (sc) => sc
  private def combineScrutinizators[B](scrutinizatorList: Seq[ScrutinizatorBase[B]]): SC[B] => SC[B] = {
    scrutinizatorList
      .map(_.apply _)
      .foldLeft(scIdentity[B]) { _ andThen _ }
  }


  private def scrutinizeLine(line: Line, scrutinizatorList: Seq[LineScrutinizator]): Seq[InfractionBase] =
    combineScrutinizators(scrutinizatorList)(SC.init(line))
      .infractionList

  private def scrutinizeDocument(document: Document, scrutinizatorList: Seq[DocumentScrutinizator]): Seq[InfractionBase] =
    combineScrutinizators(scrutinizatorList)(SC.init(document))
      .infractionList
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


  private def processLine(line: Line): Seq[InfractionBase] =
    scrutinizeLine(line, lineScrutinizatorList)

  private def processLineList(lineList: Seq[Line]): Seq[InfractionBase] =
    lineList flatMap processLine

  private def processDocument(document: Document): Seq[InfractionBase] =
    scrutinizeDocument(document, documentScrutinizatorList)

  private def scrutinizeAll(document: Document): Seq[InfractionBase] = {
    val dI = processDocument(document)
    val lI = processLineList(document.body)
    dI ++ lI
  }


  def scrutinize(document: Document) = {
    val infractionList = scrutinizeAll(document)
    new Report(document, infractionList)
  }
}
