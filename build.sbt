ThisBuild / scalaVersion := "3.0.0-M3"
ThisBuild / version      := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  // TODO
)

lazy val root =
  project
    .in(file("."))
    .aggregate(
      step01
    )

lazy val step01 =
  project
    .in(file("step01"))
    .settings(commonSettings)

lazy val commonSettings = Seq(
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
)