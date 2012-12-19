name := "image"

version := "0.1"

scalaVersion := "2.10.0-RC1"

scalaBinaryVersion := "2.10.0-RC1"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.0-RC1"

libraryDependencies += "org.scala-lang" % "scala-actors" % "2.10.0-RC1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M4" % "test"

libraryDependencies += "commons-codec" % "commons-codec" % "1.6"

scalacOptions += "-deprecation"

scalacOptions += "-feature"

