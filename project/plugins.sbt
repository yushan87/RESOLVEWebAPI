// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Play sbt plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.2")

// Bytecode enhancement
addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.1.0")