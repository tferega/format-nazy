package hr.element.fn
//import hr.element.fn.Imports._



object Imports extends Imports
object TypeImports extends TypeImports
object StaticForwarderImports extends StaticForwarderImports

trait Imports
    extends TypeImports
    with StaticForwarderImports



// For classes
trait TypeImports {
  type ScrutinizatorConfig = hr.element.fn.config.ScrutinizatorConfig
  type Line                = hr.element.fn.parsers.Line
  type Newline             = hr.element.fn.parsers.Newline

  type File                = java.io.File
}



// For objects
trait StaticForwarderImports {
  val C                   = hr.element.fn.config.C
  val ScrutinizatorConfig = hr.element.fn.config.ScrutinizatorConfig
  val Newline             = hr.element.fn.parsers.Newline
}
