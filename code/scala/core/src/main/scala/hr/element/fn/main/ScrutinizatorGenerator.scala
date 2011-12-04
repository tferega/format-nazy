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



  type BasicScrutinizatorFunction[T] = T => Seq[InfractionBase[T]]
  type DocumentBSF = BasicScrutinizatorFunction[Document]
  type LineBSF = BasicScrutinizatorFunction[Line]
  type ByteBSF = BasicScrutinizatorFunction[Byte]

  type ArgumentedScrutinizatorFunction[P, T] = (P => Boolean, T) => Seq[InfractionBase[T]]
  type DocumentASF[P] = ArgumentedScrutinizatorFunction[P, Document]
  type LineASF[P] = ArgumentedScrutinizatorFunction[P, Line]
  type ByteASF[P] = ArgumentedScrutinizatorFunction[P, Byte]



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
        Seq(new EncodingInfraction(l.row, l.strBody))
    }
  }


  private lazy val characterFun: LineBSF = (l) => {
    l.body.collect {
      case x if x == '\t' => new CharacterInfraction(l.row, l.strBody)
    }
  }


  private lazy val newlineFun: LineBSF = (l) => {
    l.newline match {
      case MAC => Seq(new NewlineInfraction(l.row, l.strBody))
      case _ => Seq.empty
    }
  }



  private lazy val t: LineASF[Byte] = (b, l) => {
    l.body.collect {
      case x if b(x) => new CharacterInfraction(l.row, l.strBody)
    }
  }
}
