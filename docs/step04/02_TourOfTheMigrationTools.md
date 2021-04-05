# Tour of the Migration Tools {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [The Scala Compilers](#the-scala-compilers)
  - [The Scala 2.13 compiler](#the-scala-213-compiler)
  - [The Scala 3 compiler](#the-scala-3-compiler)
- [Build tools](#build-tools)
  - [sbt](#sbt)
  - [Mill](#mill)
- [Code editors and IDEs](#code-editors-and-ides)
  - [Metals](#metals)
  - [IntelliJ IDEA](#intellij-idea)
- [Migration Tools](#migration-tools)
  - [Scalafix](#scalafix)
  - [Scala 3 migrate Plugin](#scala-3-migrate-plugin)
  - [Scaladex](#scaladex)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 から Scala3 への移行をサポートするいくつかのツールを確認しておきましょう。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Tour of the Migration Tools](https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/migration-tools.html)


## The Scala Compilers

### The Scala 2.13 compiler

- Scala 2.13 で、コンパイラオプション `-Xsource:3` を有効にすると、いくつかの Scala3 のシンタックスと動作を有効にできる
- `-Xsource:3` は、早期の移行を促すためのもの

### The Scala 3 compiler

- Scala3 で、コンパイラオプション `-source:3.0-migration` を有効にすると、コンパイラは Scala 2.13 のシンタックスの一部を受け入れ、変更が必要な場合は警告メッセージを出力する
- 更に `-rewrite` と組み合わせることで、コードを自動的に書き換えることができる
  - 詳細は、この後の Scala 3 Migration Mode で確認する

## Build tools

### sbt

- sbt 1.5 は、Scala3 をサポートしている
- 一般的な task や setting、多くのプラグインは同じように動作する
- 移行を助けるために、sbt 1.5 は新しい Scala3 のクロスバージョンを導入している

```scala
// build.sbt
libraryDependency += ("org.foo" %% "foo" % "1.0.0").cross(CrossVersion.for3Use2_13)
libraryDependency += ("org.bar" %% "bar" % "1.0.0").cross(CrossVersion.for2_13Use3)
```

### Mill

- Mill 0.9.3 以上は Scala3 をサポートしている


## Code editors and IDEs

### Metals

- Metals は、VS Code、Vim、Emacs、Sublime Text、Eclipse で動作する Scala language server
- Metals は、Scala3 の非常に多くの機能をサポートしていて、新しい構文の変更や新機能のために、いくつかのマイナーな調整を行う予定

### IntelliJ IDEA

- IntelliJ の Scala plugin は、Scala3 を暫定的にサポートしていて、本格的なサポートは、JetBrains 社のチームが取り組んでいる

## Migration Tools

### Scalafix

https://scalacenter.github.io/scalafix/

- Scalafix は、Scala のリファクタリングツール
- 非互換性は、対応する Scalafix のルールで解決できる
- ルールは、sbt-scalafix plugin を使って適用することもできるし、後述するオールインワンの Scala 3 migrate Plugin を使うこともできる

### Scala 3 migrate Plugin

https://github.com/scalacenter/scala3-migrate

cala 3 migrate Plugin は、Scala3 への移行を容易にするための sbt plugin で、次のようなインクリメンタルなアプローチを提案している。

- ライブラリの依存関係の移行（`migrate-libs`）
  - Coursier を使って、すべてのライブラリの依存関係について、Scala 3 で利用可能なバージョンであるかをチェックする
- コンパイラオプション（scalacOptions）の移行（`migrate-scalacOptions`）
  - Scala2 のコンパイラオプションは、Scala3 ではそのまま使えるものもあれば、削除されたり、リネームされたりしている
  - この機能を使うと、プロジェクトのコンパイラオプションをどのように進化させるのかを見つけることができる
- シンタックスの移行（`migrate-syntax`）
  - Scalafix のルールを適用することで、いくつかの非互換性を修正する
- コードの移行（`migrate`）
  - Scala3 には新しい型推論アルゴリズムが導入され、Scala2 コンパイラが推論した型とは異なる型を推論する可能性がある
  - Scala3 コンパイラがコードの意味を変えずに動作させるために、明示的にアノテーションを行う最小の型のセットを見つけようとする

### Scaladex

- [Scaladex](https://index.scala-lang.org/search?q=*&scalaVersions=scala3) では、Scala 3.0 をサポートするオープンソースライブラリを探すことができる
