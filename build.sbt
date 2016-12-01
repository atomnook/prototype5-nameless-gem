val defaultSettings = Seq(scalaVersion := "2.12.0")

defaultSettings

lazy val proto = (project in file("proto")).settings(
  defaultSettings,
  PB.targets in Compile := Seq(scalapb.gen(grpc = false, flatPackage = true) -> (sourceManaged in Compile).value))
