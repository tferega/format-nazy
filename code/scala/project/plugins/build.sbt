resolvers += {
  val typesafeProxy = new java.net.URL("http://maven.element.hr/nexus/content/repositories/typesafe/")
  val pattern = Patterns(false, "[organization]/[module]/[sbtversion]/[revision]/[type]s/[module](-[classifier])-[revision].[ext]")
  Resolver.url("Element d.o.o. Ivy Repositories", typesafeProxy)(pattern)
}

libraryDependencies <<= (libraryDependencies, sbtVersion) { (deps, version) =>
  deps :+ ("com.typesafe.sbteclipse" %% "sbteclipse" % "1.3-RC3" extra("sbtversion" -> version))
}
