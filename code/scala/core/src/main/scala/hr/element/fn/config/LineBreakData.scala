package hr.element.fn.config



object LineBreakData {
  def apply(v: Seq[Byte], abbr: String, description: String) =
    new LineBreakData(v, abbr, description)
}

class LineBreakData(val v: Seq[Byte], val abbr: String, val desc: String) extends BaseData {
  val name = "Line Break"
  val abbrOpt = Some(abbr)
}