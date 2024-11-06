import Dependencies.*
import ScalaOptions.*

lazy val root = (project in file("."))
  .settings(
    name := "scala-kafka",
    organization := "ru.arlen",
    scalaVersion := "2.13.11",
    scalacOptions ++= scalaCompilerOptions,
    libraryDependencies ++= circe ::: scalaTest ::: kafka ::: pureConfig
  )
