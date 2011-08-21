package hr.element.fn.proxies

import scala.collection.SeqProxy



object LineBreakParticles {
  import hr.element.fn.proxies.Implicits._
  import hr.element.fn.proxies.ByteParticles._

  sealed trait LineBreakParticle extends SeqProxy[Byte] with Particle
  object Windows extends LineBreakParticle {
    def self = Array[Byte](CR, LF)
    override def toString = "WIN"
  }
  object Unix extends LineBreakParticle {
    def self = Array[Byte](LF)
    override def toString = "UNIX"
  }
  object Mac extends LineBreakParticle {
    def self = Array[Byte](CR)
    override def toString = "MAC"
  }
}
