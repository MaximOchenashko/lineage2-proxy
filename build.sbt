import com.typesafe.config.ConfigFactory
import play.sbt.PlayScala

name := """lineage2-proxy"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

// disable doc generation on dist
sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

val scalaz = Seq("core", "effect")
  .map(v => "org.scalaz" %% s"scalaz-$v" % "7.2.4")

libraryDependencies ++= Seq(
  ws,
  cache,
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.3",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
  "com.github.etaty" %% "rediscala" % "1.6.0"
) ++ scalaz

routesGenerator := InjectedRoutesGenerator

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

slick <<= slickCodeGenTask
// code generation task

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val url = conf.getString("slick.dbs.default.db.url")
  val jdbcDriver = conf.getString("slick.dbs.default.db.driver")
  val outputDir = root.base.getAbsoluteFile / "app"
  val slickDriver = conf.getString("slick.dbs.default.driver").dropRight(1)
  val pkg = "models"
  val user = conf.getString("slick.dbs.default.db.user")
  val password = conf.getString("slick.dbs.default.db.password")
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg, user, password), s.log))
  Seq.empty[File]
}