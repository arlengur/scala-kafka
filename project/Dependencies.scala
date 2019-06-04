import sbt._

object Dependencies {
  lazy val scalaTest  = "org.scalatest"         %% "scalatest"  % "3.0.5"
  lazy val kafka      = "org.apache.kafka"      %% "kafka"      % "2.2.0"
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.11.0"
}
