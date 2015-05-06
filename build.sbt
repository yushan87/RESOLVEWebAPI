name := "RESOLVEWebAPI"

version := "1.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq(
  "-feature", // Shows warnings in detail in the stdout
  "-language:reflectiveCalls" 
)

libraryDependencies ++= Seq(
  "org.antlr" %% "antlr4-maven-plugin" % "4.3"
)

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)