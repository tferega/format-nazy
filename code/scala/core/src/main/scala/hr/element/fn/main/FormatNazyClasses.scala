package hr.element.fn.main
import hr.element.fn.Imports._

import java.util.UUID



abstract class Document(val body: Seq[Line]) {
  val name: String
  val shortName: String

  val lineCount = body.length
  lazy val byteCount = body.map(_.byteCount).sum

  lazy val toByteArray = body.flatMap(_.toByteArray).toArray
  override def toString = body.mkString("\n")

}



class MemoryDocument(val name: String = UUID.randomUUID.toString, body: Seq[Line]) extends Document(body) {
  val shortName = name
}



class FileDocument(val location: File, body: Seq[Line]) extends Document(body) {
  val name = location.getAbsolutePath
  val shortName = location.getName
}
