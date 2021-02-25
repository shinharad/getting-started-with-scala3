ThisBuild / scalaVersion := "3.0.0-RC1"
ThisBuild / version      := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  // TODO
)

lazy val root =
  project
    .in(file("."))
    .aggregate(
      step01,
      step02,
      step03
    )

lazy val step01 =
  project
    .in(file("step01"))
    .settings(commonSettings)
    .settings(
      scalacOptions ++= Seq(
        // Scala 3 Syntax Rewriting

        // "-rewrite", "-indent"
        // "-rewrite", "-new-syntax"
        // "-rewrite", "-no-indent"
        // "-rewrite", "-old-syntax"
      )
    )

lazy val step02 =
  project
    .in(file("step02"))
    .settings(commonSettings)

lazy val step03 =
  project
    .in(file("step03"))
    .settings(commonSettings)

lazy val commonSettings = Seq(
  libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
)