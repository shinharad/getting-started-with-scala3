# Incompatibility Table {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Incompatibility Table](#incompatibility-table-1)
  - [Syntactic Changes](#syntactic-changes)
  - [Dropped Features](#dropped-features)
  - [Other Changed Features](#other-changed-features)

<!-- /code_chunk_output -->

## 概要

最後に Scala 2.13 と Scala 3.0 との非互換性について確認しましょう。

ここで言う非互換性とは、Scala 2.13 ではコンパイルできても Scala 3 ではコンパイルできないコードのことを指します。コードベースを Scala 2.13 から Scala 3 へ移行するには、ソースコードの非互換性をすべて見つけ出して修正する必要があります。

## ドキュメント参照先

[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) からこちらを参照します。

- [Incompatibility Table](https://scalacenter.github.io/scala-3-migration-guide/docs/general/incompatibility-table.html)

補足としてこちらも参照します。

- [Syntactic Changes](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/syntactic-changes.html)
- [Dropped Features](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/dropped-features.html)
- [Other Changed Features](https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/other-changed-features.html)

## Incompatibility Table

https://scalacenter.github.io/scala-3-migration-guide/docs/general/incompatibility-table.html

Incompatibility Table は、Scala 2.13 と Scala 3 のそれぞれの非互換性について、どのような移行方法があるのかがまとめられています。

観点としては、

- Scala 2.13 コンパイラが deprecation または feature の警告メッセージを出力するか
- それに対する Scala3 migration のルールの有無
- それを修正するための Scalafix ルールの有無

また、まれに Scala 2.13 と Scala 3.0 で実行時の非互換性が発生することもあるので注意が必要です。

**:construction: かなり頻繁に変わってるので今後内容が変わる予定です :construction:**

---

ここからは、Incompatibility Table をざっと眺めながら、Scala 3.0 Migration Mode の rewrite を実際に動かしてみたいと思います。

本リポジトリの step04 のプロジェクトには、非互換なソースコードがコメントアウトの状態で書いてあります。それらのコメントアウトを一つずつ外し、`build.sbt` のコンパイラオプションを切り替えることで、エラーが警告になったり、rewrite が発動したりを確認することができます。

### Syntactic Changes

- :memo: [01_RestrictedKeywords.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/01_RestrictedKeywords.scala)
- :memo: [02_ProcedureSyntax.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/02_ProcedureSyntax.scala)
- :memo: [03_ParenthesesAroundLambdaParameter.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/03_ParenthesesAroundLambdaParameter.scala)
- :memo: [04_OpenBraceIndentationForPassingAnArgument.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/04_OpenBraceIndentationForPassingAnArgument.scala)
- :memo: [05_WrongIndentation.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/05_WrongIndentation.scala)
- :memo: [06__AsATypeParameter.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/06__AsATypeParameter.scala)
- :memo: [07_+and-AsTypeParameter.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/01_syntacticChanges/07_+and-AsTypeParameter.scala)

### Dropped Features

- :memo: [01_SymbolLiterals.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/02_droppedFeatures/01_SymbolLiterals.scala)
- :memo: [02_DoWhileConstruct.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/02_droppedFeatures/02_DoWhileConstruct.scala)
- :memo: [03_AutoApplication.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/02_droppedFeatures/03_AutoApplication.scala)
- :memo: [04_ValueEtaExpansion.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/02_droppedFeatures/04_ValueEtaExpansion.scala)

### Other Changed Features

- :memo: [01_InheritanceShadowing.scala](/step04/src/main/scala/com/github/shinharad/gettingStartedWithScala3/03_otherChangedFeatures/01_InheritanceShadowing.scala)
