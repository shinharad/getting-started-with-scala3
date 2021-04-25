# Compatibility Reference {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Source Level](#source-level)
- [Classpath Level](#classpath-level)
  - [The Scala 3 Unpickler](#the-scala-3-unpickler)
    - [Using a Scala 2.13 library in Scala 3](#using-a-scala-213-library-in-scala-3)
  - [The Scala 2.13 TASTy Reader](#the-scala-213-tasty-reader)
    - [Using a Scala 3 library in a Scala 2.13](#using-a-scala-3-library-in-a-scala-213)
  - [Intercompatibility Overview](#intercompatibility-overview)
- [Runtime](#runtime)
- [Metaprogramming](#metaprogramming)
  - [Macro Dependencies](#macro-dependencies)
    - [A Scala 3 module cannot depend on a Scala 2.13 macro](#a-scala-3-module-cannot-depend-on-a-scala-213-macro)
    - [A Scala 2.13 module cannot depend on a Scala 3 macro](#a-scala-213-module-cannot-depend-on-a-scala-3-macro)
  - [Before Rewriting a Macro](#before-rewriting-a-macro)
  - [Cross-building a Macro Library](#cross-building-a-macro-library)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 と Scala 3 の互換性について、Source Level、Classpath Level、Runtime、Metaprogramming の観点で確認します。また、それぞれのバージョンの依存関係についても、公式ドキュメントに分かりやすい例が記載されているので併せて見ていきたいと思います。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) の Compatibility Reference からこちらを参照します。

- [Source Level](https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/source.html)
- [Classpath Level](https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/classpath.html)
- [Runtime](https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/runtime.html)
- [Metaprogramming](https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/metaprogramming.html)

## Source Level

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/source.html

- Scala 2.13 の大部分は、Scala 3 でも互換性があるが、すべてではない
- いくつかの構文は単純化されたり、制限されたり、完全に削除されたりしている
- これらの決定は正当な理由があってなされたものであり、適切な回避策が可能であることを考慮されたもの
- いずれにしても、すべての非互換性に対応したクロスコンパイルソリューションがあるため、移行は簡単かつスムーズに行える
  - 非互換性については、[Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html) を参照する
- Scala 2.13 のソースコードは、Scala 3 Migration Mode や各種ツールを使うことで、Scala 3 のソースコードへ変換することができる

## Classpath Level

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/classpath.html

- コンパイラが、型やメソッドのシグネチャなどの情報をクラスファイルから読み取る際のフォーマットが、Scala 2 と Scala 3 で異なる
  - Scala 2 では、シグネチャは Pickle format と呼ばれる専用のフォーマットで格納されている
  - Scala 3 では、シグネチャのレイアウトよりもはるかに多くの情報を持つ TASTy format で格納されている

### The Scala 3 Unpickler

- Scala 3 コンパイラは、Scala 2.13 の Pickle format と TASTy format の両方を読み取ることができる

#### Using a Scala 2.13 library in Scala 3

- Scala 3 モジュールは Scala 2.13 のアーティファクトに依存することができる
- これは、sbt 1.5.0 以上が必須となる
- そもそも Scala 3 の Standard Library は Scala 2.13 ライブラリを使用している
- ここはガイドを直接見た方が良さそう
  - https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/classpath.html#using-a-scala-213-library-in-scala-3

### The Scala 2.13 TASTy Reader

- Scala 2.13.5 では、Scala 3 ライブラリの利用を可能にする TASTy reader が同梱されていて、従来の機能に加え、以下の新機能もサポートしている
  - Enumerations
  - Intersection types
  - Opaque type aliases
  - Type lambdas
  - Contextual Abstractions (new syntax)
  - Open Classes (and inheritance of super traits)
  - Export Clauses
- 以下の機能は限定的にサポートしている
  - Top level definitions
  - Extension methods
- それ以外はサポートしていない
  - Context functions
  - Polymorphic function types
  - Trait parameters
  - `@static` annotation
  - `@alpha` annotation
  - Functions and Tuples larger than 22 parameters
  - Reference to `scala.Tuple` and `scala.*:`
  - Match types
  - Union types
  - Multiversal Equality (unless explicit)
  - Inline (including Scala 3 macros)
  - Kind Polymorphism (the scala.AnyKind upper bound)

#### Using a Scala 3 library in a Scala 2.13

- 2.13.5 以降の Scala 2.13 モジュールは、`-Ytasty-reader` オプションで Tasty reader を有効にすることで、Scala 3 ライブラリに依存することができる
- ここもガイドを直接見た方が良さそう
  - https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/classpath.html#using-a-scala-3-module-in-a-scala-3
  - (ガイドのタイトルが間違えているのでリンクが変わる可能性がある)

### Intercompatibility Overview

- Scala 2.13.5 のモジュールが Scala 3.0.0-RC3 のモジュールに依存することもできるし、またその逆も可能
  - ただし、Scala 2.13.5 の TASTy reader で読み込める Scala 3 の機能には制限がある
- 後方互換性と前方互換性があるので、移行は徐々に行える
- 依存関係にあるものが Scala 3 に完全に移植されていなくても、アプリケーションは Scala 3 に移行できる

## Runtime

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/runtime.html

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

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/metaprogramming.html

- Scala 2.13 のマクロは、Scala 2.13 のコンパイラの内部と密接に結びついているため、Scala 3 のコンパイラが Scala 2.13 のマクロを拡張することはできない
- 対照的に、Scala 3 のマクロは、Scala 3 コンパイラの将来のバージョンと互換性がある
- これは、Scala 2.13 のマクロの実装を一から書き直さなければならないことを意味する
  - Scala 2 と Scala 3 の両方で、共通のマクロAPIを公開するためには、両方の実装を提供する必要がある

### Macro Dependencies

#### A Scala 3 module cannot depend on a Scala 2.13 macro

- Scala 3 モジュールは、Scala 2.13 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 2.13 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 2.13 マクロの実行を伴う Scala 2.13 アーティファクト には依存できる
- ここもガイドを直接見た方が良さそう
  - https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/metaprogramming.html#a-scala-3-module-cannot-depend-on-a-scala-213-macro

#### A Scala 2.13 module cannot depend on a Scala 3 macro

- Scala 2.13 モジュールは、Scala 3 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 3 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 3 マクロの実行を伴う Scala 3 アーティファクト には依存できる
- ここもガイドを直接見た方が良さそう
  - https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/metaprogramming.html#a-scala-213-module-cannot-depend-on-a-scala-3-macro

### Before Rewriting a Macro

マクロの再実装に取り掛かる前に、Scala 3 の新機能でサポートされるかどうかを確認する必要がある。

- Scala 3 の新機能を使って、マクロのロジックをエンコードすることはできるか？
- _match types_ を使って、マクロのインターフェースを再実装することはできるか？
- `inline` や `scala.compiletime` のメタプログラミング機能を使って、ロジックを再実装することはできるか？
- よりシンプルで安全な式ベースのマクロを使ってマクロを実装することはできるか？
- Abstract Syntax Trees へのアクセスは本当に必要か？

新しいメタプログラミングの概念は、[Macro Tutorial](https://docs.scala-lang.org/scala3/guides/macros/) で学ぶことができる。

- Inline
- Compile-time operations
- Macros
- Quoted code
- AST Reflection

### Cross-building a Macro Library

マクロライブラリを Scala 2.13 と Scala 3 の両方で利用できるようにしたい場合、2つの異なるアプローチがある
- [Cross-Building a Macro Library](https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/macro-cross-building.html)
  - 既存の Scala 2.13 のマクロライブラリをクロスビルドして、Scala 3 と Scala 2.13 の両方で利用できるようにする
- [Mixing Scala 2.13 and Scala 3 Macros](https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/macro-mixing.html)
  - Scala 2.13 と Scala 3 のマクロを1つのアーティファクトに混在させる
