package hr.element.fn.parsers

import hr.element.fn.util.ByteReader

import scala.util.parsing.combinator.Parsers




class ByteParser(br: ByteReader) extends Parsers {
  // Some helpful imports.
  import Bytes._
  import Newline._

  // This Parser's element type.
  type Elem = Option[Byte]

  // Helper method used breakLine and endLine blocks to convert a list of Elem-s to an IndexedSeq of Byte-s.
  private implicit def impaleElemList2ISeq(el: List[Elem]): IndexedSeq[Byte] =
    el.map(_.get).toIndexedSeq

  // Mutabibality!!!
  private var lineRow = 0




  /** ###########################
   *  ##### Parser Elements #####
   *  ########################### */
  // An element that signals the end of an input stream (not to be confused with an EOF byte).
  private lazy val elemEOF: Parser[Elem] = elem("EOF elem", _ == None)
  // An element that represents a LF byte.
  private lazy val elemLF:  Parser[Elem] = elem("LF  elem", _ == Some(LF))
  // An element that represents a CR byte.
  private lazy val elemCR:  Parser[Elem] = elem("CR  elem", _ == Some(CR))
  // Any non-eof, non-newline element (that is, any "normal" element).
  private lazy val elemAny: Parser[Elem] = elem("any elem", e => (e != Some(LF)) && (e != Some(CR)) && (e != None))




  /** #########################
   *  ##### Parser Blocks #####
   *  ######################### */
  private lazy val blockEOF: Parser[Newline] = elemEOF         ^^ (_ => EOF)
  private lazy val blockWIN: Parser[Newline] = elemCR ~ elemLF ^^ (_ => WIN)
  private lazy val blockNIX: Parser[Newline] = elemLF          ^^ (_ => NIX)
  private lazy val blockMAC: Parser[Newline] = elemCR          ^^ (_ => MAC)




  /** ############################
   *  ##### Parser Sequences #####
   *  ############################ */
  // A sequence of and non-Newline bytes.
  private lazy val seqByte: Parser[List[Elem]] = rep(elemAny)
  // A sequence of lines ending with a Newline bytes.
  private lazy val  seqLine: Parser[List[Line]] = rep(breakLine)




  /** ###############################
   *  ##### Parser Compositions #####
   *  ############################### */
    // Any of the Newline blocks.
    private lazy val break: Parser[Newline] =
      blockWIN | blockNIX | blockMAC

    // A line that ends with a Newline block.
    private lazy val breakLine: Parser[Line] =
      seqByte ~ break ^^ { e =>
        lineRow += 1
        new Line(lineRow, e._1, e._2)
      }

    // A line does not end with a Newline block (last byte in the line is the lasy byte of the input stream).
    private lazy val endline: Parser[Line] =
      seqByte ~ blockEOF ^^ { e =>
        lineRow += 1
        new Line(lineRow, e._1, e._2)
      }

    // An entire input stream (a list of breakLines with an optional endLine at the end).
    private lazy val lineList: Parser[IndexedSeq[Line]] =
      seqLine ~ opt(endline) <~ elemEOF ^^ (e =>
        e._1.toIndexedSeq ++ e._2)




  /** ###########################
   *  ##### ENTRY FUNCTIONS #####
   *  ########################### */
  // Does the actual parsing.
  private def doParse(): ParseResult[IndexedSeq[Line]] = {
    lineList(br)
  }


  // Main (and only) entry function.
  def parse(): IndexedSeq[Line] = {
    val r = doParse()
    r match {
      case Success(result, _) => result
      case NoSuccess(msg, _)  => throw new ParsingFailedException(msg)
    }
  }
}
