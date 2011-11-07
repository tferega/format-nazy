import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._



object ProjectInfo {
  val Organization = "hr.element.etb"
  val Name = "FormatNazy"
  val PublishName = Name.toLowerCase
  val Version = "0.0.7"
  val ScalaVersion = "2.9.1"
}


object BuildSettings {
  val buildSettingsCore = Defaults.defaultSettings ++ Seq(
    organization  := ProjectInfo.Organization,
    name          := ProjectInfo.Name,
    version       := ProjectInfo.Version,
    scalaVersion  := ProjectInfo.ScalaVersion,
    scalacOptions := Seq("-deprecation", "-Yrepl-sync"),

    externalResolvers <<= resolvers map { rs =>
      Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
    },
    publishMavenStyle := true,
    publishTo         := Some("Element Private Releases" at "http://maven.element.hr/nexus/content/repositories/releases-private/"),
    credentials += Credentials(Path.userHome / ".publish" / ".credentials")
  )

  val assemblySettingsCore = assemblySettings ++ Seq(
    jarName := ProjectInfo.PublishName +"_"+ ProjectInfo.ScalaVersion +"-"+ ProjectInfo.Version +"-assembly.jar"
  )

  val artifactSettingsCore =
    addArtifact(Artifact(ProjectInfo.PublishName, "assembly"), sbtassembly.Plugin.AssemblyKeys.assembly)
}


object Resolvers {
  val elementPublic = "Element d.o.o Maven proxy" at "http://maven.element.hr/nexus/content/groups/public/"
  val apacheRepo = "apache.repo" at "https://repository.apache.org/content/repositories/snapshots/"

  val eleResolvers = Seq(elementPublic)
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
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val CoreDeps = commons ++ Seq(
    etb,
    configrity,
    scalatest
  )

  lazy val core = Project(
    "FN",
    file("core"),
    settings =
      buildSettingsCore ++
      assemblySettingsCore ++
      artifactSettingsCore ++
      Seq(
        resolvers := eleResolvers,
        libraryDependencies := CoreDeps
      )
  )
}