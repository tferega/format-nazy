package hr.element.fn.proxies

import scala.runtime.ScalaWholeNumberProxy
import scala.collection.SeqProxy



object Implicits {
  implicit def wholeNumberProxyToVal[T](p: ScalaWholeNumberProxy[T]): T = p.self
  implicit def seqProxyToVal[T](p: SeqProxy[T]): Seq[T] = p.self
}