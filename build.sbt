ThisBuild / scalaVersion := "3.0.0-RC1"
ThisBuild / version      := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-explain",
  "-explain-types"

  // TODO
)

lazy val root =
  project
    .in(file("."))
    .aggregate(
      step01,
      step02,
      step03,
      step04
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
        // 以下のコンパイラオプションを有効にすることで、
        // MigrationMode.scala のエラーが警告になったり、自動的に書き換わります

        // このコメントアウトを外すと、
        // マイグレーションモードとして Scala3 でのコンパイルエラーが警告に変わります
        // "-source:3.0-migration"

        // このコメントアウトを外すと、MigrationMode.scala が書き換わります
        // "-source:3.0-migration", "-rewrite"
      )
    )

lazy val commonSettings = Seq(
  libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
)