package debug

import java.io.File
import hr.element.fn.parsers._
import hr.element.util._

import java.io.File
import org.apache.commons.io.FileUtils

import hr.element.fn._

import hr.element.fn.main._


object EntryPoint extends Meter {
  val Data1: Array[Byte] = "wain linija jedan\r\nmac linija dva\runix linija tri\neof linija cetri"
    .toArray.map(_.toByte)
  val Data2: Array[Byte] = Array(95, 96, 0xF6, 0x0D, 0x0A, 100, 101, 0xF7).map(_.toByte)

  def main(args: Array[String]) {
    val sg = new ScrutinizatorGenerator("mydoc", "MyDocument")
    val utf8s = sg.utf8
    val d = ByteParser.parse(Data2).get
    val fn = new FormatNazy(sg.utf8)
    val d1 = fn.scrutinize(d)
    println(d1.fullReport)
  }

//  def stats(path: String) {
//    val size = FileUtils.sizeOf(new File(path))
//    val t = meter(size, initCount = 5, repeatCount = 20) {
//      val d = ByteParser.parseFile(path)
//      val r = FormatNazy.scrutinize(d.get)
//      r.newDocument.overwrite
//    }
//    println(t)
//  }

  def prettyPrint(data: Array[Byte]) {
    println(ByteParser.parse(Data2))
  }
}
