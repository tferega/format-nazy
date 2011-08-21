package hr.element.fn.parsers

import org.apache.commons.io.FileUtils
import java.io.File

import hr.element.fn.proxies._
import ByteParticles._
import LineBreakParticles._
import Implicits._



class Line(val body: List[Byte], val lineBreak: Option[LineBreakParticle]) {
  override def toString =
    "%s[%s]".format(
        body.map(_.toChar).mkString,
        lineBreak.getOrElse(EOF).toString)

  val byteCount = body.length + lineBreak.map(_.length).getOrElse(0)

  def toByteArray: Array[Byte] =
    body.toArray ++ lineBreak.map(_.toArray).getOrElse(Array())
}

class Document(val body: List[Line]) {
  val lineCount = body.length
  lazy val byteCount = body.map(_.byteCount).sum

  def toByteArray = body.flatMap(_.toByteArray).toArray
  override def toString = body.mkString("\n")

}


class FileDocument(val location: File, body: List[Line]) extends Document(body) {
  def write(file: File) {
    FileUtils.writeByteArrayToFile(file, toByteArray)
  }

  def overwrite {
    write(location)
  }
}