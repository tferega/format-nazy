package hr.element.fn
//import hr.element.fn.Imports._



object Imports extends Imports
object TypeImports extends TypeImports
object StaticForwarderImports extends StaticForwarderImports

trait Imports
    extends TypeImports
    with StaticForwarderImports
    with Implicits
    with org.scala_tools.time.Imports
    with ETBTemporaryPimps



// For classes
trait TypeImports {
  type File = java.io.File

  type BaseData       = hr.element.fn.config.BaseData
  type BOMData        = hr.element.fn.config.BOMData
  type ByteData       = hr.element.fn.config.ByteData
  type LineBreakData  = hr.element.fn.config.LineBreakData

  type Line           = hr.element.fn.parsers.Line
  type Document       = hr.element.fn.parsers.Document
  type FileDocument   = hr.element.fn.parsers.FileDocument

  type Level          = hr.element.fn.main.Level

  type ByteReader     = hr.element.fn.util.ByteReader
}



// For objects
trait StaticForwarderImports {
  val FileUtils = FileUtilsWrapper

  val C              = hr.element.fn.config.C
  val BOMData        = hr.element.fn.config.BOMData
  val ByteData       = hr.element.fn.config.ByteData
  val LineBreakData  = hr.element.fn.config.LineBreakData

  val Level          = hr.element.fn.main.Level
}



// @#@#@# Maknuti jednom kada u ETB-u object Pimps prebacimo u trait Pimps
trait ETBTemporaryPimps {
  import hr.element.etb.Pimps
  implicit def pimpMyString(s: String)   = Pimps.pimpMyString(s)
  implicit def pimpMyBoolean(b: Boolean) = Pimps.pimpMyBoolean(b)
}


// @#@#@# Ovo je ODVRATNO, i treba nekako popraviti
object FileUtilsWrapper extends {
  import org.apache.commons.io.FileUtils
  def readFileToString(file: Imports.File)                                  = FileUtils.readFileToString(file)
  def writeStringToFile(file: Imports.File, data: String)                   = FileUtils.writeStringToFile(file, data)
  def writeStringToFile(file: Imports.File, data: String, encoding: String) = FileUtils.writeStringToFile(file, data, encoding)
  def writeByteArrayToFile(file: Imports.File, data: Array[Byte])           = FileUtils.writeByteArrayToFile(file, data)
  def cleanDirectory(file: Imports.File)                                    = FileUtils.cleanDirectory(file)
  def readFileToByteArray(file: Imports.File)                               = FileUtils.readFileToByteArray(file)
}
