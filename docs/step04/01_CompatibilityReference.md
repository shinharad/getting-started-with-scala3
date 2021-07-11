# Compatibility Reference {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Source Level](#source-level)
- [Classpath Level](#classpath-level)
  - [The Scala 3 Unpickler](#the-scala-3-unpickler)
    - [A Scala 3 module can depend on a Scala 2.13 artifact](#a-scala-3-module-can-depend-on-a-scala-213-artifact)
  - [The Scala 2.13 TASTy Reader](#the-scala-213-tasty-reader)
    - [A Scala 2.13 module can depend on a Scala 3 artifact](#a-scala-213-module-can-depend-on-a-scala-3-artifact)
  - [Intercompatibility Overview](#intercompatibility-overview)
- [Runtime](#runtime)
- [Metaprogramming](#metaprogramming)
  - [Macro Dependencies](#macro-dependencies)
  - [Porting the Macro Ecosystem](#porting-the-macro-ecosystem)
  - [Rewriting a Macro](#rewriting-a-macro)
  - [Cross-building a Macro Library](#cross-building-a-macro-library)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 と Scala 3 の互換性について、Source Level、Classpath Level、Runtime、Metaprogramming の観点で確認します。また、それぞれのバージョンの依存関係についても、公式ドキュメントに分かりやすい例が記載されているので併せて見ていきたいと思います。

## ドキュメント参照先

[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) の Compatibility Reference からこちらを参照します。

- [Source Level](https://docs.scala-lang.org/scala3/guides/migration/compatibility-source.html)
- [Classpath Level](https://docs.scala-lang.org/scala3/guides/migration/compatibility-classpath.html)
- [Runtime](https://docs.scala-lang.org/scala3/guides/migration/compatibility-runtime.html)
- [Metaprogramming](https://docs.scala-lang.org/scala3/guides/migration/compatibility-metaprogramming.html)

## Source Level

https://docs.scala-lang.org/scala3/guides/migration/compatibility-source.html

- Scala 2.13 の大部分は、Scala 3 でも互換性があるが、すべてではない
- いくつかの構文は単純化されたり、制限されたり、完全に削除されたりしている
- これらの決定は正当な理由があってなされたものであり、適切な回避策が可能であることを考慮されたもの
- いずれにしても、すべての非互換性に対応したクロスコンパイルソリューションがあるため、移行は簡単かつスムーズに行える
  - 非互換性については、[Incompatibility Table](https://docs.scala-lang.org/scala3/guides/migration/incompatibility-table.html) を参照する
- ただし、Scala 2 の experimental なマクロは例外で、Scala 3 では新しいメタプログラミングのフレームワークが追加されたが、これらは互換性がない
- Scala 2.13 のソースコードは、Scala 3 Migration Mode や各種ツールを使うことで、Scala 3 のソースコードへ変換することができる
- Scala 2.13 のソースコードを Scala 3 へ移行したとしても、その Scala 3 のアーティファクトを、別の Scala 2.13 のモジュールから依存させる方法が提供されている
  - TASTy readerについては後述する

## Classpath Level

https://docs.scala-lang.org/scala3/guides/migration/compatibility-classpath.html

- コードの中で public な型や項（terms）を使用したり、別のモジュールやライブラリで定義されている public なメソッドを呼び出すことができるが、これはコンパイラフェーズの type checker が、コードの意味的な整合性を検証するために、class ファイルからそれらのシグネチャを読み取ることで実現している
- このシグネチャの格納フォーマットが Scala 2 と Scala 3 で異なる
  - Scala 2 では、シグネチャは Pickle format と呼ばれる専用のフォーマットで格納されている
  - Scala 3 では、シグネチャのレイアウトよりもはるかに多くの情報を持つ TASTy format で格納されている

### The Scala 3 Unpickler

- Scala 3 コンパイラは、Scala 2.13 の Pickle format と TASTy format の両方を読み取ることができる

#### A Scala 3 module can depend on a Scala 2.13 artifact

- Scala 3 モジュールは Scala 2.13 のアーティファクトに依存することができる
- そもそも Scala 3 の Standard Library は Scala 2.13 ライブラリを使用している
- これは、sbt 1.5.0 以上が必須となる
- sbt マルチプロジェクトの場合は、このようにできる
  ```scala
  lazy val foo = project.in(file("foo"))
    .settings(scalaVersion := "3.0.1")
    .dependsOn(bar)

  lazy val bar = project.in(file("bar"))
    .settings(scalaVersion := "2.13.6")
  ```
- または、Scala 2.13 ライブラリに依存する場合は、`CrossVersion.for3Use2_13` を使用する
  ```scala
  lazy val foo = project.in(file("foo"))
    .settings(
      scalaVersion := "3.0.1",
      libraryDependencies += ("org.bar" %% "bar" % "1.0.0").cross(CrossVersion.for3Use2_13)
      // or
      // libraryDependencies += "org.bar" % "bar_2.13" % "1.0.0"
    )
  ```

### The Scala 2.13 TASTy Reader

- Scala 2.13 から Scala 3 に依存させることができる、TASTy reader が Scala 2.13.4 から同梱されている
- TASTy reader は、コンパイラオプションの `-Ytasty-reader` を有効にすることで利用できる
- ただし、Scala 2.13 から Scala 3.0.1 に依存させるには、tasty の互換性の問題で、Scala 2.13.6 以上が必要
  - [Scala 2.13.6 planning](https://contributors.scala-lang.org/t/scala-2-13-6-planning/4975)
- TASTy reader は、Scala 3 の以下の機能をサポートしている（すべてではない）
  - Enumerations
  - Intersection Types
  - Opaque Type Aliases
  - Type Lambdas
  - Contextual Abstractions (new syntax)
  - Open Classes (and inheritance of super traits)
  - Export Clauses
- 以下の機能は限定的にサポートしている
  - Top level definitions
  - Extension methods
- それ以外はサポートしていない
  - Context Functions
  - Polymorphic Function types
  - Trait parameters
  - `@static` Annotation
  - `@alpha` Annotation
  - Functions and Tuples larger than 22 parameters
  - Match Types
  - Union Types
  - Multiversal Equality (unless explicit)
  - Inline (including Scala 3 macros)
  - Kind Polymorphism (the `scala.AnyKind` upper bound)

#### A Scala 2.13 module can depend on a Scala 3 artifact

- Scala 2.13 で `-Ytasty-reader` を有効にして TASTy reader を使用することで、Scala 3 アーティファクトに依存させることができる
  ```scala
  lazy val foo = project.in.file("foo")
    .settings(
      scalaVersion := "2.13.6",
      scalacOptions += "-Ytasty-reader"
    )
    .dependsOn(bar)

  lazy val bar = project.in(file("bar"))
    .settings(scalaVersion := "3.0.1")
  ```
- または、Scala 3 ライブラリの場合は `CrossVersion.for2_13Use3` を使用する
  ```scala
  lazy val foo = project.in.file("foo")
    .settings(
      scalaVersion := "2.13.6",
      scalacOptions += "-Ytasty-reader",
      libraryDependencies += ("org.bar" %% "bar" % "1.0.0").cross(CrossVersion.for2_13Use3)
      // or
      // libraryDependencies += "org.bar" % "bar_3" % "1.0.0"
    )
  ```

### Intercompatibility Overview

- つまり、後方互換性と前方互換性があるので、移行は徐々に行うことができる
- 大規模な Scala アプリケーションであれば、ライブラリの依存関係がまだ移行されていなくても（マクロライブラリを除く）、モジュールを1つずつ移行することができる
- `app_2.13` -> `core_3` -> `lib_2.13` という依存関係にもできる
  - すべてのライブラリが単一のバイナリバージョンに解決されている限り、これは許可される
  - 同じクラスパスに `lib-foo_3` と `lib-foo_2.13` を置くことはできない
- 逆のパターン、`app_3` -> `core_2.13` -> `lib_3` でも可能
- ただし、ライブラリ作成者は、公開しているライブラリで上記のような依存関係にしてしまうと、ライブラリの利用者のクラスパスに同じ `foo` ライブラリの2つの相反するバージョン `foo_2.13` と `foo_3` を持つことになってしまい、安全ではないので避けたい

## Runtime

https://docs.scala-lang.org/scala3/guides/migration/compatibility-runtime.html

- Scala 2.13 と Scala 3 は同じ ABI (Application Binary Interface) を共有する
- ABI は、Scala のコードをバイトコード、または Scala.js の IR (Intermediate Representation) で表現したもので、実行時の動作に大きく左右する
  - Scala.js の IR は、略して "SJSIR" と呼ぶ
  - 詳しくは [Scala.js IR](http://lampwww.epfl.ch/~doeraene/thesis/sjsir-semantics/) を参照
- 推論された型と implicit の解決策が同じであれば、コードは同じバイトコードを生成し、最終的には実行時の動作も同じになる
- ABI を共有することで、
  - Scala 2.13 と Scala 3 のクラスファイルを同じ JVM クラスローダで読み込むことができる
  - Scala 2.13 と Scala 3 の sjsir ファイルを Scala.js リンカでリンクすることができる
- これにより、Scala 2.13 から Scala 3 への移行は、ランタイムのクラッシュやパフォーマンス面で非常に安全になった
- Scala 3 のランタイム動作は、一見すると Scala 2.13 と比較して良くも悪くもなっていないが、いくつかの新機能はプログラムを最適化するのに役立つ
  - Opaque Type Aliases
  - Inline Methods
  - @threadUnsafe annotation

## Metaprogramming

https://docs.scala-lang.org/scala3/guides/migration/compatibility-metaprogramming.html

- マクロメソッドの呼び出しは、コンパイラフェーズの macro expansion で実行され、プログラムの一部であれ抽象構文木を生成する
- Scala 2.13 のマクロAPIは、Scala 2.13 のコンパイラの内部と密接に結びついているため、Scala 3 のコンパイラが Scala 2.13 のマクロを展開することはできない
- 対称的に、Scala 3 では、安定性を重視したメタプログラミングの新しいアプローチを導入している
- Scala 3 のマクロ、そして `inline` メソッドは、Scala 3 コンパイラの将来のバージョンと互換性がある
- これは、Scala 2.13 のマクロの実装を一から書き直さなければならないことを意味する
  - Scala 2 と Scala 3 の両方で、共通のマクロAPIを公開するためには、両方の実装を提供する必要がある

### Macro Dependencies

- Scala 3 のモジュールは、マクロ定義を含む Scala 2.13 のアーティファクトに依存することはできるが、コンパイラはそのマクロを展開することができないので、エラーになる
  ```
  -- Error: /src/main/scala/example/Example.scala:10:45
  10 |  val documentFormat = Json.format[Document]
      |                            ^
      |Scala 2 macro cannot be used in Scala 3. See https://dotty.epfl.ch/docs/reference/dropped-features/macros.html
      |To turn this error into a warning, pass -Xignore-scala2-macros to the compiler
  ```
- `-Xignore-scala2-macros` を使用すると、コードの型チェックには役立つが、不完全な class ファイルが生成されることに注意が必要
- このエラーがプロジェクトに表示された場合、マクロアーティファクトの Scala 3 コンパイルバージョンにアップグレードする以外の選択肢はない

### Porting the Macro Ecosystem

- これまで Scala コミュニティは、experimental ではあるが、コード生成、最適化、人間工学に基づいた DSL など、様々な方法で Scala 2 のマクロ機能を採用してきた
- 現在、エコシステムの大部分は、外部ライブラリで定義された Scala 2.13 のマクロに依存している
- それらのライブラリを特定して移行することは、エコシステムを前進させる鍵となる
- 多くのオープンソースのマクロライブラリの移行状況は、以下のページで確認することができる
  - https://scalacenter.github.io/scala-3-migration-guide/docs/macros/macro-libraries.html

### Rewriting a Macro

新しいメタプログラミングの機能は、以下のように構成されており、Scala 2 とは全く異なっている

- [Inline Methods](https://docs.scala-lang.org/scala3/guides/macros/inline.html)
- [Compile-time operations](https://docs.scala-lang.org/scala3/guides/macros/compiletime.html)
- [Macros](https://docs.scala-lang.org/scala3/guides/macros/macros.html)
- [Quoted code](https://docs.scala-lang.org/scala3/guides/macros/quotes.html)
- [Reflection over Abstract Syntax Trees (AST)](https://docs.scala-lang.org/scala3/guides/macros/reflection.html)

マクロの再実装に取り掛かる前に、Scala 3 の新機能でサポートされるかどうかを確認する必要がある。

- `inline` や `scala.compiletime` を使ってロジックを再実装することはできるか？
- よりシンプルで安全な式ベースのマクロを使うことはできるか？
- 本当に AST にアクセスする必要があるのか？
- [match types](https://dotty.epfl.ch/docs/reference/new-types/match-types.html) 結果型として使用できるか？

新しいメタプログラミングの概念は、[Macro Tutorial](https://docs.scala-lang.org/scala3/guides/macros/) で学ぶことができる。

### Cross-building a Macro Library

マクロライブラリを Scala 2.13 と Scala 3 の両方で利用できるようにしたい場合、2つの異なるアプローチがある
- [Cross-Building a Macro Library](https://docs.scala-lang.org/scala3/guides/migration/tutorial-macro-cross-building.html)
  - 既存の Scala 2.13 のマクロライブラリをクロスビルドして、Scala 3 と Scala 2.13 の両方で利用できるようにする
- [Mixing Scala 2.13 and Scala 3 Macros](https://docs.scala-lang.org/scala3/guides/migration/tutorial-macro-mixing.html)
  - Scala 2.13 と Scala 3 のマクロを1つのアーティファクトに混在させる
