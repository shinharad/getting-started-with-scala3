# Scala 3.0 Migration Mode {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Migration mode](#migration-mode)
- [Automatic rewrites](#automatic-rewrites)
- [Error explanations](#error-explanations)

<!-- /code_chunk_output -->


## 概要

Scala 2.13 のコードベースを Scala 3.0 へ移植していく際のマイグレーションモードについて確認しましょう。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Scala 3.0 Migration Mode](https://scalacenter.github.io/scala-3-migration-guide/docs/scala-3-migration-mode.html)


## Migration mode

- `source:3.0-migration` オプションは、エラーの代わりに警告を表示することで、 ほとんどの機能が削除されてもコンパイラは寛容になる
- これを、Scala 3 Migration Mode と呼んでいる

## Automatic rewrites

- マイグレーションモード `-source:3.0-migration` と `-rewrite` オプションを指定してコンパイルすると、ほとんどすべての警告はコンパイラによって自動的に解決してくれる
- `-rewrite` は、Step1 で確認したとおり、コードを自動的に書き換えてくれる機能
- コンパイルエラーになった場合は、`-rewrite` が適用されない

## Error explanations

- マイグレーションモード `-source:3.0-migration` は、すべての機能を処理できるわけではなくて、場合によっては、 Scala 2.13 と Scala 3.0 の非互換性のためにエラーが残ることもある
- この場合は、`-source:3.0-migration` を `-explain` や `-explain-types` と組み合わせて指定することで、エラーの詳細を知ることができる
  - `-explain` : エラーの詳細を表示する
  - `-explain-types` 型エラーを詳細に表示する
- `-explain` や `-explain-types` は、移行モードに限定されるものではなくて、Scala3 の学習やコーディングを支援してくれる