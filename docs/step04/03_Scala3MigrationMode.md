# Scala 3 Migration Mode {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Migration mode](#migration-mode)
- [Automatic rewrites](#automatic-rewrites)
- [Error explanations](#error-explanations)

<!-- /code_chunk_output -->

## 概要

Scala 3 コンパイラは、Scala 2.13 から Scala 3 への移行を容易にするたの Migration Mode を提供しています。ここでは、この Migration Mode について確認しましょう。

## ドキュメント参照先

[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) からこちらを参照します。

- [Scala 3 Migration Mode](https://docs.scala-lang.org/scala3/guides/migration/tooling-migration-mode.html)


## Migration mode

- Scala 3 でコンパイラオプションの `source:3.0-migration` を指定すると、コンパイラは Scala 3 で廃止された機能のほとんどに寛容になり、エラーの代わりに警告を表示するようになる
- それぞれの警告は、Scala 3 で廃止されたコードをクロスコンパイル対応のコードに安全に書き換える能力があることを示している
- これを、Scala 3 migration compilation と呼んでいる

## Automatic rewrites

- Migration Mode でコードをコンパイルすると、ほとんどすべての警告はコンパイラ自身によって自動的に解決してくれる
- そのためには、`-source:3.0-migration` の他に `-rewrite` オプションを指定して再度コンパイルする必要がある
  - `-rewrite` は、本リポジトリの Step1 で確認した通り、コードを自動的に書き換えてくれるコンパイラオプション
- コンパイラによる自動書き換えの注意点
  - コードを書き換えてしまうので、書き換え前のコードをコミットしておき、コンパイラが適用した差分が分かるようにしておく
  - コンパイルエラーの場合は書き換えが適用されない
  - どのルールで書き換えを行うかは選択できないので、すべてのルールに対して実行される
- どのような警告に対して自動的に書き換えをしてくれるかは、 [Incompatibility Table](https://docs.scala-lang.org/scala3/guides/migration/incompatibility-table.html) を参照する

## Error explanations

- `-source:3.0-migration` モードでは、すべての機能を処理できるわけではなくて、場合によっては Scala 2.13 と Scala 3.0 の非互換性のためにエラーが残ることもある
- `-explain` または `-explain-types` オプションを指定すると、残りのエラーについての詳細を表示する
- `-explain` と `-explain-types` オプションは、Migration Mode に限定されるものではなくて、Scala 3 の学習やコーディングを支援してくれるコンパイラオプション
