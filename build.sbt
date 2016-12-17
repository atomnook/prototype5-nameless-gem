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

val scalatest = libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

val libSettings = defaultSettings ++ Seq(scalatest)

val protobufSettings = defaultSettings ++ Seq(
  PB.targets in Compile := Seq(
    PB.gens.java -> (sourceManaged in Compile).value,
    scalapb.gen(javaConversions=true, grpc = false, flatPackage = true) -> (sourceManaged in Compile).value),
  libraryDependencies ++= Seq(
    "com.google.protobuf" % "protobuf-java-util" % com.trueaccord.scalapb.compiler.Version.protobufVersion,
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"))

val domainSettings = defaultSettings ++ Seq(scalatest)

val boardSettings = defaultSettings ++ Seq(
  libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.4.12")) // https://github.com/playframework/playframework/blob/2.5.10/framework/project/Dependencies.scala#L9

val toolSettings = defaultSettings ++ Seq(
  javaOptions in Test ++= Seq(
    s"-D$chromedriverProp=$chromedriver",
    s"-D$gekodriverProp=$gekodriver"),
  libraryDependencies ++= Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
    "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.48.2" % "test")) // https://github.com/playframework/scalatestplus-play/blob/1.5.1/build.sbt#L38

val testDependency = "test->test"

lazy val lib = (project in file("lib")).settings(libSettings)

lazy val protobuf = (project in file("protobuf")).settings(protobufSettings).dependsOn(lib)

lazy val domain = (project in file("domain")).settings(domainSettings).dependsOn(protobuf, protobuf % testDependency)

lazy val board = (project in file("board")).settings(boardSettings).dependsOn(protobuf)

lazy val tool = (project in file("tool")).enablePlugins(PlayScala).settings(toolSettings).
  dependsOn(domain, board, protobuf % testDependency)

defaultSettings
