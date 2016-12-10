val defaultSettings = Seq(
  scalaVersion := "2.11.8",
  parallelExecution in Test := false,
  logBuffered in Test := false,
  testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDS"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-target:jvm-1.8",
    "-Xfatal-warnings"))

val chromedriverProp = "webdriver.chrome.driver"

val gekodriverProp = "webdriver.gecko.driver"

val chromedriver = file(sys.props.getOrElse(chromedriverProp, "chromedriver")).getAbsolutePath

val gekodriver = file(sys.props.getOrElse(gekodriverProp, "geckodriver")).getAbsolutePath

val protobufSettings = defaultSettings ++ Seq(
  PB.targets in Compile := Seq(scalapb.gen(grpc = false, flatPackage = true) -> (sourceManaged in Compile).value),
  libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test")

val domainSettings = defaultSettings ++ Seq(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test")

val toolSettings = defaultSettings ++ Seq(
  javaOptions in Test ++= Seq(
    s"-D$chromedriverProp=$chromedriver",
    s"-D$gekodriverProp=$gekodriver"),
  libraryDependencies ++= Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
    "org.seleniumhq.selenium" % "selenium-firefox-driver" % "3.0.1" % "test"))

val testDependency = "test->test"

lazy val protobuf = (project in file("protobuf")).settings(protobufSettings)

lazy val domain = (project in file("domain")).settings(domainSettings).dependsOn(protobuf, protobuf % testDependency)

lazy val tool = (project in file("tool")).enablePlugins(PlayScala).settings(toolSettings).
  dependsOn(domain, protobuf % testDependency)

defaultSettings
