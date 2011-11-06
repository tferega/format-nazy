package hr.element.fn
import hr.element.fn.Imports._

import hr.element.fn.main.{ ScrutinizatorGenerator, FormatNazy }
import hr.element.fn.parsers.{ ByteParser, Document }



object EntryPoint {
  def main(args: Array[String]) {
    println("Format nazy starting...")
    val retList = run(args)

    val problemList = retList.filter(_.hasInfractions)

    val extCode = if (problemList.isEmpty) {
      println("No problems found")
      0
    } else {
      problemList foreach { r =>
        println; println;
        println("########################################")
        println("Document: "+ r.document.name)
        println(r.fullReport)
      }
      1
    }

    println("FormatNazy finished successfully")
    sys.exit(extCode)
  }


  def run(args: Array[String]): Seq[Report] = {
    args.map(runFilename)
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
    val dOpt = ByteParser.parse(file)
    dOpt match {
      case Some(d) =>
        runDocument(d)
      case None =>
        println("An error occured while parsing file %s".format(file.toString))
        sys.exit(3)
    }
  }


  def runDocument(d: Document): Report = {
    val sg = new ScrutinizatorGenerator(d.shortName, d.name)
    val fn = new FormatNazy(sg.utf8, sg.character, sg.newline)
    fn.scrutinize(d)
  }
}
