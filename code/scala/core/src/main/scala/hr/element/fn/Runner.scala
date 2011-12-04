package hr.element.fn
import hr.element.fn.Imports._

import hr.element.fn.main.{ Document, FileDocument, FormatNazy, ScrutinizatorGenerator }
import hr.element.fn.parsers.ByteParser
import hr.element.fn.util.ByteReader

import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils



object Runner {
  def runFilenameList(filenameList: Seq[String]): Int = {
    val infractionCountList = filenameList.par.flatMap(runFilename)
    (infractionCountList /:\ 0)(_ + _)
  }


  private def tryRunFilename(filename: String): Option[Int] = {
    try {
      runFilename(filename)
    } catch {
      case t: Throwable =>
        println("An error occured while processing %s (%s)".format(filename, t.toString))
        sys.exit(2)
    }
  }


  private def runFilename(filename: String): Option[Int] = {
    val ext = FilenameUtils.getExtension(filename)
    val scOpt = C.ScrutinizatorConfigs.get(ext)

    scOpt.map { sc =>
      val d = parseDocument(filename)
      runDocument(d, sc)
    }
  }


  private def parseDocument(filename: String): FileDocument = {
    val file       = new File(filename)
    val byteArray  = FileUtils.readFileToByteArray(file)
    val byteReader = new ByteReader(byteArray)
    val lineList   = (new ByteParser(byteReader)).parse
    new FileDocument(file, lineList)
  }


  private def runDocument(d: Document, sc: ScrutinizatorConfig): Int = {
    val sg = new ScrutinizatorGenerator(sc)
    val fn = new FormatNazy(sg.utf8, sg.character, sg.newline)
    val r  = fn.scrutinize(d)

    if (r.hasInfractions) {
      println(r.fullReport)
      println
//      sys.exit(1)
    }

    r.infractionList.size
  }
}
