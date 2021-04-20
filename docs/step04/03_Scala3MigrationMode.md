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

Scala3 コンパイラは、Scala 2.13 から Scala3 への移行を容易にするたの Migration Mode を提供しています。ここでは、この Migration Mode について確認しましょう。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Scala 3 Migration Mode](https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/scala-3-migration-mode.html)


## Migration mode

- コンパイラオプションの `source:3.0-migration` は、エラーの代わりに警告を表示することで、Scala 2.13 の非推奨や削除された機能を使っていたとしてもコンパイラは寛容になってくれる
- これを、Scala 3 Migration Mode と呼んでいる

## Automatic rewrites

- Migration Mode の `-source:3.0-migration` と `-rewrite` オプションを指定してコンパイルすると、ほとんどすべての警告はコンパイラによって自動的に解決してくれる
- `-rewrite` は、Step1 で確認した通り、コードを自動的に書き換えてくれるコンパイラオプション
- どのような警告に対して自動的に書き換えをしてくれるかは、 [Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html) を参照する
- ただし、Migration Mode でコンパイルエラーになった場合は、`-rewrite` が適用されない

## Error explanations

- Migration Modeの `-source:3.0-migration` は、すべての機能を処理できるわけではなくて、場合によっては、 Scala 2.13 と Scala 3.0 の非互換性のためにエラーが残ることもある
- その場合は、`-source:3.0-migration` を `-explain` や `-explain-types` と組み合わせて指定することで、エラーの詳細を知ることができる
  - `-explain` : エラーの詳細を表示する
  - `-explain-types` 型エラーを詳細に表示する
- `-explain` や `-explain-types` は、移行モードに限定されるものではなくて、Scala3 の学習やコーディングを支援してくれるコンパイラオプション
