package hr.element.fn

import hr.element.fn.parsers._



case class LineReport(num: Int, oldLine: Line, newLine: Line, infractions: Seq[Infraction]) {
  val hasInfractions = !infractions.isEmpty

  override def toString =
    if (hasInfractions)
      "  Line %d [Infractions found]\n%s".format(num+1, infractions.mkString("\n"))
    else
      "  Line %d [No infractions]".format(num+1)
}

case class Report(oldDocument: FileDocument, newDocument: FileDocument, lineReports: Seq[LineReport]) {
  val documentName = oldDocument.location.getName
  val infractionLines = lineReports.filter(_.hasInfractions)
  val hasInfractions = !infractionLines.isEmpty

  override def toString =
    if (hasInfractions)
      "File %s [Infractions found]\n%s".format(documentName, infractionLines.mkString("\n"))
    else
      "File %s [No infractions]".format(documentName)
}
