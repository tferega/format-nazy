import sbt._
import Keys._

object BuildSettings {
  val buildScalaVersion = "2.9.0-1"

  val buildSettingsCore = Defaults.defaultSettings ++ Seq(
    organization := "Element d.o.o",
    name         := "Format Nazy - Scala - Core",
    version      := "0.0.1",
    scalaVersion := buildScalaVersion,
    externalResolvers <<= resolvers map { rs =>
      Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
    }
  )
}

object Resolvers {
  val elementPublic = "Element d.o.o Maven proxy" at "http://maven.element.hr/nexus/content/groups/public/"
  val eleResolvers = Seq(elementPublic)
}

object Dependencies {
  val etb = "hr.element.etb" % "etb_2.9.0-1" % "0.1.21"
  val configrity = "org.streum" %% "configrity" % "0.8.0"
  val commonsIO  = "commons-io" % "commons-io" % "2.0.1"
  val scalatest  = "org.scalatest" % "scalatest_2.9.0" % "1.6.1" % "test"
}

object FormatNazyBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val coreDeps = Seq(
    etb,
    configrity,
    commonsIO,
    scalatest
  )

  lazy val fn = Project(
    "Core",
    file("core"),
    settings = buildSettingsCore ++ Seq(resolvers := eleResolvers, libraryDependencies := coreDeps)
  )
}
