# Opaque Type Aliases {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Opaque Type Aliases](#opaque-type-aliases-1)

<!-- /code_chunk_output -->

## 概要

次に、Opaque Type Aliases を見てみます。この Opaque Type Aliases は、個人的にかなり嬉しい機能追加だと思ってます！

## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Opaque Type Aliases](https://dotty.epfl.ch/docs/reference/other-new-features/opaques.html)

## Opaque Type Aliases

https://dotty.epfl.ch/docs/reference/other-new-features/opaques.html

- Opaque types aliases は、実態となる型を限定されたスコープ内でのみ型エイリアスとして参照できるようにして、スコープの外からは opaque（不透明）とすることで、型の抽象化を行うもの
- スコープの外からは、公開されたフィールドやメソッドからしかアクセスすることができない
- コンパイルすると実態の型に変換されるのでオーバーヘッドを気にしなくても良い
- Scala 2 では、このような実装を [Value classes](https://docs.scala-lang.org/ja/overviews/core/value-classes.html) で実現していたが、[制約が多かった](https://docs.scala-lang.org/ja/overviews/core/value-classes.html)
- Value classes は削除されることはなくて、[project Valhalla](https://openjdk.java.net/projects/valhalla/) で計画されているような JVM でネイティブにサポートされるようになれば、状況が変わるかもしれない

:memo: [OpaqueTypeAliases.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/OpaqueTypeAliases.scala) / [OpaqueTypeAliases2.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/OpaqueTypeAliases2.scala)