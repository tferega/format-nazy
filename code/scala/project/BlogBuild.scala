import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "Element d.o.o."
  val buildScalaVersion = "2.9.1"
  val buildScalacOptions = Seq("-deprecation", "-Yrepl-sync")

  val bsCore = Defaults.defaultSettings ++ Seq(
    organization  := buildOrganization,
    name          := "FormatNazy - Scala - Core",
    version       := "0.0.1",
    scalaVersion  := buildScalaVersion,
    scalacOptions := buildScalacOptions
  )
}



object Resolvers {
  val eleRepo = "Element Repo" at "http://element.hr/ivy"
  val apacheRepo = "apache.repo" at "https://repository.apache.org/content/repositories/snapshots/"

  val resCore = Seq(eleRepo)
}



object Dependencies {
  val etb = "hr.element.etb" % "etb_2.9.0-1" % "0.1.21"

  val configrity = "org.streum" %% "configrity" % "0.8.0"

  val commons = Seq(
    "commons-io" % "commons-io" % "2.0.1",
    "commons-codec" % "commons-codec" % "1.5"
  )

  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
}



object FormatNazyBuild extends Build {
  import BuildSettings._
  import Resolvers._
  import Dependencies._

  val depsCore = commons ++ Seq(
    etb,
    configrity,
    scalatest
  )

  lazy val core = Project(
    "Core",
    file("core"),
    settings = bsCore ++ Seq(
      resolvers := resCore,
      libraryDependencies := depsCore
    )
  )
}