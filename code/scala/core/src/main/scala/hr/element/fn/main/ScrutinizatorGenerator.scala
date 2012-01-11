package hr.element.fn.main
import hr.element.fn.Imports._

import java.nio.ByteBuffer
import java.nio.charset.{ Charset, CharsetDecoder }



object EncodingInfraction {
  val emptySeq = Seq.empty[EncodingInfraction]
}

class EncodingInfraction(val rowNum: Int, val line: String) extends LineInfraction {
  val description = "Line is not a valid UTF-8 document!"
  val level = Level.Error
}

class CharacterInfraction(val b: Byte, val rowNum: Int, val colNum: Int, val line: String) extends ByteInfraction {
  val description = "Invalid character (0x%02X)!" format b
  val level = Level.Error
}

class NewlineInfraction(val newline: Newline, val rowNum: Int, val line: String) extends LineInfraction {
  val description = "Invalid newline (%s)!" format newline
  val level = Level.Error
}



class ScrutinizatorGenerator(sc: ScrutinizatorConfig) {
  type ScrutinizatorFunction[T, I] = T => Seq[InfractionBase[I]]
  type DocumentSF = ScrutinizatorFunction[Document, Document]
  type LineSF = ScrutinizatorFunction[Line, Line]

  val Encoding = "UTF-8"
  val Decoder = Charset.availableCharsets.get(Encoding)



  lazy val utf8      = new LineScrutinizator(utf8Fun)
  lazy val character = new LineScrutinizator(characterFun)
  lazy val newline   = new LineScrutinizator(newlineFun)



  private lazy val utf8Fun: LineSF = (l) => {
    try {
      val decoder = Decoder.newDecoder
      decoder.decode(ByteBuffer.wrap(l.body.toArray));
      EncodingInfraction.emptySeq
    } catch  {
      case t: Throwable =>
        Seq(new EncodingInfraction(l.row, l.strBody))
    }
  }


  private lazy val characterFun: LineSF = (l) => {
    for {
      col <- 0 until l.body.size
      b = l.body(col)
      if !sc.characterPredicate(b)
    } yield (new CharacterInfraction(b, l.row, col, l.strBody))
  }


  private lazy val newlineFun: LineSF = (l) => {
    if (l.newline == Newline.EOF) {
      Seq.empty
    } else {
      sc.newlinePredicate(l.newline) match {
        case false => Seq(new NewlineInfraction(l.newline, l.row, l.strBody))
        case true  => Seq.empty
      }
    }
  }
}
