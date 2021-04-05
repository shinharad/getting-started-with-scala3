# Compatibility Reference {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Source Level](#source-level)
- [Classpath Compatibility](#classpath-compatibility)
- [Runtime](#runtime)
- [Metaprogramming](#metaprogramming)
- [Examples](#examples)
  - [Scala 3 モジュールは Scala 2.13 のアーティファクトに依存することができる](#scala-3-モジュールは-scala-213-のアーティファクトに依存することができる)
  - [Scala 2.13 モジュールは Scala 3 のアーティファクトに依存することができる](#scala-213-モジュールは-scala-3-のアーティファクトに依存することができる)
  - [Scala 3 モジュールは Scala 2.13 のマクロに依存できない](#scala-3-モジュールは-scala-213-のマクロに依存できない)
  - [Scala 2.13 モジュールは Scala 3 マクロに依存できない](#scala-213-モジュールは-scala-3-マクロに依存できない)
  - [Scala 3 アーティファクトの共有マクロ API](#scala-3-アーティファクトの共有マクロ-api)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 と Scala 3 の互換性について、Source Level、Classpath Compatibility、Runtime、Metaprogramming の観点で確認します。また、それぞれのバージョンの依存関係についても、公式ドキュメントに分かりやすい例が記載されているので併せて見ていきたいと思います。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Compatibility Reference](https://scalacenter.github.io/scala-3-migration-guide/docs/general/compatibility.html)


## Source Level

- Scala 2.13 の大部分は、Scala3 でも互換性があるが、すべてではない
- いくつかの構文は単純化されたり、制限されたり、完全に削除されたりしている
- これらの決定は正当な理由があってなされたものであり、適切な回避策が可能であることを考慮されたもの
- いずれにしても、すべての非互換性に対応したクロスコンパイルソリューションがあるため、移行は簡単かつスムーズに行える
  - 非互換性については、この後の Incompatibility Table で確認する
- Scala 2.13 のソースコードは、Scala 3 Migration Mode や各種ツールを使うことで、Scala3 のソースコードへ変換することができる

## Classpath Compatibility

- コンパイラが、型やメソッドのシグネチャなどの情報をクラスファイルから読み取る際のフォーマットが、Scala2 と Scala3 で異なる
  - Scala2 では、シグネチャは Pickle format で格納されている
  - Scala3 では、シグネチャのレイアウトよりも多くの機能を持つ TASTy format で格納されている
- Scala3 コンパイラは、Scala 2.13 の Pickle format と TASTy format の両方を読み取ることができる
- TASTy reader
  - Scala 2.13.5 では、Scala3 ライブラリの利用を可能にする TASTy reader が同梱されていて、従来の機能に加え、以下の新機能もサポートしている
    - Enumerations
    - Intersection types
    - Opaque type aliases
    - Type lambdas
    - New syntax for contextual abstractions
    - Inheritance of open classes and super traits
    - Exported definitions
  - 以下の機能は限定的にサポートしている
    - Top level definitions
    - Extension methods
  - それ以外はサポートしていない
    - Context functions
    - Polymorphic function types
    - Trait parameters
    - @static annotation
    - @alpha annotation
    - Functions and Tuples larger than 22 parameters
    - Reference to scala.Tuple and scala.*:
    - Match types
    - Union types
    - Multiversal equality constraints unless explicit
    - Inline functions (including Scala 3 macros)
    - Subtype kind polymorphism (upper bound scala.AnyKind)
- Scala 2.13.5 のモジュールが Scala 3.0.0-RC2 のモジュールに依存することもできるし、その逆も可能
- 後方互換性と前方互換性があるので、移行は徐々に行える
- 依存関係にあるものが Scala3 に完全に移植されていなくても、アプリケーションは Scala3 に移行できる

**:warning: TASTy reader は、Scala3 のすべての機能をサポートしているわけではないので注意が必要**


## Runtime

- Scala 2.13 と Scala3 は同じ ABI (Application Binary Interface) を共有する
- ABI は、Scala のコードをバイトコード、または Scala.js の IR (Intermediate Representation) で表現したもので、実行時の動作に大きく左右する
  - Scala.js の IR は、略して "SJSIR" と呼ぶ
  - 詳しくは [Scala.js IR](http://lampwww.epfl.ch/~doeraene/thesis/sjsir-semantics/) を参照
- 推論された型と implicit の解決策が同じであれば、コードは同じバイトコードを生成し、最終的には実行時の動作も同じになる
- ABI を共有することで、
  - Scala 2.13 と Scala3 のクラスファイルを同じ JVM クラスローダで読み込むことができる
  - Scala 2.13 と Scala3 の sjsir ファイルを Scala.js リンカでリンクすることができる
- これにより、Scala 2.13 から Scala3 への移行は、ランタイムのクラッシュやパフォーマンスの面で非常に安全になった

## Metaprogramming

- Scala3 のマクロは、TASTy format をベースにしている
- Scala3 のマクロは、Scala3 コンパイラの将来のバージョンとは互換性があるが、TASTy を完全にサポートしていない Scala 2.13 コンパイラとは互換性がない
- Scala2 と Scala3 の両方で、共通のマクロAPIを公開するためには、両方の実装を提供する必要がある

## Examples

ここからは、以下のリンク先の図やコードを一緒に見ていきましょう。

https://scalacenter.github.io/scala-3-migration-guide/docs/general/compatibility.html#examples

※以降は、sbt 1.5.0 以上が必須となります

### Scala 3 モジュールは Scala 2.13 のアーティファクトに依存することができる

- そもそも Scala 3 の Standard Library は Scala 2.13 ライブラリで、Scala 2.13 でコンパイルしたものをそのまま使用しているものすらある

### Scala 2.13 モジュールは Scala 3 のアーティファクトに依存することができる

- Scala 2.13.4 は、コンパイラオプションの `-Ytasty-reader` で TASTy reader を有効にすることで、Scala 3 ライブラリに依存することができる

### Scala 3 モジュールは Scala 2.13 のマクロに依存できない

- Scala 2.13 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 2.13 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 2.13 マクロの実行を伴う Scala 2.13 アーティファクト には依存できる

### Scala 2.13 モジュールは Scala 3 マクロに依存できない

- Scala 3 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 3 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 3 マクロの実行を伴う Scala 3 アーティファクト には依存できる

### Scala 3 アーティファクトの共有マクロ API

- Scala 3 のアーティファクトは、Scala 2.13 と Scala 3 のマクロを両方実装できる
- Scala 2.13 のアーティファクトは、Scala 2.13 でコンパイルされている必要があるため、別のモジュールとして提供する必要がある
