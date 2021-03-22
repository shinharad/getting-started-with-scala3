# Intersection TypesとUnion Types {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Intersection Types](#intersection-types)
- [Union Types](#union-types)

<!-- /code_chunk_output -->

## 概要

次に、Scala3 で新しく追加された型、Intersection Types (交差型) と Union Types (合併型) を見てみましょう。（本リポジトリの対応するサンプルコードも併せて参照してください）

## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Intersection Types](https://dotty.epfl.ch/docs/reference/new-types/intersection-types.html)
- [Union Types](https://dotty.epfl.ch/docs/reference/new-types/union-types.html)

## Intersection Types

https://dotty.epfl.ch/docs/reference/new-types/intersection-types.html

- `&` 演算子で、Intersection Types （交差型）を作成できるようになった
- `A & B` は、`A` 型でもあるし `B` 型でもあるという意味になる
- `A & B` のメンバは、`A` 型のすべてのメンバと `B` 型のすべてのメンバを持っている
- `A` と `B` に同一のメンバが存在する場合、`A & B` のメンバの型は、それぞれのメンバの型の Intersection Types になる
- `&` 演算子は可換的で、`A & B` と `B & A` は等価

:memo: [IntersectionTypes.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/IntersectionTypes.scala)

## Union Types

https://dotty.epfl.ch/docs/reference/new-types/union-types.html

- `|` 演算子で、Union Types（合併型）を作成できるようになった
- `A | B` は、`A` 型か `B` 型のどちらかという意味になる
- `|` 演算子は可換的で、`A | B` と `B | A` は等価

:memo: [UnionTypes.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/UnionTypes.scala)