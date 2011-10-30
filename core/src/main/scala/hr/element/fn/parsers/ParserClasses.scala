package hr.element.fn.parsers
import hr.element.fn.Imports._



class Line(val body: Seq[Byte], val lineBreak: Option[LineBreakData]) {
  lazy val getBody: String = new String(body.toArray, "UTF-8")

  override def toString =
    "%s[%s]".format(
        getBody,
        lineBreak.getOrElse("EOF"))

  lazy val byteCount = body.length + lineBreak.map(_.length).getOrElse(0)
  lazy val toByteArray: Array[Byte] =
    body.toArray ++ lineBreak.map(_.v.toArray).getOrElse(Array())
}


abstract class Document(val body: Seq[Line]) {
  val name: String
  val shortName: String

  val lineCount = body.length
  lazy val byteCount = body.map(_.byteCount).sum

  lazy val toByteArray = body.flatMap(_.toByteArray).toArray
  override def toString = body.mkString("\n")

}


class MemoryDocument(val name: String, body: Seq[Line]) extends Document(body) {
  val shortName = name
}


class FileDocument(val location: File, body: Seq[Line]) extends Document(body) {
  val name = location.getAbsolutePath
  val shortName = location.getName

  def write(file: File) {
    FileUtils.writeByteArrayToFile(file, toByteArray)
  }

  def overwrite {
    write(location)
  }
}
