val defaultSettings = Seq(
  scalaVersion := "2.12.0",
  parallelExecution in Test := false,
  logBuffered in Test := false,
  testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD"),
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test")

defaultSettings

lazy val protobuf = (project in file("protobuf")).
  settings(
    defaultSettings,
    PB.targets in Compile := Seq(scalapb.gen(grpc = false, flatPackage = true) -> (sourceManaged in Compile).value))

lazy val domain = (project in file("domain")).settings(defaultSettings).dependsOn(protobuf % "test->test;compile->compile")
