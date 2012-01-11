package hr.element.fn.main
import hr.element.fn.Imports._



sealed trait Level {
  import Level._

  val severity: Int
  lazy val description = getDescription(severity)
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



sealed abstract class InfractionType[T]
object InfractionType {
  import hr.element.fn.main.{ Document => D }
  import hr.element.fn.parsers.{ Line => L }
  import scala.{ Byte => B }
  implicit object Document extends InfractionType[D]
  implicit object Line extends InfractionType[L]
  implicit object Byte extends InfractionType[B]
}


abstract class InfractionBase[T: InfractionType] {
  val description: String

  val level: Level

  def quickReport(name: String): String
  def fullReport(name: String): String
}



abstract class DocumentInfraction extends InfractionBase[Document] {
  def quickReport(name: String) = "%s in document %s: %s".format(level.description, name, description)
  def fullReport(name: String)  = "%s in document %s: %s".format(level.description, name, description)
}



abstract class LineInfraction extends InfractionBase[Line] {
  val rowNum: Int
  val line: String

  def quickReport(name: String) = "%s on line %d in document %s: %s".format(level.description, rowNum, name, description)
  def fullReport(name: String)  = "%s %s:%d: %s\n%1$s %s".format(level.description, name, rowNum, description, line)
}



abstract class ByteInfraction extends InfractionBase[Line] {
  val rowNum: Int
  val colNum: Int
  val line: String

  private def getReportPadding = " " * colNum
  def quickReport(name: String) = "%s on line %d at column %d in document %s: %s".format(level.description, rowNum, colNum, name, description)
  def fullReport(name: String)  = "%s %s:%d: %s\n%1$s %s\n%1$s %s^".format(level.description, name, rowNum, description, line, getReportPadding)
}
