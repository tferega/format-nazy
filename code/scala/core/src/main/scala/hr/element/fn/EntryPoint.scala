package hr.element.fn
import hr.element.fn.Imports._

import org.apache.commons.io.FileUtils



object EntryPoint {
  import scala.collection.JavaConversions._
  def getAllFiles(folder: File): Array[String] =
    FileUtils.listFiles(folder, null, true).toSeq.toArray.map(_.getPath)


  def getExtCode(infractionCount: Int) =
    infractionCount match {
      case 0 => 0
      case _ => 1
    }


  def main(args: Array[String]) {

    val filenameList =
      args
//      Seq("/home/huitz/test1.txt", "/home/huitz/test2.txt")
//      Seq("/home/huitz/test1.txt", "/home/huitz/Desktop/client-sections.png")
//      getAllFiles(new File("/home/huitz/code/bmw"))
//      getAllFiles(new File("/home/huitz/Desktop/Work/Files"))

    println("Format nazy starting...")

    val s = System.currentTimeMillis
    val infractionCount = Runner.runFilenameList(filenameList)
    val e = System.currentTimeMillis

    println
    println("Running time [ms]: "+ (e-s))
    println("Total number of problems [kilofails]: "+ infractionCount)
    println("FormatNazy finished successfully")

    sys.exit(getExtCode(infractionCount))
  }
}
