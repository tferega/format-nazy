package hr.element.fn.main
import hr.element.fn.config._

import hr.element.fn.parsers.{ Line, Newline }
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



class ScrutinizatorGenerator(sc: ScrutinizatorConfig) {
  type BasicScrutinizatorFunction[T] = T => Seq[InfractionBase[T]]
  type DocumentBSF = BasicScrutinizatorFunction[Document]
  type LineBSF = BasicScrutinizatorFunction[Line]
  type ByteBSF = BasicScrutinizatorFunction[Byte]

  val Encoding = "UTF-8"
  val Decoder = Charset.availableCharsets.get(Encoding)



  lazy val utf8      = new LineScrutinizator(utf8Fun)
  lazy val character = new LineScrutinizator(characterFun)
  lazy val newline   = new LineScrutinizator(newlineFun)



  private lazy val utf8Fun: LineBSF = (l) => {
    try {
      val decoder = Decoder.newDecoder
      decoder.decode(ByteBuffer.wrap(l.body.toArray));
      EncodingInfraction.emptySeq
    } catch  {
      case t: Throwable =>
        Seq(new EncodingInfraction(l.row, l.toString))
    }
  }


  private lazy val characterFun: LineBSF = (l) => {
    l.body.collect {
      case x if !sc.characterPredicate(x) => new CharacterInfraction(l.row, l.toString)
    }
  }


  private lazy val newlineFun: LineBSF = (l) => {
    if (l.newline == Newline.EOF) {
      Seq.empty
    } else {
      sc.newlinePredicate(l.newline) match {
        case false => Seq(new NewlineInfraction(l.row, l.toString))
        case true  => Seq.empty
      }
    }
  }
}
