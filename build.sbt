name := "RESOLVEWebAPI"

version := "1.0"

scalaVersion := "2.11.7"

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
  "org.antlr" % "antlr4" % "4.5"
)

// Unmanaged Dependencies (Compile/Runtime)
unmanagedBase in Compile := baseDirectory.value / "custom_lib"
unmanagedBase in Runtime := (unmanagedBase in Compile).value

// Unmanaged Dependencies (Test)
unmanagedBase in Test := baseDirectory.value / "test" / "lib"

// Use injection
routesGenerator := InjectedRoutesGenerator

lazy val main = (project in file("."))
  .enablePlugins(PlayJava)