package hr.element.fn.config



object BOMData {
  def apply(v: Seq[Int], description: String) =
    new BOMData(v.map(_.toByte), description)
}

class BOMData(val v: Seq[Byte], val desc: String) extends BaseData {
  val name = "BOM"
  val abbrOpt = None
}