addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.1")

libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.5.44"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")
