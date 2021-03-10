# Tour of the Migration Tools {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [The Scala 3 compiler](#the-scala-3-compiler)
- [Scalafix](#scalafix)
- [sbt and other build tools](#sbt-and-other-build-tools)
- [Metals and other IDEs](#metals-and-other-ides)
- [Scaladex](#scaladex)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 から Scala 3.0 への移行をサポートするいくつかのツールを確認しておきましょう。


## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Tour of the Migration Tools](https://scalacenter.github.io/scala-3-migration-guide/docs/migration-tools.html)


## The Scala 3 compiler

- Scala3 コンパイラ自体が強力な移行ツールとなっている
- Scala 3 Migration Mode を備えていて、必要に応じて Scala 2.13 のコードを Scala3 のコードへ書き換えてくれる
  - 詳細は、この後の Scala 3 Migration Mode で確認する
- このツールを使用して、コミュニティではすでにかなりの数のライブラリが移行されている
  - [Scala 3 Community Build](https://github.com/lampepfl/dotty/tree/master/community-build/community-projects)

## Scalafix

https://scalacenter.github.io/scalafix/

- Scalafix は Scala のリファクタリングツールだが、Scala3 への移行時にコンパイラを支援する補助的なツールとして使える
- Scalafix は、 Scala 2.13 で動作するため、移行前にコードを整備することができる
- ルールを一つずつ適用していくことでインクリメンタルに進めることができる
- コードにいくつかの型や implicit の値を追加することで、型推論や implicit の解決の問題を自動または半自動で解決するのには非常に便利

## sbt and other build tools

- [sbt-dotty](https://dotty.epfl.ch/docs/usage/getting-started.html) プラグインは、Scala 3.0 と Scala 2.13 とのクロスコンパイルをサポートしている
- [Mill](https://com-lihaoyi.github.io/mill/) など他のビルドツールもクロスコンパイルをサポートしている

## Metals and other IDEs

- Visual Studio Code には独自の Scala 3.0 Language Server プラグインがあり、sbt-dotty プラグインの `sbt launchIDE` タスクで設定できる

## Scaladex

- [Scaladex](https://index.scala-lang.org/search?q=*&scalaVersions=scala3) では、Scala 3.0 をサポートするオープンソースライブラリを探すことができる
