# EnumsとADTs {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Enumerations](#enumerations)
- [Algebraic Data Types](#algebraic-data-types)

<!-- /code_chunk_output -->

## 概要

Scala 3 では、Enumerations や Algebraic Data Types (ADTs, 代数的データ型) を定義するための専用のシンタックスが追加されました。Step3 はまずそこから見ていきましょう。（本リポジトリの対応するサンプルコードも併せて参照してください）

## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Enumerations](https://dotty.epfl.ch/docs/reference/enums/enums.html)
- [Algebraic Data Types](https://dotty.epfl.ch/docs/reference/enums/adts.html)

## Enumerations

https://dotty.epfl.ch/docs/reference/enums/enums.html

- Scala 3 では、`enum` で Enumerations（列挙型）を定義できるようになった
  ```scala
  enum Color:
    case Red, Green, Blue
  ```
- 列挙した型は、`enum` のコンパニオンオブジェクトとして定義される。つまり、このように使える
  ```scala
  Color.Red
  ```
- 列挙した型は、一意の `Int` の値に対応していて、`ordinal` で取得ができる
- Enums のコンパニオンオブジェクトには、ユーティリティメソッドとして、`valueOf`、`values`、`fromOrdinal` が用意されている
- Enums には独自のメソッドを定義できる
- Scala で定義した Enums は、 `java.lang.Enum` を継承することで、Java の Enums として使用することができる

:memo: [Enumerations.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/Enumerations.scala)

## Algebraic Data Types

https://dotty.epfl.ch/docs/reference/enums/adts.html

- `enum` で、Algebraic Data Types （ADTs、代数的データ型）を表現できる
- `enum` の型パラメータの変位指定が covariant（共変）の場合は、コンパイラの型推論により列挙した型の `extends` を省略できるが、変位指定が covariant 以外の場合は明示的に書く必要がある
- Enums と同様、ADTs は独自のメソッドを定義することができる 
- ADTs は、Enums と混在させることができる
- ADTs の `case` の型パラメータは、親である `enum` の変位指定を引き継ぐ

:memo: [AlgebraicDataTypes.scala](/step03/src/main/scala/com/github/shinharad/gettingStartedWithScala3/AlgebraicDataTypes.scala)