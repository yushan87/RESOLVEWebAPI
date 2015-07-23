name := "RESOLVEWebAPI"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

libraryDependencies ++= Seq(
  "org.antlr" % "antlr4" % "4.5"
)

unmanagedBase := baseDirectory.value / "custom_lib"

routesGenerator := InjectedRoutesGenerator

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)