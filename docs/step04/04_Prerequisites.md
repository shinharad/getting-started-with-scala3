# Prerequisites {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Prerequisites](#prerequisites-1)
  - [Macro dependencies](#macro-dependencies)
  - [Compiler plugins](#compiler-plugins)
    - [SemanticDB](#semanticdb)
    - [Scala.js](#scalajs)
    - [Scala Native](#scala-native)
    - [Kind Projector](#kind-projector)
  - [Run-time reflection](#run-time-reflection)

<!-- /code_chunk_output -->

## 概要

次に、Scala 2.13 のプロジェクトを Scala 3 へ移行する前に満たさなければならない前提条件を確認しておきましょう。

## ドキュメント参照先

[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) の Migration Tutorial からこちらを参照します。

- [Prerequisites](https://docs.scala-lang.org/scala3/guides/migration/tutorial-prerequisites.html)


## Prerequisites

https://docs.scala-lang.org/scala3/guides/migration/tutorial-prerequisites.html

Scala 2.13 と Scala 3 の間には相互運用性があるので、Scala 3 への移行は容易です。しかし、Scala 2.13 のプロジェクトを Scala 3 へ移行する前に満たさなければならない前提条件がいくつかあります。

- Scala 3 をまだサポートしていないマクロライブラリに依存してはいけない
  - マクロライブラリとは、マクロを使用したメソッドを公開している Scala ライブラリのことを指す
- Scala 3 で同等のものがないコンパイラプラグインを使用してはいけない
- `scala-reflect` に依存してはいけない

ここでは、これらの前提条件をチェックする方法と、もしそれらが満たされていない場合の対処法について見ていきます。


### Macro dependencies

- Scala 3 のコンパイラは、Scala 2.13 のマクロを展開することができない
- そのため、自分のプロジェクトがまだ Scala 3 へ移行していないマクロライブラリに依存していないかどうかを確認する必要がある
- 多くのマクロライブラリの移行状況は、[Scala Macro Libraries](https://scalacenter.github.io/scala-3-migration-guide/docs/macros/macro-libraries.html) で確認ができる
- 自分のプロジェクトが依存しているマクロライブラリのそれぞれが、クロスビルドバージョン（Scala 2.13 と Scala 3 の両方で利用可能なバージョン）へアップデートする必要がある
- 例えば、scalatest の場合
  ```scala
  // 移行前の scalatest 3.0.9 だった場合、Scala 3 をサポートしていないので、
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7"

  // Scala 2.13 と Scala 3 を cross-published している scalatest 3.2.7 に上げる
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7"
  ```

### Compiler plugins

- Scala 2 のコンパイラプラグインは、Scala 3 とは互換性がない
- コンパイラプラグインは通常、`build.sbt` の中で、以下のいずれかの設定を行う
  ```scala
  // build.sbt
  libraryDependencies +=
    compilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
  ```
- コンパイラプラグインの中には、sbt プラグインによって自動的に追加されるものがある
- 設定されているすべてのコンパイラープラグインは、このようにして確認ができる
  ```
  sbt:example> show example / Compile / scalacOptions
  [info] * -Xplugin:target/compiler_plugins/wartremover_2.13.5-2.4.12.jar
  [info] * -Xplugin:target/compiler_plugins/semanticdb-scalac_2.13.5-4.3.20.jar
  [info] * -Yrangepos
  [info] * -P:semanticdb:targetroot:/example/target/scala-2.13/meta
  ```
- 上の例では、wartremover と semanticdb という2つのコンパイラプラグインが使用されていることがわかる。それぞれのプラグインについて、代替案があるかどうか確認するか、無効にする必要がある

以降は比較的ポピュラーなコンパイラプラグインの代替案について確認します。

#### SemanticDB

Scala 3 コンパイラに SemanticDB のサポートが追加されたので、以下のコンパイラオプションで有効にすることができる。

- `-Ysemanticdb` オプションは、semanticDB ファイルの生成を有効にする
- `-semanticdb-target` オプションは、semanticDB ファイルの出力先ディレクトリを指定することができる

なお、sbt は、`semanticdbEnabled := true` という設定だけで、SemanticDBを設定することができます。

#### Scala.js

- Scala.js のプロジェクトをコンパイルするには、`sbt-scalajs` プラグインの 1.5.0 以上を使用する
  ```scala
  // project/plugins.sbt
  addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.5.0")
  ```

#### Scala Native

- Scala Native はまだ Scala 3 をサポートしていない
- もしもプロジェクトが Scala Native にクロスビルドしている場合、Scala 3 に移行することはできるが、Scala Native プラットフォーム用にコンパイルすることはできない

#### Kind Projector

- kind-projector のシンタックスは、Scala 3 では `-Ykind-projector` オプションでサポートされている
- ただし、多くの場合は Scala 3 の新機能で代替することができる
  - Type Lambdas
  - Polymorphic Functions
  - Kind Polymorphism
- Kind Projector の移行については、[Kind Projector Migration](https://docs.scala-lang.org/scala3/guides/migration/plugin-kind-projector.html) で詳しく解説されている

### Run-time reflection

- `scala-reflect` は、Scala 3 には存在しない Scala 2 コンパイラの内部構造を公開しているため、Scala 3 には移行できない
- 自分のプロジェクトが、`scala-reflect` に依存していたり、`Manifest` クラスのインスタンスを使用している場合は、Scala 3 コンパイラではコンパイルできない
- 代替手段としては、Java のリフレクションや Scala 3 のメタプログラミング機能を使って、対象のコードを再実装する必要がある
- `scala-reflect` がクラスパスに一時的に追加されている場合は、恐らくそれをもたらす依存関係をアップグレードする必要がある

