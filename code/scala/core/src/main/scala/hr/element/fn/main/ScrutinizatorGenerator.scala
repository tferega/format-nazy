package hr.element.fn.main

import hr.element.fn.parsers.Line
import hr.element.fn.parsers.Newline._
import java.nio.ByteBuffer
import java.nio.charset.{ Charset, CharsetDecoder }



object EncodingInfraction {
  val emptySeq = Seq.empty[EncodingInfraction]
}

class EncodingInfraction(val rowNum: Int, val line: String) extends LineInfraction {
  val description = "Line is not a valid UTF-8 document!"
  val level = Level.Error
}

class CharacterInfraction(val rowNum: Int, val line: String) extends LineInfraction {
  val description = "Invalid character!"
  val level = Level.Error
}

class NewlineInfraction(val rowNum: Int, val line: String) extends LineInfraction {
  val description = "Invalid newline!"
  val level = Level.Error
}



class ScrutinizatorGenerator() {
  val Encoding = "UTF-8"
  val Decoder = Charset.availableCharsets.get(Encoding)

  lazy val utf8      = new LineScrutinizator(utf8Fun)
  lazy val character = new LineScrutinizator(characterFun)
  lazy val newline   = new LineScrutinizator(newlineFun)

  private lazy val utf8Fun: ((Line) => Seq[EncodingInfraction]) = (l: Line) => {
    try {
      val decoder = Decoder.newDecoder
      decoder.decode(ByteBuffer.wrap(l.body.toArray));
      EncodingInfraction.emptySeq
    } catch  {
      case t: Throwable =>
        Seq(new EncodingInfraction(l.row, l.strBody))
    }
  }

  private lazy val characterFun: ((Line) => Seq[CharacterInfraction]) = (l: Line) => {
    l.body.collect {
      case x if x == '\t' => new CharacterInfraction(l.row, l.strBody)
    }
  }

  private lazy val newlineFun: ((Line) => Seq[NewlineInfraction]) = (l: Line) => {
    l.newline match {
      case MAC => Seq(new NewlineInfraction(l.row, l.strBody))
      case _ => Seq.empty
    }
  }
}
