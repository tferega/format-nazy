package hr.element.fn

import hr.element.fn.parsers._
import hr.element.fn.proxies._
import ByteParticles._
import LineBreakParticles._
import Implicits._



object FormatNazy {
  private case class VerifyResult(line: Line, infractions: List[Infraction])
  private def verifyCharacters(oldResult: VerifyResult): VerifyResult =
    oldResult.line.body.contains(TAB) match {
      case true  =>
        val newBody: List[Byte] = oldResult.line.body.flatMap { e =>
            if (e == TAB) List[Byte](SPACE, SPACE)
            else List(e)
        }
        val newInfractions = InvalidCharacter(TAB) :: oldResult.infractions
        VerifyResult(new Line(newBody, oldResult.line.lineBreak), newInfractions)
      case false =>
        oldResult
    }
  private def verifyLineBreak(oldResult: VerifyResult): VerifyResult =
    oldResult.line.lineBreak match {
      case None          => oldResult
      case Some(Windows) => oldResult
      case Some(x)       =>
        val newInfractions = InvalidLineBreak(x) :: oldResult.infractions
        VerifyResult(new Line(oldResult.line.body, Some(Windows)), newInfractions)
    }
  private def verifyEncoding(oldResult: VerifyResult): VerifyResult =
    oldResult



  private def lineScrutinizer(line: Line) =
    (verifyCharacters _ andThen
        verifyLineBreak andThen
        verifyEncoding)(VerifyResult(line, List.empty))

  private def scrutinizeLine(num: Int, oldLine: Line): LineReport = {
    val result = lineScrutinizer(oldLine)
    LineReport(num, oldLine, result.line, result.infractions)
  }

  def scrutinize(oldDocument: FileDocument): Report = {
    val lineReports = oldDocument.body.zipWithIndex.map(e =>
      scrutinizeLine(e._2, e._1))
    val newDocument = new FileDocument(oldDocument.location, lineReports.map(_.newLine))
    Report(oldDocument, newDocument, lineReports)
  }
}