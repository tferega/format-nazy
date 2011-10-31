package hr.element.fn.main
import hr.element.fn.Imports._



sealed trait Level {
  import Level._

  val severity: Int
  val description = getDescription(severity)
}
object Level {
  def getDescription(severity: Int) = severity match {
    case 0 => "[valid]"
    case 1 => "[info] "
    case 2 => "[warn] "
    case 3 => "[error]"
  }

  case object Valid extends Level { val severity = 0 }
  case object Info  extends Level { val severity = 1 }
  case object Warn  extends Level { val severity = 2 }
  case object Error extends Level { val severity = 3 }
}



abstract class InfractionBase {
  val description: String
  val documentShortName: String
  val documentLongName: String

  val level: Level

  val quickReport: String
  val fullReport: String
}



abstract class DocumentInfraction extends InfractionBase {
  lazy val quickReport = "%s in document %s: %s".format(level.description, documentShortName, description)
  lazy val fullReport = "%s in document %s: %s".format(level.description, documentLongName, description)
}



abstract class LineInfraction extends InfractionBase {
  val linNum: Int
  val line: String

  lazy val quickReport = "%s on line %d in document %s: %s".format(level.description, linNum, documentShortName, description)
  lazy val fullReport = "%s %s:%d: %s\n%1$s %s".format(level.description, documentLongName, linNum, description, line)
}



abstract class ByteInfraction extends InfractionBase {
  val linNum: Int
  val colNum: Int
  val line: String

  private def getReportPadding = " "*(colNum-1)
  lazy val quickReport = "%s on line %d at column %d in document %s: %s".format(level.description, linNum, colNum, documentShortName, description)
  lazy val fullReport = "%s %s:%d: %s\n%1$s %s\n%1$s %s^".format(level.description, documentLongName, linNum, description, line, getReportPadding)
}
