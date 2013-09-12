name := "dm-image"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M7"

libraryDependencies += "commons-codec" % "commons-codec" % "1.8"

scalacOptions ++= Seq("-deprecation", "-feature")

site.settings

site.sphinxSupport()

site.includeScaladoc()

ghpages.settings

git.remoteRepo := "git@github.com:dupontmanual/dm-image.git"
