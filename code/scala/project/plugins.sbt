// +------------------------------------------------------------------------------------+
// | SBT Eclipse (https://github.com/typesafehub/sbteclipse)                            |
// | Creates .project and .classpath files for easy Eclipse project imports             |
// |                                                                                    |
// | See also: Eclipse downloads (http://www.eclipse.org/downloads/)                    |
// | See also: Scala IDE downloads (http://download.scala-ide.org/)                     |
// +------------------------------------------------------------------------------------+

resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse" % "1.5.0")

// +------------------------------------------------------------------------------------+
// | Proguard plugin (https://github.com/siasia/xsbt-proguard-plugin)                   |
// | Optimizes bytecode and shrinks jar releases by removing unreferenced entities.     |
// |                                                                                    |
// | See also: Proguard homepage (http://proguard.sourceforge.net/)                     |
// +------------------------------------------------------------------------------------+

// resolvers += "Siasia repo" at "http://siasia.github.com/maven2"

// libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-proguard-plugin" % (v+"-0.1.1"))

// +------------------------------------------------------------------------------------+
// | SBT Scalariform (https://github.com/typesafehub/sbt-scalariform)                   |
// | Performs source code formatting                                                    |
// |                                                                                    |
// | See also: Scalariform reference (http://mdr.github.com/scalariform/)               |
// +------------------------------------------------------------------------------------+

// (repository already added above)
// resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0")

// +------------------------------------------------------------------------------------+
// | SBT Assembly (https://github.com/eed3si9n/sbt-assembly)                            |
// | Creates single jar releases from multiple projects                                 |
// +------------------------------------------------------------------------------------+

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.7.2")