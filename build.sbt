name := "dM Image"

normalizedName := "dm-image"

description := "a Scala port (mostly) of the Racket image library by Robby Findler"

organization := "net.toddobryan"

organizationName := "Todd O'Bryan"

version := "0.10-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature")

unmanagedJars in Compile += Attributed.blank(
    file(scala.util.Properties.javaHome) / "lib" / "ext" / "jfxrt.jar")

fork in (Test, run) := true

initialCommands in console := """import net.toddobryan.image._; net.toddobryan.image.initialize()"""

cleanupCommands in console := """net.toddobryan.image.cleanUp()"""

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.40-R8",
  "org.scalatest" %% "scalatest" % "2.2.5",
  "org.jdom" % "jdom2" % "2.0.6",
  "commons-codec" % "commons-codec" % "1.10",
  "com.novocode" % "junit-interface" % "0.11"
)

site.settings

site.sphinxSupport()

site.includeScaladoc()

ghpages.settings

git.remoteRepo := "git@github.com:dupontmanualhs/dm-image.git"

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
    <url>git://github.com/dupontmanualhs/dm-image.git</url>
    <connection>scm:git://github.com/dupontmanualhs/dm-image.git</connection>
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
