name := "spray-chat"

version := "0.1"

scalaVersion  := "2.11.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.5"
  val sprayV = "1.3.1"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-client" % sprayV,
    "io.spray"            %%  "spray-json"	  % "1.2.6",
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.scalatest"       %%  "scalatest"     % "2.2.1" % "test"
  )
}

Revolver.settings

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)