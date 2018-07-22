name := "RESOLVEWebAPI"

version := "1.0"

scalaVersion := "2.12.6"

// Scala compiler options
scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls"
)

// Javac compiler options
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Xdiags:verbose"
)

// Managed Dependencies
libraryDependencies ++= Seq(
  guice,
  "com.atlassian.commonmark" % "commonmark"  % "0.11.0",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "com.typesafe.play" % "play-ahc-ws-standalone_2.12" % "1.1.9",
  "com.typesafe.play" % "play-iteratees_2.12" % "2.6.1",
  "com.typesafe.play" % "play-iteratees-reactive-streams_2.12" % "2.6.1",
  "com.typesafe.play" % "play-json_2.12" % "2.6.9",
  "com.typesafe.play" % "play-ws-standalone-json_2.12" % "1.1.9",
  "org.antlr" % "antlr4" % "4.7.1"
)

// Unmanaged Dependencies
unmanagedBase := baseDirectory.value / "custom_lib"

// Use Injection
routesGenerator := InjectedRoutesGenerator

// License Headers
headerMappings := headerMappings.value + (HeaderFileType.java -> HeaderCommentStyle.cStyleBlockComment)

headerLicense := Some(HeaderLicense.Custom(
  """|---------------------------------
     |Copyright (c) 2018
     |RESOLVE Software Research Group
     |School of Computing
     |Clemson University
     |All rights reserved.
     |---------------------------------
     |This file is subject to the terms and conditions defined in
     |file 'LICENSE.txt', which is part of this source code package.""".stripMargin
))

// Java Formatter
javaFormattingSettingsFilename := "rsrg-format.xml"

lazy val main = (project in file("."))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)
