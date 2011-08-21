import sbt._
import de.element34.sbteclipsify._


class FormatNazyProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify {
  val scalaToolsRepo = "Scala Tools" at "http://scala-tools.org/repo-releases/"

  val configgy = "net.lag" % "configgy" % "2.0.0" intransitive()
  val apacheCommonsIo = "commons-io" % "commons-io" % "2.0.1"
}