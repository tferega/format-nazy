package hr.element.fn.config



object ByteData {
  def apply(v: Int, abbr: String, description: String) =
    new ByteData(Seq(v.toByte), abbr, description)
}

class ByteData(val v: Seq[Byte], val abbr: String, val desc: String) extends BaseData {
  val name = "Byte"
  val abbrOpt = Some(abbr)
}