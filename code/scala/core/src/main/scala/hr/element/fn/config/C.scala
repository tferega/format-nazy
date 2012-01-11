package hr.element.fn.config

import hr.element.fn.parsers.Newline



object ScrutinizatorConfig {
  import Newline._

  def getDefaultByNewlineList(newlineList: Newline*) =
    new ScrutinizatorConfig(
      newlineList.contains(_),
      e => !(e >= 0x00 && e < 0x20)
  )

  val ScWin = getDefaultByNewlineList(WIN)
  val ScNix = getDefaultByNewlineList(NIX)
  val ScMixed = getDefaultByNewlineList(WIN, NIX)
}

case class ScrutinizatorConfig(
    newlinePredicate: Newline => Boolean,
    characterPredicate: Byte => Boolean
)


object C {
  val ScrutinizatorConfigs: Map[String, ScrutinizatorConfig] = Map(
    "bat"    -> ScrutinizatorConfig.ScWin,
    "conf"   -> ScrutinizatorConfig.ScNix,
    "config" -> ScrutinizatorConfig.ScNix,
    "cs"     -> ScrutinizatorConfig.ScWin,
    "css"    -> ScrutinizatorConfig.ScNix,
    "csv"    -> ScrutinizatorConfig.ScNix,
    "html"   -> ScrutinizatorConfig.ScNix,
    "java"   -> ScrutinizatorConfig.ScNix,
    "js"     -> ScrutinizatorConfig.ScNix,
    "less"   -> ScrutinizatorConfig.ScNix,
    "php"    -> ScrutinizatorConfig.ScMixed,
    "scala"  -> ScrutinizatorConfig.ScNix,
    "sh"     -> ScrutinizatorConfig.ScNix,
    "txt"    -> ScrutinizatorConfig.ScMixed,
    "yaml"   -> ScrutinizatorConfig.ScNix
  )
}
