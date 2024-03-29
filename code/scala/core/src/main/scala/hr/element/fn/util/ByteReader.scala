package hr.element.fn.util

import scala.util.parsing.input.{ Position, Reader }



// Based on work by Paul Phillips
// http://www.scala-lang.org/node/4693
case class ByteOffsetPosition(offset: Int) extends Position {
  final val line = 1
  def column = offset + 1
  def lineContents: String = ""
}


class ByteReader(val bytes: Array[Byte], override val offset: Int) extends Reader[Option[Byte]] {
  def this(reader: Reader[_]) = this(reader.source.toString.getBytes, 0)
  def this(bytes: Seq[Byte]) = this(bytes.toArray, 0)
  def this(str: String) = this(str.getBytes, 0)

  override def source = bytes map (_.toChar)

  override def first: Option[Byte] = if (offset < bytes.length) Some(bytes(offset)) else None
  def rest: ByteReader = if (offset < bytes.length) new ByteReader(bytes, offset + 1) else this
  def pos: Position = ByteOffsetPosition(offset)
  def atEnd = offset >= bytes.length

  def byteAt(n: Int) = bytes(n)
  def length = bytes.length - offset

  override def drop(n: Int): ByteReader = new ByteReader(bytes, offset + n)
  def take(n: Int): Seq[Byte] = bytes drop offset take n

  override def toString = "ByteReader(%d / %d)".format(offset, bytes.length)
}
