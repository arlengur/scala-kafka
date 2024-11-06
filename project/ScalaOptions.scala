object ScalaOptions {

  // format: off
  val scalaCompilerOptions = Seq(
    "-encoding", "utf8",
    "-deprecation",
    "-release", "11",
    "-feature",
    "-Xfatal-warnings",
    "-language:existentials",
    "-language:higherKinds",
    "-language:postfixOps",
    "-Wunused:imports",
    "-Ymacro-annotations",
  )
  // format: on
}
