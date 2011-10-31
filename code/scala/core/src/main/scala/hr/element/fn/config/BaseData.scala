package hr.element.fn.config



trait BaseData {
  val name: String
  val v: Seq[Byte]
  val abbrOpt: Option[String]
  val desc: String

  override def toString =
    abbrOpt match {
      case Some(abbr) => "%s %s (%s)".format(name, abbr, desc)
      case None       => "%s %s".format(name, desc)
  }

  def length = v.length
}