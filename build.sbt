import de.heikoseeberger.sbtheader.HeaderPattern

name := "RESOLVEWebAPI"

version := "1.0"

scalaVersion := "2.12.2"

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
  "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.0",
  "com.typesafe.play" %% "play-iteratees" % "2.6.0",
  "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.0",
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.play" %% "play-ws-standalone-json" % "1.0.0",
  "org.antlr" % "antlr4" % "4.7"
)

// Unmanaged Dependencies
unmanagedBase := baseDirectory.value / "custom_lib"

// Use Injection
routesGenerator := InjectedRoutesGenerator

// License Headers
headers := headers.value ++ Map(
  "java" -> (
    HeaderPattern.cStyleBlockComment,
    """|/**
       | * ---------------------------------
       | * Copyright (c) 2017
       | * RESOLVE Software Research Group
       | * School of Computing
       | * Clemson University
       | * All rights reserved.
       | * ---------------------------------
       | * This file is subject to the terms and conditions defined in
       | * file 'LICENSE.txt', which is part of this source code package.
       | */
       |""".stripMargin
  )
)

// Java Formatter
javaFormattingSettingsFilename := "rsrg-format.xml"

lazy val main = (project in file("."))
  .enablePlugins(PlayJava, AutomateHeaderPlugin)