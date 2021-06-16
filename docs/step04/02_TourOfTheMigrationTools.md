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
  - [Maven](#maven)
- [Code editors and IDEs](#code-editors-and-ides)
  - [Metals](#metals)
  - [IntelliJ IDEA](#intellij-idea)
- [Formatting Tools](#formatting-tools)
  - [Scalafmt](#scalafmt)
- [Migration Tools](#migration-tools)
  - [Scalafix](#scalafix)
  - [Scala 3 migrate Plugin](#scala-3-migrate-plugin)
  - [Scaladex](#scaladex)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 から Scala 3 への移行をサポートするいくつかのツールを確認しておきましょう。

## ドキュメント参照先

[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) からこちらを参照します。

- [Tour of the Migration Tools](https://docs.scala-lang.org/scala3/guides/migration/tooling-tour.html)


## The Scala Compilers

### The Scala 2.13 compiler

- Scala 2.13 で、コンパイラオプション `-Xsource:3` を有効にすると、いくつかの Scala 3 のシンタックスと動作を有効にできる
  - ほとんどの非推奨のシンタックスはエラーを出力する
  - 廃止されたシンタックスの多くはエラーを出力する
  - infix 演算子は、複数行からなる式の途中で行を開始できる
  - 暗黙的な検索とオーバーロードの解決は、特別なチェックのときにScala 3 の反変性（contravariance）のハンドリングに従う
- `-Xsource:3` は、早期の移行を促すためのもの

### The Scala 3 compiler

- Scala 3 で、コンパイラオプション `-source:3.0-migration` を有効にすると、コンパイラは Scala 2.13 のシンタックスの一部を受け入れ、変更が必要な場合は警告メッセージを出力する
- 更に `-rewrite` と組み合わせることで、コードを自動的に書き換えることができる
  - 詳細は、この後の Scala 3 Migration Mode で確認する

## Build tools

### sbt

- sbt 1.5 以降は Scala 3 をサポートしている
  - sbt 1.4 で Scala 3 をサポートするには、`sbt-dotty` プラグインが必要だった
- 一般的な task や setting、多くのプラグインは同じように動作する
- 移行を助けるために、sbt 1.5 は新しい Scala 3 のクロスバージョンを導入している

```scala
// Scala 3 で Scala 2.13 のライブラリを使用する
libraryDependency += ("org.foo" %% "foo" % "1.0.0").cross(CrossVersion.for3Use2_13)

// Scala 2.13 で Scala 3 のライブラリを使用する
libraryDependency += ("org.bar" %% "bar" % "1.0.0").cross(CrossVersion.for2_13Use3)
```

### Mill

- Mill 0.9.x は Scala 3 をサポートしている

### Maven

- [scala-maven-plugin](https://github.com/davidB/scala-maven-plugin) は、間もなく Scala 3 をサポートする予定


## Code editors and IDEs

### Metals

- Metals は、VS Code、Vim、Emacs、Sublime Text、Eclipse で動作する Scala language server
- Metals は、Scala 3 の非常に多くの機能をサポートしている
- 新しいシンタックスの変更や新機能はこれから対応される予定

### IntelliJ IDEA

- IntelliJ の Scala plugin は、Scala 3 を暫定的にサポートしていて、本格的なサポートは、JetBrains 社のチームが取り組んでいる

## Formatting Tools

### Scalafmt

- [Scalafmt](https://scalameta.org/scalafmt/) は、v3.0.0-RCx は、Scala 2.13 と Scala 3 の両方をサポートしている
- Scala 3 のフォーマットを有効にするには、`.scalafmt.conf` ファイルで `runner.dialect = scala3` を設定する
- 選択的に有効にしたい場合は、`fileOverride` の設定を行う
  ```
  //.scalafmt.conf
  fileOverride {
    "glob:**/scala-3*/**" {
      runner.dialect = scala3
    }
  }
  ```

## Migration Tools

### Scalafix

https://scalacenter.github.io/scalafix/

- Scalafix は、Scala のリファクタリングツール
- 現時点では Scala 2.13 でしか動作しないが、Scala 3 へ移行する前にソースコードを準備するのに役立つ
- 非互換性は、対応する Scalafix のルールで解決できる
- ルールは、sbt-scalafix プラグインを使って適用することもできるし、後述するオールインワンの sbt-scala3-migrate を使うこともできる

### Scala 3 migrate Plugin

https://github.com/scalacenter/scala3-migrate

Scala 3 migrate は、Scala 3 への移行を容易にするための sbt plugin で、次のようなインクリメンタルなアプローチを提案している。

- ライブラリの依存関係の移行
  - すべてのライブラリの依存関係に対して、Scala 3.0 で利用可能なバージョンであるかをチェックする
- コンパイラオプション（scalacOptions）の移行
  - Scala 2 のコンパイラオプションは、Scala 3 ではそのまま使えるものもあれば、削除されたり、リネームされたりしている
  - このステップでは、コンパイラオプションを適応させるのに役立つ
- シンタックスの移行
  - このステップでは、Scalafix と既存のルールに依存して、非推奨のシンタックスを修正する
- コードの移行
  - Scala 3 には新しい型推論アルゴリズムが導入され、Scala 2 コンパイラが推論した型とは異なる型を推論する可能性がある
  - 最後のステップでは、プロジェクトがランタイムの動作を変更することなく Scala 3 でコンパイルできるように、最小限の型のセットを明示する

### Scaladex

- [Scaladex](https://index.scala-lang.org/search?q=*&scalaVersions=scala3) では、Scala 3.0 をサポートするオープンソースライブラリを探すことができる
