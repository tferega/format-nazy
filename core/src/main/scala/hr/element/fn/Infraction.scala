package hr.element.fn

import hr.element.fn.proxies._
import ByteParticles._
import LineBreakParticles._



sealed trait Infraction {
  val name: String
  val payload: Particle

  override def toString = "    %s: %s".format(name, payload.toString)
}

case class InvalidLineBreak(payload: LineBreakParticle) extends Infraction {
  val name = "Invalid Line Break"
}
case class InvalidCharacter(payload: ByteParticle) extends Infraction {
  val name = "Invalid Character"
}
case class InvalidEncoding(payload: ByteParticle) extends Infraction {
  val name = "Invalid Encoding"
}
