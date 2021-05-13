# sbt Migration Tutorial {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [sbt Migration Tutorial](#sbt-migration-tutorial-1)
  - [1. Check the project prerequisites](#1-check-the-project-prerequisites)
  - [2. Choose a module](#2-choose-a-module)
  - [3. Set up cross-building](#3-set-up-cross-building)
  - [4. Prepare the dependencies](#4-prepare-the-dependencies)
  - [5. Configure the Scala 3 Compiler](#5-configure-the-scala-3-compiler)
  - [6. Solve the Incompatibilities](#6-solve-the-incompatibilities)
  - [7. Validate the migration](#7-validate-the-migration)
  - [8. Finalize the migration](#8-finalize-the-migration)

<!-- /code_chunk_output -->

## 概要

sbt Migration Tutorial では、sbt プロジェクトを Scala 3 へ移行していく様子が順を追って解説されています。ここでは、どのような点に気をつけながら Scala 3 に移行するのかを確認しておきましょう。

（実はこの後に解説する Scala 3 Migrate Plugin を使用することで、もう少し移行作業は楽になりそうです）


## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) の Tutorials からこちらを参照します。

- [sbt Migration Tutorial](https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/sbt-migration.html)


## sbt Migration Tutorial

https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/sbt-migration.html

sbt プロジェクトを Scala 3 へ移行する際、移行元のプロジェクトは以下の前提を満たしている必要があります。

- Scala 2.13.x
- sbt 1.5.x

それでは、プロジェクト全体を Scala 3 に移行するために必要な手順を見ていきましょう。

### 1. Check the project prerequisites

- [Project Prerequisites](https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/prerequisites.html) に記載されている条件を満たしていることを確認する

### 2. Choose a module

- Scala 2.13 と Scala 3 はある程度互換性があるので、どのモジュールからでも始めることができる
- 依存関係の少ないモジュールから始めるのがシンプル
- マクロ定義やマクロアノテーションを内部で使用している場合は、まずそれらを移行する必要がある

### 3. Set up cross-building

- コードベースの移行には、2つの大きな課題がある
  - ソースコードをコンパイルする
  - ランタイムの動作が変わらないことを確認する
- このチュートリアルでは、コードを Scala 3 と Scala 2.13 でコンパイルする、クロスビルド戦略（cross-building strategy）を推奨している
  - その理由は、各修正の後に Scala 2.13 でテストを実行し、ランタイムの動作が変更されていないことを確認するため
  - これは、非互換性を修正する際に発生する可能性のあるバグを避けるために非常に重要
- クロスビルドの設定はこのようにする
  ```scala
  // デフォルトは、3.0.0
  scalaVersion := "3.0.0"

  // 2.13.5 は、`++2.13.5` コマンドで読み込むことができる
  // 3.0.0 は、`++3.0.0` コマンドで読み込むことができる
  crossScalaVersions ++= Seq("2.13.5", "3.0.0")
  ```
- `reload` コマンドは、常にデフォルトのバージョンをロードするので注意が必要

### 4. Prepare the dependencies

- この段階でコンパイルを実行すると、いくつかの依存関係が見つからないというエラーを出力する可能性がある
- これは、依存関係のバージョンが Scala3 用に公開していないからなので、新しいバージョンにアップグレードするか、sbt に Scala 2.13 バージョンのライブラリを使うように指定する必要がある
- 依存関係のライブラリが Scala 3 のバージョンを公開しているかは、[Scaladex](https://index.scala-lang.org/) で探すことができる
- ライブラリの Scala 3 バージョンが存在した場合
  - Scala 3 をサポートしたいずれかのバージョンを使用する
  - その際は、選択したバージョンが既存のコードに変化を与えないか確認する
- ライブラリの Scala 3 バージョンが存在しなかった場合
  - Scala 2.13 バージョンを使用することができる
    ```scala
    ("com.lihaoyi" %% "os-lib" % "0.7.3").cross(CrossVersion.for3Use2_13)
    ```
  - Scala.js の場合
    ```scala
    ("com.lihaoyi" %%% "os-lib" % "0.7.3").cross(CrossVersion.for3Use2_13)
    ```
- 未解決の依存関係をすべて修正したら、Scala 2.13 でテストがまだ通るかを確認する
  ```
  sbt:example> ++2.13.5
  sbt:example> test
  ...
  [success]
  ```

### 5. Configure the Scala 3 Compiler

- Scala 3 のコンパイラオプションは Scala 2.13 のものとは異なる
  - いくつかのオプションはリネームされたり、まだ Scala 3 をサポートしていないものもある
  - 詳細は [Compiler Options Table](https://scalacenter.github.io/scala-3-migration-guide/docs/compiler-options/compiler-options-table.html) を参照
- 共通的に適用するものや、`scalaVersion` によって適用するものを切り替える例
  ```scala
  scalacOptions ++= {
    Seq(
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:implicitConversions",
      // disabled during the migration
      // "-Xfatal-warnings"
    ) ++ 
      (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) => Seq(
          "-unchecked",
          "-source:3.0-migration"
        )
        case _ => Seq(
          "-deprecation",
          "-Xfatal-warnings",
          "-Wunused:imports,privates,locals",
          "-Wvalue-discard"
        )
      })
  }
  ```
- この時、`-Xfatal-warnings` を無効にすることで、マイグレーションモードと自動リライトを最大限に活用することができる

### 6. Solve the Incompatibilities

- ここでいよいよ Scala 3 でのコンパイルにチャレンジする
  ```
  sbt:example> ++3.0.0
  [info] Setting Scala version to 3.0.0 on 1 project.
  ...
  sbt:example> / compile
  ...
  sbt:example> / Test / compile
  ```
- この時コンパイラは、2つの異なるレベルの診断を行う
  - エラーになった場合: コードの一部がコンパイルできなくなった場合、エラーには様々な理由があるため、[Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html) で詳細を確認する
  - 移行に関する警告が出力された場合: コンパイラオプションの `-rewrite` で自動的にパッチを当てることができる
- エラーの修正
  - [Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html) のそれぞれ非互換性は、その説明と提案された解決策にリンクされている
  - 特に、エラーがライブラリの公開インターフェイスに影響を与える場合は、バイナリ互換性を最も維持できるソリューションを選択する必要がある
  - 非互換性を修正した後は、Scala 2.13 のテストを実行して解決策を検証することができる
    ```
    sbt:example> ++2.13.5
    [info] Setting Scala version to 2.13.5 on 1 project.
    ...
    sbt:example> / test
    ...
    [success]
    ```
  - すべてのエラーを修正すると、Scala 3 で正常にコンパイルできるようになる
- 移行に関する警告メッセージの修正
  - 残っているのは移行に関する警告で、コンパイラが自動的にパッチを当ててくれる
  - `-rewrite` コンパイラオプションを追加して、もう一度コンパイルしてみる
    ```
    sbt:example> ++3.0.0
    [info] Setting Scala version to 3.0.0 on 1 project.
    ...
    sbt:example> set  / scalacOptions += "-rewrite"
    [info] Defining  / scalacOptions
    ...
    sbt:example>  / compile
    ...
    [info] [patched file /example/src/main/scala/app/Main.scala]
    [warn] two warnings found
    [success]
    ```
- 最後に `-source:3.0-migration` オプションを削除し、必要に応じて `-Xfatal-warnings` オプションを再度追加する

### 7. Validate the migration

- まれに、異なる暗黙の値が解決され、プログラムの実行時の動作が変わることがある。このようなバグを見逃さないためには、テストが唯一の保証となる
- Scala 3 でテストを実行する
  ```
  sbt:example>  / test
  ...
  [success]
  ```

### 8. Finalize the migration

- これで単一のモジュールの Scala 3 の移行の作業は完了
- プロジェクトが Scala 3 に完全に移行するまで、各モジュールに対して同じプロセスを繰り返す
- そして、ライブラリをクロスパブリッシュ（cross-publish）するかどうかによって、Scala 2.13 のクロスビルドの設定をそのままにするかどうかを決める
