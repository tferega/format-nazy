package debug

import java.io.File
import hr.element.fn.parsers._
import hr.element.util._

import java.io.File
import org.apache.commons.io.FileUtils

import hr.element.fn._

import hr.element.fn.main._



sealed trait Return {
  val num: Int
  val msg: String
  val reportOpt: Option[Report]

  def exit {
    println(msg)
    System.exit(num)
  }
}
object Return
{
  case class  Valid(report: Report) extends Return { val reportOpt = Some(report); val num = 0; val msg = "Successful" }
  case object InvalidArguments  extends Return { val reportOpt = None; val num = 1; val msg = "This program takes exactly one argument." }
  case object InvalidFile       extends Return { val reportOpt = None; val num = 2; val msg = "File not found." }
  case object ParsingFailed     extends Return { val reportOpt = None; val num = 3; val msg = "This program takes exactly one argument." }
}



object EntryPoint {
  def main(args: Array[String]) {
    println("Format nazy starting...")
    val ret = run(args)

    for (r <- ret.reportOpt if r.hasInfractions) {
      println(r.fullReport)
    }

    ret.exit
  }


  def run(args: Array[String]): Return = {
    args match {
      case Array(filename) =>
        runFilename(filename)
      case _ =>
        Return.InvalidArguments
    }
  }

  def runFilename(filename: String): Return = {
    val file = new File(filename)
    if (!file.exists() || !file.canRead()) {
      Return.InvalidFile
    } else {
      runFile(file)
    }
  }


  def runFile(file: File): Return = {
    val dOpt = ByteParser.parse(file)
    dOpt match {
      case Some(d) =>
        runDocument(d)
      case None =>
        Return.ParsingFailed
    }
  }


  def runDocument(d: Document): Return = {
    val sg = new ScrutinizatorGenerator(d.shortName, d.name)
    val fn = new FormatNazy(sg.utf8, sg.character, sg.newline)
    val r = fn.scrutinize(d)
    Return.Valid(r)
  }
}
