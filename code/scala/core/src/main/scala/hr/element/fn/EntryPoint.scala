package hr.element.fn

import hr.element.fn.main.{ Document, FileDocument, FormatNazy, Report, ScrutinizatorGenerator }
import hr.element.fn.parsers.ByteParser
import hr.element.fn.util.ByteReader

import java.io.File

import org.apache.commons.io.FileUtils



object EntryPoint {
  def main(args: Array[String]) {
    println("Format nazy starting...")

    val reportList  = run(Array("/home/huitz/test1.txt", "/home/huitz/test2.txt"))
    val problemList = reportList.filter(_.hasInfractions)
    printReportList(problemList)

    println("FormatNazy finished successfully")

    val extCode = problemList.isEmpty match {
      case true  => 0
      case false => 1
    }
    sys.exit(extCode)
  }




  def run(args: Array[String]): Seq[Report] = {
    args.par.map(runFilename).seq
  }


  def runFilename(filename: String): Report = {
    try {
      val file = new File(filename)
      runFile(file)
    } catch {
      case t: Throwable =>
        println("An error occured while processing %s (%s)".format(filename, t.toString))
        sys.exit(2)
    }
  }


  def runFile(file: File): Report = {
    val d = parseDocument(file)
    runDocument(d)
  }


  def parseDocument(file: File): FileDocument = {
    val byteArray  = FileUtils.readFileToByteArray(file)
    val byteReader = new ByteReader(byteArray)
    val lineList   = ByteParser.parse(byteReader)
    new FileDocument(file, lineList)
  }


  def runDocument(d: Document): Report = {
    val sg = new ScrutinizatorGenerator()
    val fn = new FormatNazy(sg.utf8, sg.character, sg.newline)
    fn.scrutinize(d)
  }


  def printReportList(reportList: Seq[Report]) {
    reportList.isEmpty match {
      case true =>
        println("No problems found")
      case false =>
        println("########## FORMAT NAZY PROBLEM REOPRT START ##########")
        reportList foreach printReport
        println("########## FORMAT NAZY PROBLEM REOPRT END ##########")
    }
  }


  def printReport(report: Report) {
    println(report.fullReport)
  }
}
