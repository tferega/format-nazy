/*
package hr.element.fn.parsers

import scala.collection.immutable.HashMap
import net.lag.configgy.Configgy


case class LineBreak(name: String, b: Array[Byte])
class LineBreakMap(lineBreakMap: Map[String, Array[Byte]]) extends HashMap{
  def apply(s: String): LineBreak =
    LineBreak(s, lineBreakMap(s))
}

object C {
  private def getByteRange(start: Char, end: Char): List[Byte] =
    (start to end toList).map(_.toByte)

  Configgy.configure("""C:\fn.conf""")
  val c = Configgy.config

  private val rawByteMap = c.getConfigMap("ByteMap")
  private val byteMapOpt: Option[Map[String, Byte]] = rawByteMap.map { e1 => Map.empty ++ e1.keys.map { e2 => e2 -> e1.getInt(e2).get.toByte } }

//  val byteMap: Map[String, Byte] = Map(
//      "TAB" -> 0x09,
//      "LF"  -> 0x0A,
//      "CR"  -> 0x0D)
//
//  val whitelist: List[Byte] = getByteRange(' ', '~')
//  val lineBreakList: List[LineBreak] = List(
//      LineBreak("WIN", Array(byteMap("CR"), byteMap("LF"))),
//      LineBreak("UNIX", Array(byteMap("LF"))),
//      LineBreak("MAC", Array(byteMap("CR"))))
//
  def show {
    println(c.getString("conf.test"))
    println(c.getList("conf.lista"))
  }

  //val allowNL: LineBreak = lineBreakList
}
*/