name := "dM Image"

normalizedName := "dm-image"

description := "a Scala port (mostly) of the Racket image library by Robby Findler"

organization := "org.dupontmanual"

organizationName := "duPont Manual High School"

version := "0.9-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-feature")

unmanagedJars in Compile += Attributed.blank(
    file(scala.util.Properties.javaHome) / "lib" / "jfxrt.jar")

fork := true

initialCommands in console := """import org.dupontmanual.image._; org.dupontmanual.image.initialize()"""

cleanupCommands in console := """org.dupontmanual.image.cleanUp()"""

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "1.0.0-R8",
  "org.scalatest" %% "scalatest" % "2.1.2",
  "commons-codec" % "commons-codec" % "1.9",
  "com.novocode" % "junit-interface" % "0.11-RC1"
)

site.settings

site.sphinxSupport()

site.includeScaladoc()

ghpages.settings

git.remoteRepo := "git@github.com:dupontmanual/dm-image.git"

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2") 
}

publishArtifact in Test := false

credentials += Credentials(Path.userHome / ".ssh" / ".credentials")

pomExtra := (
  <url>http://dupontmanual.github.io/dm-image</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/dupontmanual/dm-image.git</url>
    <connection>scm:git://github.com/dupontmanual/dm-image.git</connection>
  </scm>
  <developers>
    <developer>
      <name>Jim Miller</name>
      <roles>
        <role>Student, Class of 2014</role>
      </roles>
    </developer>
    <developer>
      <name>Todd O'Bryan</name>
      <roles>
        <role>Teacher</role>
      </roles>
    </developer>
  </developers>
)
