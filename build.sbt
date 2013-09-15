name := "dM Image"

normalizedName := "dm-image"

description := "an image manipulation library based on Robby Findler's 2htdp/image library in Racket"

organization := "org.dupontmanual"

organizationName := "duPont Manual High School"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-swing" % "2.10.2",
  "org.scalautils" %% "scalautils" % "2.0.M6",
  "commons-codec" % "commons-codec" % "1.8",
  "org.scalatest" %% "scalatest" % "2.0.M6" % "test"
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
  <organization>
    <name>duPont Manual High School Computer Science Department</name>
    <url>http://dupontmanual.github.io/</url>
  </organization>
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
