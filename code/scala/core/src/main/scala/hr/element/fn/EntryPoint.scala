package hr.element.fn
import hr.element.fn.config._

import hr.element.fn.main.{ Document, FileDocument, FormatNazy, Report, ScrutinizatorGenerator }
import hr.element.fn.parsers.ByteParser
import hr.element.fn.util.ByteReader

import java.io.File

import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils



object EntryPoint {
  import scala.collection.JavaConversions._
  def getAllFiles(folder: File): Array[String] =
    FileUtils.listFiles(
      folder,
      new org.apache.commons.io.filefilter.RegexFileFilter("^(.*?)"),
      org.apache.commons.io.filefilter.DirectoryFileFilter.DIRECTORY
    ).toSeq.toArray.map(_.getPath)


  def getExtCode(infractionCount: Int) =
    infractionCount match {
      case 0 => 0
      case _ => 1
    }


  def main(args: Array[String]) {

    val filenameList =
//      args
//      Seq("/home/huitz/code/bmw/code/server/scala/continuous-compilation.bat")
//      Seq("/home/huitz/test1.txt", "/home/huitz/Desktop/client-sections.png")
//      getAllFiles(new File("/home/huitz/code/bmw"))
      getAllFiles(new File("/home/huitz/Desktop/Work/Files"))

    println("Format nazy starting...")

    val s = System.currentTimeMillis
    val infractionCount = runFilenameList(filenameList)
    val e = System.currentTimeMillis

    println
    println("Running time [ms]: "+ (e-s))
    println("Total number of problems [kilofails]: "+ infractionCount)
    println("FormatNazy finished successfully")

    sys.exit(getExtCode(infractionCount))
  }




  def runFilenameList(filenameList: Seq[String]): Int = {
    val infractionCountList = filenameList.par.flatMap(runFilename)
    (infractionCountList /:\ 0)(_ + _)
  }


  def tryRunFilename(filename: String): Option[Int] = {
    try {
      runFilename(filename)
    } catch {
      case t: Throwable =>
        println("An error occured while processing %s (%s)".format(filename, t.toString))
        sys.exit(2)
    }
  }


  def runFilename(filename: String): Option[Int] = {
//    println("%03d: %s".format(Thread.currentThread.getId, filename))
    val ext = FilenameUtils.getExtension(filename)
    val scOpt = C.ScrutinizatorConfigs.get(ext)

    scOpt.map { sc =>
      val d = parseDocument(filename)
      runDocument(d, sc)
    }
  }


  def parseDocument(filename: String): FileDocument = {
    val file       = new File(filename)
    val byteArray  = FileUtils.readFileToByteArray(file)
    val byteReader = new ByteReader(byteArray)
    val lineList   = ByteParser.parse(byteReader)
    new FileDocument(file, lineList)
  }


  def runDocument(d: Document, sc: ScrutinizatorConfig): Int = {
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
