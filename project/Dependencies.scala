import sbt.*

object Dependencies {
  val circeVersion = "0.14.0"

  val zioV        = "2.0.21"
  val zioLoggingV = "2.2.0"
  val zioConfV    = "4.0.2"
  val zioTestV    = "2.1.9"
  val zioKafkaV   = "2.7.1"

  val scalaLoggingVersion = "3.9.4"
  val logbackVersion      = "1.5.6"
  val logstashVersion     = "7.4"

  val scalaTestVersion       = "3.2.19"


  lazy val kafka      = List("org.apache.kafka"      %% "kafka"      % "3.8.0")
  lazy val pureConfig = List("com.github.pureconfig" %% "pureconfig" % "0.17.7")

  val circe = List(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-jackson29"
  ).map(_ % circeVersion)

  val zio = List(
    "dev.zio" %% "zio"                 % zioV,
    "dev.zio" %% "zio-streams"         % zioV,
    "dev.zio" %% "zio-kafka"           % zioKafkaV,
    "dev.zio" %% "zio-logging"         % zioLoggingV,
    "dev.zio" %% "zio-logging-slf4j"   % zioLoggingV,
    "dev.zio" %% "zio-config-typesafe" % zioConfV,
    "dev.zio" %% "zio-config-magnolia" % zioConfV
  )

  val logs = {
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"           % scalaLoggingVersion
    val logback      = "ch.qos.logback"             % "logback-classic"          % logbackVersion
    val logstash     = "net.logstash.logback"       % "logstash-logback-encoder" % logstashVersion

    List(scalaLogging, logback, logstash)
  }

  val tests = {
    lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    val zioTest = List(
      "dev.zio" %% "zio-test"          % zioTestV % Test,
      "dev.zio" %% "zio-test-sbt"      % zioTestV % Test,
      "dev.zio" %% "zio-test-magnolia" % zioTestV % Test,
      "dev.zio" %% "zio-kafka-testkit" % "2.8.3" % Test
    )
    List(scalaTest) ++ zioTest
  }

}
