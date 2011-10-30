package hr.element.fn.main

import hr.element.fn.parsers._



class Report(document: Document, infractionList: Seq[InfractionBase]) {
  lazy val hasInfractions  = !infractionList.isEmpty
  lazy val highestLevel = infractionList.map(_.level.severity).max

  private lazy val baseLevelDescription = Level.getDescription(highestLevel)
  private lazy val baseQuickReport = infractionList.map(_.quickReport)
  private lazy val baseFullReport = infractionList.map(_.fullReport)

  lazy val summary = infractionList.size match {
    case 0 => "%s No problems found!".format(Level.getDescription(0))
    case x => "%s %d problems found!".format(baseLevelDescription, x)
  }

  lazy val quickReport =
    "%s\n%s".format(baseQuickReport, summary)
  lazy val fullReport =
    "%s\n%s".format(baseFullReport, summary)
}
