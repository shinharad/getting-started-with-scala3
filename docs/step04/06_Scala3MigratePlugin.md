# Scala 3 Migrate Plugin {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Scala 3 Migrate Plugin](#scala-3-migrate-plugin-1)
  - [Requirements](#requirements)
  - [Installation](#installation)
  - [Choose a module](#choose-a-module)
  - [Migrate library dependencies](#migrate-library-dependencies)
    - [Macro library](#macro-library)
    - [Compiler plugins](#compiler-plugins)
    - [Libraries that can be updated](#libraries-that-can-be-updated)
    - [Valid libraries](#valid-libraries)
    - [The new build after migrate-libs](#the-new-build-after-migrate-libs)
  - [Migrate scalacOptions](#migrate-scalacoptions)
    - [Non-existing scalacOptions in Scala 3](#non-existing-scalacoptions-in-scala-3)
    - [Renamed scalacOptions](#renamed-scalacoptions)
    - [Plugins specific scalacOptions](#plugins-specific-scalacoptions)
    - [The new build after migrate-scalacOptions](#the-new-build-after-migrate-scalacoptions)
  - [Migrate the syntax](#migrate-the-syntax)
  - [Migrate the code: last command](#migrate-the-code-last-command)
  - [What to do next ?](#what-to-do-next)

<!-- /code_chunk_output -->


## 概要

Scala 3 Migrate Plugin は、ビルド設定とコードを Scala 3 へ移行する際のサポートをしてくれるコンパイラプラグインです。以前確認した sbt Migration Tutorial では、Scala 3 への移行プロセスを確認しましたが、このプラグインを使用することで、より移行が楽になりそうです。

それでは、Scala 3 Migrate Plugin を使用した場合の移行プロセスを確認しましょう。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) の Tooling からこちらを参照します。

- [Scala 3 Migrate Plugin](https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html)

## Scala 3 Migrate Plugin

Scala 3 Migrate Plugin による移行は、以下の独立したステップで構成されており、これからはそれぞれ sbt のコマンドとなっている。

- `migrate-libs` : `libraryDependencies` の更新をサポートする
- `migrate-scalacOptions` : `scalacOptions` の更新をサポートする
- `migrate-syntax` : Scala 2.13 のコードに含まれるいくつかの非互換なシンタックスを修正する
- `migrate` : 必要最低限の inferred types（推論された型）と暗黙的な情報を追加することで、Scala 3 でのコンパイルを試みる

### Requirements

- Scala 2.13（望ましいのは Scala 2.13.5）
- sbt 1.4 以上
- 免責事項: マクロを含むライブラリを移行することはできない
- 現在、すべてのコマンドは Compile configuration で動作する
  - Test のような他の configuration の対応はまだ未対応

### Installation

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#installation

- `addSbtPlugin` で、`sbt-scala3-migrate` を設定する
- sbt 1.4 の場合は、`sbt-dotty` も設定する

### Choose a module

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#choose-a-module

- プロジェクトに複数のモジュールが含まれている場合、最初のステップはどのモジュールを最初に移行するのかを選択する
- Scala3-migrate は、一度に1つのモジュールを操作するため、選択したモジュールが、プロジェクトの aggregate でないことを確認する

### Migrate library dependencies

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#migrate-library-dependencies

- `migrate-libs` コマンドは、`libraryDependencies` を移行する
- sbt コマンドの `migrate-libs [projectId]` で実行する
  ```
  > migrate-libs main
  ```

#### Macro library

- Scala 2.13 のマクロは、Scala 3 のコンパイラでは実行できない。そのため、マクロライブラリに依存している場合は、このライブラリが Scala 3 用に公開されるまで待つ必要がある

#### Compiler plugins

- Scala 2.13 のコンパイラプラグインは、Scala 3 ではサポートしていない
- もしも Scala 3 でサポートしていないコンパイラプラグインが設定されていた場合、コンパイラプラグインなしでコンパイルできるようにコードを修正する必要がある
- 例
  - `better-monadic-for` は、Scala 3 ではサポートしていないので削除して、コンパイラプラグインなしでコンパイルできるようにコードを修正する
  - `kind-projector` は、同等のコンパイラオプションがあるので、`scalacOptions` に追加する

#### Libraries that can be updated

- Scala3-migrate が利用可能なバージョンを提案してくれる
- 例
  - `cats-core` には、`3.0.0-RCx` 用に公開された利用可能なバージョンがあるため、そのバージョンを提案してくれる
  - `scalafix-rules` は、Scala 3 用の利用可能なバージョンはないが、ライブラリにはマクロが含まれていないため、2.13 バージョンを Scala 3 でそのまま利用することができる。ただしその場合、Scala のバージョンを明示する必要がある
    ```scala
    // 移行前
    "ch.epfl.scala" %% "scalafix-rules"      % "0.9.26" % "test"
    // 移行後
    "ch.epfl.scala" %  "scalafix-rules_2.13" % "0.9.26" % "test"
    ```
- 3.0.x のコードには _2.13 アーティファクトを使いたいが、2.13.x には _2.13 を、2.12.x には_2.12 を使い続けたい、ということを表現する場合
   ```scala
   // sbt 1.5 以上の場合
   "ch.epfl.scala"%% "scalafix-rules" % "0.9.26" % Test cross CrossVersion.for3Use2_13
   // sbt 1.3 または 1.4 の場合は
   ("ch.epfl.scala" %% "scalafix-rules" % "0.9.26" % Test).withDottyCompat(scalaVersion.value)
   ```

#### Valid libraries

- そのままの状態で使用できるライブラリは、コンソールに `Valid` と出力される
  ```
  [info] ch.epfl.scala:scalafix-interfaces:0.9.26 -> Valid
  ```
- なお、Java ライブラリの場合も `Valid` と出力される

#### The new build after migrate-libs

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#the-new-build-after-migrate-libs

- `migrate-libs` によって変更された差分を確認する

### Migrate scalacOptions

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#migrate-scalacoptions

- `migrate-scalacOptions` コマンドは、`scalacOptions` を移行する
- sbt コマンドの `migrate-scalacOptions [projectId]` で実行する
  ```
  > migrate-scalacOptions main
  ```
- 移行する `scalacOptions` は [Compiler Options Table](https://scalacenter.github.io/scala-3-migration-guide/docs/compiler-options/compiler-options-table.html) がベースとなっている

#### Non-existing scalacOptions in Scala 3

- Scala 3 で存在しなくなった scalacOptions の移行
- 例
- `-Yrangepos`:
  - いくつかの `scalacOptions` はビルドファイルではなく、sbt プラグインによって提供されている
  - 例えば scala3-migrate は Scala 2 の semanticdb を有効にし、`-Yrangepos` を追加する
  - ここでは sbt が Scala 3 の semanticdb のオプションを適応するので、何もする必要はない
- `-Wunused` :
  - この `scalacOption` は削除する必要がある

#### Renamed scalacOptions

- 名称が変更された `scalacOptions` はリネームするだけ

#### Plugins specific scalacOptions

前回の `migrate-libs` コマンドでは、すでに `kind-projector` を適応させ、`better-monadic-for` を削除したので、前のステップが正しく行われていれば、何も変更する必要はない

#### The new build after migrate-scalacOptions

- `migrate-scalacOptions` によって変更された差分を確認する

### Migrate the syntax

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#migrate-the-syntax

- `migrate-syntax` は、以下の Scalafix のルールを低起用することで、いくつかの非互換性を修正する
  - ProcedureSyntax
  - fix.scala213.ConstructorProcedureSyntax
  - fix.scala213.ExplicitNullaryEtaExpansion
  - fix.scala213.ParensAroundLambda
  - fix.scala213.ExplicitNonNullaryApply
  - fix.scala213.Any2StringAdd
- sbt コマンドの `migrate-syntax [projectId]` で実行する
  ```
  > migrate-syntax main
  ```
- 対象の非互換性については、[Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html) を参照する

### Migrate the code: last command

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migrate-plugin.html#migrate-the-code-last-command

- sbt コマンドの `migrate [projectId]` で実行する
  ```
  > migrate main
  ```
- Scala 3 は、新しい型推論アルゴリズムを使用しているため、Scala 3.0 のコンパイラは、Scala 2.13 で推論された型とは異なる型を推論することができる
- このコマンドの目的は、コードをコンパイルするために必要な型を見つけること
- なお、ライブラリが正しく移行されていない場合、 `migrage [projectId]` を実行しても問題のあるライブラリを報告することはできない
- このツールは最後に Scala 3 で `-rewrite` を使ってコンパイルする

### What to do next ?

- 別のモジュール MODULE2 に対して、Scala3-migrate で移行を行う
- MODULE2 が最後に移行したモジュールに依存している場合は、MODULE2 の `scalacOptions` に `-Ytasty-reader` を追加するか、`MODULE-MIGRATED/scalaVersion := "2.13.5" ` を設定する必要がある
- 移行が完了したら、プラグインから `scala3-migrate` を削除する
