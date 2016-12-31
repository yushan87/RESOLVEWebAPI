// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/ivy-releases/"

// SBT Plugin for Play Framwork 2
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")

// SBT Plugin for Bytecode Enhancement
addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.1.0")

// SBT Plugin for (License) Headers
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.6.0")