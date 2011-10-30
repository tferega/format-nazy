package hr.element.fn.main
import hr.element.fn.Imports._

import java.nio.ByteBuffer
import java.nio.charset.{ Charset, CharsetDecoder }



object EncodingInfraction {
  val emptySeq = Seq.empty[EncodingInfraction]
}

class EncodingInfraction(val documentShortName: String, val documentLongName: String, val linNum: Int, val line: String) extends LineInfraction {
  val description = "Line is not a valid UTF-8 document!"
  val level = Level.Error
}

class CharacterInfraction(val documentShortName: String, val documentLongName: String, val linNum: Int, val line: String) extends LineInfraction {
  val description = "Invalid character!"
  val level = Level.Error
}



class ScrutinizatorGenerator(documentShortName: String, documentLongName: String) {
  val Encoding = "UTF-8"
  val Decoder = Charset.availableCharsets.get(Encoding)

  lazy val utf8 = new LineScrutinizator(utf8Fun)

  private lazy val utf8Fun: ((Line) => Seq[EncodingInfraction]) = (l: Line) => {
    try {
      val decoder = Decoder.newDecoder
      decoder.decode(ByteBuffer.wrap(l.body.toArray));
      EncodingInfraction.emptySeq
    } catch  {
      case t: Throwable =>
        Seq(new EncodingInfraction(documentShortName, documentLongName, 0, l.getBody))
    }
  }

  private lazy val character: ((Line) => Seq[CharacterInfraction]) = (l: Line) => {
      
    Seq.empty
  }
}