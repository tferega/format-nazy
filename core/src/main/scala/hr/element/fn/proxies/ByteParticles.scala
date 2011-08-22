package hr.element.fn.proxies

import scala.runtime.ScalaWholeNumberProxy



object ByteParticles {
  sealed trait ByteParticle extends ScalaWholeNumberProxy[Byte] with Particle
  object TAB extends ByteParticle {
    def self = 0x09
    override def toString = "TAB"
  }
  object LF  extends ByteParticle {
    def self = 0x0A
    override def toString = "LF"
  }
  object CR  extends ByteParticle {
    def self = 0x0D
    override def toString = "CR"
  }
  object EOF extends ByteParticle {
    def self = 0x1A
    override def toString = "EOF"
  }
  object SPACE extends ByteParticle {
    def self = 0x20
    override def toString = "SPACE"
  }
}