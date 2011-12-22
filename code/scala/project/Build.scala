import sbt._
import Keys._

import sbtassembly.Plugin._
import AssemblyKeys._

object ProjectInfo {
  val Organization = "hr.element.etb"
  val Name = "FormatNazy"
  val PublishName = Name.toLowerCase
  val Version = "0.1.0"
  val ScalaVersion = "2.9.1"
}

object BuildSettings {
  val buildSettingsCore = Defaults.defaultSettings ++ Seq(
    organization  := ProjectInfo.Organization,
    name          := ProjectInfo.Name,
    version       := ProjectInfo.Version,
    scalaVersion  := ProjectInfo.ScalaVersion,
    scalacOptions := Seq("-deprecation", "-Yrepl-sync"),
    publishMavenStyle := true,
    publishTo         := Some("Element Private Releases" at "http://maven.element.hr/nexus/content/repositories/releases-private/"),
    credentials += Credentials(Path.userHome / ".publish" / "element.credentials")
  )

  val buildSettingsNld  = Defaults.defaultSettings ++ Seq(
    organization  := ProjectInfo.Organization,
    name          := "NewlineDriller",
    version       := "0.0.1",
    scalaVersion  := ProjectInfo.ScalaVersion,
    scalacOptions := Seq("-deprecation", "-Yrepl-sync"),
    publishMavenStyle := true,
    publishTo         := Some("Element Private Releases" at "http://maven.element.hr/nexus/content/repositories/releases-private/"),
    credentials += Credentials(Path.userHome / ".publish" / "element.credentials")
  )

  val assemblySettingsCore = assemblySettings ++ Seq(
    jarName := ProjectInfo.PublishName +"_"+ ProjectInfo.ScalaVersion +"-"+ ProjectInfo.Version +"-assembly.jar"
  )

  val artifactSettingsCore =
    addArtifact(Artifact(ProjectInfo.PublishName, "assembly"), sbtassembly.Plugin.AssemblyKeys.assembly)

  val assemblySettingsNld = assemblySettings ++ Seq(
    jarName := "etb-nl_"+ ProjectInfo.Version +".jar"
  )

  val artifactSettingsNld =
    addArtifact(Artifact(ProjectInfo.PublishName, "assembly"), sbtassembly.Plugin.AssemblyKeys.assembly)
}


object Resolvers {
  val elementPublic = "Element d.o.o Maven proxy" at "http://maven.element.hr/nexus/content/groups/public/"
  val apacheRepo = "apache.repo" at "https://repository.apache.org/content/repositories/snapshots/"

  val eleResolvers = Seq(elementPublic)
}


object Dependencies {
  val etb = "hr.element.etb" %% "etb" % "0.1.22"
  val configrity = "org.streum" %% "configrity" % "0.9.0"
  
  val commonsIo = "commons-io" % "commons-io" % "2.1"
  val commonsCodec = "commons-codec" % "commons-codec" % "1.6"

  val scalaIo = "com.github.scala-incubator.io" %% "scala-io-file" % "0.2.0" 
  val diffUtils = "com.googlecode.java-diff-utils" % "diffutils" % "1.2.1"
  
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"

  val depsCore = Seq(
    commonsIo,
    commonsCodec,    
    etb,
    configrity,

    // test
    scalatest
  )

  val depsNld = Seq(
    scalaIo,
    diffUtils,
    
    // test
    scalatest
  ) 
}


object FormatNazyBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val cons = Project(
    "NLD",
    file("nld"),
    settings =
      buildSettingsNld ++
      assemblySettingsNld ++
      artifactSettingsNld ++
      Seq(
        resolvers := eleResolvers,
        libraryDependencies := depsNld
      )
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
        libraryDependencies := depsCore
      )
  )
}