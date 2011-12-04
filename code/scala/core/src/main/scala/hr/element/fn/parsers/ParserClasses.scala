package hr.element.fn.parsers



class ParsingFailedException(message: String) extends Exception(message)


object Bytes {
  val LF: Byte = 0x0A
  val CR: Byte = 0x0D
}


sealed trait Newline {
  val bytes: IndexedSeq[Byte]
}
object Newline {
  case object EOF extends Newline { val bytes = IndexedSeq()                   }
  case object NIX extends Newline { val bytes = IndexedSeq(Bytes.LF)           }
  case object WIN extends Newline { val bytes = IndexedSeq(Bytes.CR, Bytes.LF) }
  case object MAC extends Newline { val bytes = IndexedSeq(Bytes.CR)           }
}



class Line(val row: Int, val body: IndexedSeq[Byte], val newline: Newline) {
  lazy val strBody      = new String(body.toArray, "UTF-8")
  lazy val byteCount    = body.length + newline.bytes.length
  lazy val toByteArray  = body.toArray ++ newline.bytes
  override def toString = "%s[%s]".format(strBody, newline.toString)
}
