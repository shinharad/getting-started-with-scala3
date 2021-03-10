# Compatibility Reference {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Source Level](#source-level)
- [Compile Time](#compile-time)
- [Runtime](#runtime)
- [Metaprogramming](#metaprogramming)
- [Examples](#examples)
  - [Scala 3.0 モジュールは Scala 2.13 のアーティファクトに依存することができる](#scala-30-モジュールは-scala-213-のアーティファクトに依存することができる)
  - [Scala 2.13 モジュールは Scala 3.0 のアーティファクトに依存することができる](#scala-213-モジュールは-scala-30-のアーティファクトに依存することができる)
  - [Scala 3.0 モジュールは Scala 2.13 のマクロに依存できない](#scala-30-モジュールは-scala-213-のマクロに依存できない)
  - [Scala 2.13 モジュールは Scala 3.0 マクロに依存できない](#scala-213-モジュールは-scala-30-マクロに依存できない)
  - [Scala 3 アーティファクトの共有マクロ API](#scala-3-アーティファクトの共有マクロ-api)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 と Scala 3.0 の互換性について、Source Level、Compile Time、Runtime、Metaprogramming の観点で確認します。また、それぞれのバージョンの依存関係についても、公式ドキュメントに分かりやすい例が記載されているので併せて見ていきたいと思います。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Compatibility Reference](https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility.html)


## Source Level

- Scala 2.13 の大部分は、Scala 3.0 でも互換性があるが、すべてではない
- いくつかの構文は単純化されたり、制限されたり、完全に削除されたりしている
- これらの決定は正当な理由があってなされたものであり、適切な回避策が可能であることを考慮されたもの
- いずれにしても、すべての非互換性に対応したクロスコンパイルソリューションがあるため、移行は簡単かつスムーズに行える
  - 非互換性については、この後の Incompatibility Table で確認する
- Scala 2.13 のソースコードは、Scala 3 Migration Mode や各種ツールを使うことで、Scala3 のソースコードへ簡単に変換することができる

## Compile Time

- コンパイラが、型やメソッドのシグネチャなどの情報をクラスファイルから読み取る際のフォーマットが、Scala2 と Scala3 で異なる
  - Scala2 では、シグネチャは Pickle format で格納されている
  - Scala3 では、シグネチャのレイアウトよりも多くの機能を持つ TASTy format で格納されている
- Scala3 コンパイラは、Scala 2.13 の Pickle format と TASTy format の両方を読み取ることができる
- Scala 2.13.4 では、Scala 3.0 ライブラリの利用を可能にする TASTy reader が追加されていて、従来のすべての機能に加え、以下の新機能もサポートしている
  - Enums
  - Intersection types
  - Higher-kinded type lambdas
  - Scala 3 extension methods
  - New syntax for context abstraction
  - Inheritance of open classes and super traits
  - Exported definitions
- 後方互換性と前方互換性があるので、移行は徐々に、そしてどのような順番でも可能
- 例えばモジュール間の依存関係をこんな感じにできる
  ```plantuml
  left to right direction
  (Scala 2.13.4)-->(Scala 3.0 アーティファクト)
  (Scala 2.13.4)-->(Scala 2.13.4 アーティファクト)
  ```

**:warning: TASTy reader は、Scala3 のすべての機能をサポートしているわけではないので注意が必要**


## Runtime

- Scala 2.13 と Scala 3.0 は同じ ABI (Application Binary Interface) を共有する
- ABI は、Scala のコードをバイトコード、または Scala.js の IR (Intermediate Representation) で表現したもので、実行時の動作に大きく左右する
  - Scala.js の IR は、略して "SJSIR" と呼ぶ
  - 詳しくは [Scala.js IR](http://lampwww.epfl.ch/~doeraene/thesis/sjsir-semantics/) を参照
- 推論された型と implicit の解決策が同じであれば、コードは同じバイトコードを生成し、最終的には実行時の動作も同じになる
- ABI を共有することで、
  - Scala 2.13 と Scala 3.0 のクラスファイルを同じ JVM クラスローダで読み込むことができる
  - Scala 2.13 と Scala 3.0 の sjsir ファイルを Scala.js リンカでリンクすることができる

## Metaprogramming

- Scala3 のマクロは、TASTy format をベースにしている
- Scala3 のマクロは、Scala3 コンパイラの将来のバージョンとは互換性があるが、TASTy を完全にサポートしていない Scala 2.13 コンパイラとは互換性がない
- Scala2 と Scala3 の両方で、共通のマクロAPIを公開するためには、両方の実装を提供する必要がある

## Examples

ここからは、以下のリンク先の図やコードを一緒に見ていきましょう。

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility.html#examples

### Scala 3.0 モジュールは Scala 2.13 のアーティファクトに依存することができる

- そもそも Scala 3.0 の Standard Library は Scala 2.13 ライブラリで、Scala 2.13 でコンパイルしたものをそのまま使用しているものすらある

### Scala 2.13 モジュールは Scala 3.0 のアーティファクトに依存することができる

- Scala 2.13.4 は、コンパイラオプションの `-Ytasty-reader` で TASTy reader を有効にすることで、Scala 3.0 ライブラリに依存することができる

### Scala 3.0 モジュールは Scala 2.13 のマクロに依存できない

- Scala 2.13 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 2.13 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 2.13 マクロの実行を伴う Scala 2.13 アーティファクト には依存できる

### Scala 2.13 モジュールは Scala 3.0 マクロに依存できない

- Scala 3.0 のマクロを使用しているアーティファクトに直接依存することはできない
- しかし、そのアーティファクトが依存している Scala 3.0 アーティファクトに関しては、マクロを使用していたとしても依存することができる
- 言い換えると、コンパイル時に Scala 3.0 マクロの実行を伴う Scala 3.0 アーティファクト には依存できる

### Scala 3 アーティファクトの共有マクロ API

- Scala 3.0 のアーティファクトは、Scala 2.13 と Scala 3.0 のマクロを両方実装できる
- Scala 2.13 のアーティファクトは、Scala 2.13 でコンパイルされている必要があるため、別のモジュールとして提供する必要がある
