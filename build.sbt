lazy val scala3 = "3.3.1"
lazy val supportedScalaVersions = List(scala3)

ThisBuild / organization := "name.michaelford"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := scala3

lazy val dockerSettings = Seq(
  // dockerBaseImage := "ubuntu:latest", // use default jdk (deprecated openjdk)
  // dockerBaseImage := "ubuntu:20.04",
  // dockerBaseImage := "amazoncorretto:11-alpine-jdk",
  dockerBaseImage := "sapmachine:11-jre-ubuntu",
  dockerExposedPorts := Seq(9000, 9001), // todo verify and remove
  // dockerExposedVolumes := Seq("/opt/docker/log"), // todo remove - replace with remote logging framework
  Docker / packageName := s"${(ThisBuild / organization).value}/${(moduleName).value}",
  dockerEntrypoint := Seq("bin/docker-minimal"),
  // Docker / defaultLinuxInstallLocation := "/opt/docker"
)

val catsVersion = "2.9.0"
val scalaTestVersion = "3.2.17"
val zioVersion = "2.0.17" // cats-effect 3.4.8 (cats 2.9.0)

libraryDependencies in Global ++= Seq(
  "org.scalactic"   %% "scalactic"  % scalaTestVersion % Test,
  "org.scalatest"   %% "scalatest"  % scalaTestVersion % Test,
  "org.scalatest" %% "scalatest-freespec" % scalaTestVersion % Test,
  "org.scalatest" %% "scalatest-propspec" % scalaTestVersion % Test,
  "org.scalatestplus" %% "scalacheck-1-17" % "3.2.17.0" % Test,
  "org.typelevel" %% "spire" % "0.18.0",
  "ch.qos.logback" % "logback-classic" % "1.4.7" % Runtime,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
)
lazy val cats = Seq(
  "org.typelevel" %% "cats-core" % catsVersion
)
lazy val zio = Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test
)
lazy val zioLogging = Seq(
  "dev.zio" %% "zio-logging",
  "dev.zio" %% "zio-logging-slf4j2"
).map(_ % "2.1.14")

lazy val dockerMinimal = project.in(file("."))
  .settings(
    name := "docker-minimal",
    libraryDependencies ++=
      zio ++
      zioLogging
  )
  .settings(dockerSettings)
  .enablePlugins(UniversalDeployPlugin, DockerPlugin, JavaAppPackaging)
