import sbt.*

object Dependencies {
  val circeVersion        = "0.14.0"

  lazy val scalaTest  = List("org.scalatest"         %% "scalatest"       % "3.2.19" % Test)
  lazy val kafka      = List("org.apache.kafka"      %% "kafka"           % "3.8.0")
  lazy val pureConfig = List("com.github.pureconfig" %% "pureconfig"      % "0.17.7")
  lazy val logback    = List("ch.qos.logback"        %% "logback-classic" % "1.2.9")

  val circe = List(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-jackson29"
  ).map(_ % circeVersion)

}
