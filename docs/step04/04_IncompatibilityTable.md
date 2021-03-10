# Incompatibility Table {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Incompatibility Table](#incompatibility-table-1)

<!-- /code_chunk_output -->

## 概要

最後に Scala 2.13 と Scala 3.0 との非互換性について確認しましょう。

ここで言う非互換性とは、Scala 2.13 ではコンパイルできても Scala 3.0 ではコンパイルできないコードのことを指します。コードベースを Scala 2.13 から Scala 3.0 へ移行するには、ソースコードの非互換性をすべて見つけ出して修正する必要があります。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/table.html)

補足としてこちらも参照します。

- [Syntactic Changes](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/syntactic-changes.html)
- [Dropped Features](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/dropped-features.html)
- [Other Changed Features](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/other-changed-features.html)

## Incompatibility Table

https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/table.html

Incompatibility Table は、Scala 2.13 と Scala 3.0 のそれぞれの非互換性について、どのような移行方法があるのかがまとめられています。

観点としては、

- Scala 2.13 で Deprecation だったかどうか
- Scala 3.0 Migration Mode で自動的に書き換えられるか
- Scalafix Rule で検出できるか

また、まれに Scala 2.13 と Scala 3.0 で実行時の非互換性が発生することもあるので注意が必要です。

---

ここからは、Incompatibility Table をざっと眺めながら、Scala 3.0 Migration Mode の rewrite を実際に動かしてみたいと思います。

本リポジトリの step04 のプロジェクトには、非互換なソースコードがコメントアウトの状態で書いてあります。それらのコメントアウトを一つずつ外し、`build.sbt` のコンパイラオプションを切り替えることで、エラーが警告になったり、rewrite が発動したりを確認することができます。
