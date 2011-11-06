package hr.element.fn
import hr.element.fn.Imports._

import hr.element.fn.main.{ ScrutinizatorGenerator, FormatNazy }
import hr.element.fn.parsers.{ ByteParser, Document }



sealed trait Error {
  val num: Int
  val msg: String
  val arg: String

  def exit: Nothing = {
    println(msg format arg)
    sys.exit(num)
  }
}
object Error
{
  case class Unknown(val err: String, val arg: String) extends Error { val num = 1; val msg = "An unknown error occured while processing %%s (%s)" format err }
  case class InvalidFile(val arg: String) extends Error { val num = 1; val msg = "File does not exists or cannot be read: %s" }
  case class ParsingFailed(val arg: String) extends Error { val num = 2; val msg = "Error while parsing file: %s" }
}



object EntryPoint {
  def main(args: Array[String]) {
    println("Format nazy starting...")
    val retList = run(args)

    val problemList = retList.filter(_.hasInfractions)

    if (problemList.isEmpty) {
      println("No problems found")
    } else {
      problemList foreach { r =>
        println("Document: "+ r.document.name)
        println(r.fullReport)
      }
    }

    println("FormatNazy finished successfully")
    sys.exit(0)
  }


  def run(args: Array[String]): Seq[Report] = {
    args.map(runFilename)
  }


  def runFilename(filename: String): Report = {
    val file = new File(filename)
    if (!file.exists() || !file.canRead()) {
      Error.InvalidFile(filename).exit
    } else {
      try {
        runFile(file)
      } catch {
        case t: Throwable =>
          Error.Unknown(t.toString, filename).exit
      }
    }
  }


  def runFile(file: File): Report = {
    val dOpt = ByteParser.parse(file)
    dOpt match {
      case Some(d) =>
        runDocument(d)
      case None =>
        Error.ParsingFailed(file.toString).exit
    }
  }


  def runDocument(d: Document): Report = {
    val sg = new ScrutinizatorGenerator(d.shortName, d.name)
    val fn = new FormatNazy(sg.utf8, sg.character, sg.newline)
    fn.scrutinize(d)
  }
}
