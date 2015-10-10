name := "Scala Image"

normalizedName := "scala-image"

description := "a Scala port (mostly) of the Racket image library by Robby Findler"

organization := "net.toddobryan"

organizationName := "Todd O'Bryan"

version := "0.9-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature")

fork := true

initialCommands in console := """import net.toddobryan.image._; net.toddobryan.image.initialize()"""

cleanupCommands in console := """net.toddobryan.image.cleanUp()"""

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.60-R9",
  "org.scalatest" %% "scalatest" % "2.2.5",
  "org.jdom" % "jdom2" % "2.0.6",
  "commons-codec" % "commons-codec" % "1.10",
  "com.novocode" % "junit-interface" % "0.11"
)

site.settings

site.sphinxSupport()

site.includeScaladoc()

ghpages.settings

git.remoteRepo := "git@github.com:toddobryan/scala-image.git"

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
  <url>http://toddobryan.github.io/scala-image</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/toddobryan/scala-image.git</url>
    <connection>scm:git://github.com/toddobryan/scala-image.git</connection>
  </scm>
)
