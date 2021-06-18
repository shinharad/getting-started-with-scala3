# Porting an sbt Project {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Porting an sbt Project](#porting-an-sbt-project-1)
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

Porting an sbt Project では、sbt プロジェクトを Scala 3 へ移行していく様子をステップ・バイ・ステップで解説されています。ここでは、どのような点に気をつけながら Scala 3 に移行するのかを確認しておきましょう。

## ドキュメント参照先

[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) の Migration Tutorial からこちらを参照します。

- [Porting an sbt Project](https://docs.scala-lang.org/scala3/guides/migration/tutorial-sbt.html)

## Porting an sbt Project

https://docs.scala-lang.org/scala3/guides/migration/tutorial-sbt.html

sbt プロジェクトを Scala 3 へ移行する際、移行元のプロジェクトは以下の前提条件を満たしている必要があります。

- 最新の Scala 2.13.x
- 最新の sbt 1.5.x

それでは、プロジェクト全体を Scala 3 に移行するために必要な手順を見ていきましょう。

### 1. Check the project prerequisites

- [Prerequisites](https://docs.scala-lang.org/scala3/guides/migration/tutorial-prerequisites.html) に記載されている前提条件を満たしていることを確認する

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

  // 2.13.6 は、`++2.13.6` コマンドでロードすることができる
  // 3.0.0 は、`++3.0.0` コマンドでロードすることができる
  crossScalaVersions ++= Seq("2.13.6", "3.0.0")
  ```
- なお、sbt の `reload` コマンドは、常にデフォルトのバージョンをロードするので注意が必要

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
    ("com.lihaoyi" %% "os-lib" % "0.7.7").cross(CrossVersion.for3Use2_13)
    ```
  - Scala.js の場合
    ```scala
    ("com.lihaoyi" %%% "os-lib" % "0.7.7").cross(CrossVersion.for3Use2_13)
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
  - 詳細は [Compiler Options Lookup Table](https://docs.scala-lang.org/scala3/guides/migration/options-lookup.html) を参照
- 共通的なオプションのリスト、Scala 2.13 固有のオプションのリスト、Scala 3 固有のオプションのリストを作成するにはこのようにする
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
- Scala 3 Migration Mode を有効にするために、`-source:3.0-migration` を追加する
- また、Migration Mode と コードの自動書き換えを最大限に活用するために、`-Xfatal-warnings` は無効にしておく

### 6. Solve the Incompatibilities

- ここでいよいよ Scala 3 でのコンパイルにチャレンジする
  ```
  sbt:example> ++3.0.0
  [info] Setting Scala version to 3.0.0 on 1 project.
  ...
  sbt:example> example / compile
  ...
  sbt:example> example / Test / compile
  ```
- 補足) `example / compile` は、厳密には `example / Compile / compile`
- この時コンパイラは、2つの異なるレベルの診断を行う
  - Migration Warning:
    - これらの警告は、コンパイラの `-rewrite` オプションで自動的にパッチを当てることができる
    - コンパイラが自動的に修正してくれるので、マイグレーションの警告は無視しても構わない
  - Error:
    - コードの一部が非互換性のエラーでコンパイルできなくなった
- エラーの対処
  - 非互換性のエラーについては手動で対処する必要がある
  - 多くの既知の非互換性は、[Incompatibility Table](https://docs.scala-lang.org/scala3/guides/migration/incompatibility-table.html) にエラーの説明と解決策についての提案が記載されている
  - 可能であれば、コードのバイナリ互換性を最もよく維持できる修正方法を見つけるようにする。これは、移行するプロジェクトが公開されたライブラリである場合に特に重要
  - なお、マクロの非互換性は多くのコードを一から書き直さなければならない
    - [Metaprogramming](https://docs.scala-lang.org/scala3/guides/migration/compatibility-metaprogramming.html) 参照
  - 非互換性を修正した後は、Scala 2.13 のテストを実行する
    ```
    sbt:example> ++2.13.6
    [info] Setting Scala version to 2.13.6 on 1 project.
    ...
    sbt:example> example / test
    ...
    [success]
    ```
  - このとき、定期的に変更をコミットすることを検討する
  - すべてのエラーを修正すると、Scala 3 で正常にコンパイルできるようになる
- マイグレーションの警告の対処
  - `source:3.0-migration -rewrite` オプションを付けてコンパイルすることで、自動的にパッチを当てることができる
    ```
    sbt:example> ++3.0.0
    sbt:example> set example / scalacOptions += "-rewrite"
    sbt:example> example / compile
    ...
    [info] [patched file /example/src/main/scala/app/Main.scala]
    [warn] two warnings found
    [success]
    ```
- ここで、`-source:3.0-migration` オプションを削除し、必要に応じて `-Xfatal-warnings` オプションを再度追加する
- このとき `reload` を忘れずに

### 7. Validate the migration

- まれに、異なる暗黙の値が解決され、プログラムの実行時の動作が変わることがある。このようなバグを見逃さないためには、テストが唯一の保証となる
- Scala 2.13 と Scala 3 の両方でテストが通ることを確認する
  ```
  sbt:example> ++2.13.6
  sbt:example> example / test
  ...
  [success]
  sbt:example> ++3.0.0
  sbt:example> example / test
  ...
  [success]
  ```
- CI のパイプラインを設定している場合は、それを Scala 3 用にセットアップする

### 8. Finalize the migration

- これで単一のモジュールの Scala 3 の移行作業は完了
- プロジェクト全体が Scala 3 に完全に移行するまで、各モジュールに対して同じプロセスを繰り返す
- そして、ライブラリをクロスパブリッシュ（cross-publish）するかどうかによって、Scala 2.13 のクロスビルドの設定をそのままにするかどうかを決める
