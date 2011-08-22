package hr.element

import java.io.File
import hr.element.fn.parsers._
import hr.element.util._

import java.io.File
import org.apache.commons.io.FileUtils

import hr.element.fn._




object EntryPoint extends Meter {
  val Data = "wain linija jedan\r\nmac linija dva\r\tunix linija tri\neof linija cetri"
    .toArray.map(_.toByte)

  def main(args: Array[String]) {
    C.show
    /*
    val pathInvalid = """c:\1.scala"""
    val pathBin = """r:\ANSICON\ANSI-LLW.exe"""
    val pathBig = """c:\bigtest.scala"""
    val pathMiniBig = """c:\bigtest.scala"""

//    stats(pathMiniBig)
    prettyPrint(Data)
    */
  }

  def stats(path: String) {
    val size = FileUtils.sizeOf(new File(path))
    val t = meter(size, initCount = 5, repeatCount = 20) {
      val d = ByteParser.parseFile(path)
      val r = FormatNazy.scrutinize(d.get)
      r.newDocument.overwrite
    }
    println(t)
  }

  def prettyPrint(data: Array[Byte]) {
    println(ByteParser.parseArray(Data))
  }
}
