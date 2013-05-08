import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "image"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "commons-codec" % "commons-codec" % "1.6"

scalacOptions += "-deprecation"

scalacOptions += "-feature"

site.settings

site.includeScaladoc()

site.pamfletSupport()

seq(jacoco.settings : _*)
