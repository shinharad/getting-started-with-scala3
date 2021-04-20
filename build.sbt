ThisBuild / scalaVersion := "3.0.0-RC3"
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
      step03,
      step04,
      `step05-import-export`,
      `step05-open-class`,
      `step05-explicit-nulls`
    )

lazy val step01 =
  project
    .in(file("step01"))
    .settings(commonSettings)
    .settings(
      scalacOptions ++= Seq(
        // Scala 3 Syntax Rewriting

        // 1行ずつコメントアウトを外すことで、
        // Scala3SyntaxRewriting.scala の内容が書き換わることを確認してみてください

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

lazy val step04 =
  project
    .in(file("step04"))
    .settings(commonSettings)
    .settings(
      scalacOptions ++= Seq(
        // Scala 3.0 Migration mode

        // 以下のコンパイラオプションを有効にすることで、
        // Scala 2.13 と Scala 3.0 の非互換性に関する詳細なエラーを表示したり、
        // コードが自動的に書き換わります

        // 以下を有効にすると詳細なエラーが表示されます
        // "-explain",

        // 以下を有効にすると、非互換性のエラーが警告に変わります
        // "-source:3.0-migration"

        // 以下を有効にすると、非互換性の警告箇所が自動的に書き換わります
        // "-source:3.0-migration", "-rewrite"
      )
    )

lazy val `step05-import-export` =
  project
    .in(file("step05/import-export"))
    .settings(commonSettings)

lazy val `step05-open-class` =
  project
    .in(file("step05/open-class"))
    .settings(commonSettings)
    .settings(
      scalacOptions ++= Seq(
        "-source", "future"
      )
    )

lazy val `step05-explicit-nulls` =
  project
    .in(file("step05/explicit-nulls"))
    .settings(commonSettings)
    .settings(
      scalacOptions ++= Seq(
        "-Yexplicit-nulls",
        "-Ysafe-init",
        // "-language:unsafeNulls"
      )
    )

lazy val commonSettings = Seq(
  libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
)