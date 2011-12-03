package hr.element.fn.main

import hr.element.fn.parsers.Line

import java.io.File
import java.util.UUID

import org.apache.commons.io.FileUtils



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

  def write(file: File) {
    FileUtils.writeByteArrayToFile(file, toByteArray)
  }

  def overwrite {
    write(location)
  }
}