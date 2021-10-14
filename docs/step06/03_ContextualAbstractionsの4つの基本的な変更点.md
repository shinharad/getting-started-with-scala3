# Contextual Abstractionsの4つの基本的な変更点 {ignore=true}

**:construction: EPFLのドキュメントは、docs.scala-lang.org/scala3 に統合されたためこの内容は古くなっています。 :construction:**

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Given Instances](#given-instances)
- [Using Clauses](#using-clauses)
- [Importing Givens](#importing-givens)
- [Implicit Conversions](#implicit-conversions)

<!-- /code_chunk_output -->


## 概要

これから Contextual Abstractions の各機能について見ていきますが、まずは4つの基本的な変更点である、Given Instances、Using Clauses、Importing Givens、Implicit Conversion について確認しておきましょう。

## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) の Contextual Abstractions よりこちらを参照します。

- [Given Instances](https://dotty.epfl.ch/docs/reference/contextual/givens.html)
- [Using Clauses](https://dotty.epfl.ch/docs/reference/contextual/using-clauses.html)
- [Importing Givens](https://dotty.epfl.ch/docs/reference/contextual/given-imports.html)
- [Implicit Conversions](https://dotty.epfl.ch/docs/reference/contextual/conversions.html)

## Given Instances

https://dotty.epfl.ch/docs/reference/contextual/givens.html

- Given Instances (またはシンプルに "givens") は、特定の型に対する振る舞いを定義し、コンテキストパラメータへの引数を合成する役割を果たす
  ```scala
  trait Ord[T]:
    def compare(x: T, y: T): Int
      extension (x: T) def < (y: T) = compare(x, y) < 0
      extension (x: T) def > (y: T) = compare(x, y) > 0

  // Given Instances
  given intOrd: Ord[Int] with
    def compare(x: Int, y: Int) =
      if x < y then -1 else if x > y then +1 else 0
  ```
- givens は、名前を省略することができる
  ```scala
  given Ord[Int] with
    def compare(x: Int, y: Int) = ???
  ```
- givens のエイリアスを定義することができる
  ```scala
  given global: ExecutionContext = ExecutionContext.fromExecutorService(ForkJoinPool())
  ```
- givens のエイリアスは匿名にすることができる
  ```scala
  given ExecutionContext = ExecutionContext.fromExecutorService(ForkJoinPool())
  ```
- given は、パターンの中にも書くことができる
  ```scala
  for given Context <- applicationContexts do ???
  pair match
    case (ctx @ given Context, y) => ???
  ```
- `scala.util.NotGiven` を使用することで、givens がスコープに存在しないかどうかを調べることができる

:memo: [GivenInstance.scala](/step06/src/main/scala/com/github/shinharad/gettingStartedWithScala3/GivenInstance.scala)


## Using Clauses

https://dotty.epfl.ch/docs/reference/contextual/using-clauses.html

- メソッドに対して `using` 句を使用してコンテキストパラメータを暗黙的に指定できる（Scala 2 の implicit parameter に代わるもの）
  ```scala
  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  // Ord[Int]、Ord[List[Int]] の givens がスコープ内に存在する場合はコンテキストパラメータを省略できる
  max(2, 3)
  max(List(1, 2, 3), Nil)
  ```
- `using` 句の名前は省略できる
  ```scala
  def maximum[T](xs: List[T])(using Ord[T]): T = xs.reduceLeft(max)
  ```
- `summon` は特定の型の givens を返す（Scala 2 の `implicity` に代わるもの）
  ```scala
  summon[Ord[List[Int]]]
  ```

:memo: [UsingClauses.scala](/step06/src/main/scala/com/github/shinharad/gettingStartedWithScala3/UsingClauses.scala)

## Importing Givens

https://dotty.epfl.ch/docs/reference/contextual/given-imports.html

- givens をインポートするために使用する
  ```scala
  // 今までのインポートは、Given Instance 以外をインポートする
  //（と思っているのだが、そういうわけではなさそう？？？）
  import A.*
  // givens のみをインポートする
  import A.given
  // すべてのメンバをインポートする
  import A.{ given, * }
  ```
- このルールによって得られるメリット
  - スコープ内の givens がどこからインポートされているのかが、より明確になる
  - 通常のワイルドカードによるインポートの長いリストの中で埋もれることがなくなる
  - 他のものをインポートすることなく、すべての givens をインポートすることができる
- givens は、無名の可能性があるため、通常はワイルドカードによるインポートを使用するが、もしも個別の givens をインポートしたい場合は、By-type インポートを使用する
  ```scala
  import A.given TC
  // 複数の場合
  import A.{given T1, ..., given Tn}
  ```
- パラメータ化された型の givens をインポートするには、ワイルドカード引数を使用する
  ```scala
  import Instances.{ given Ordering[?], given ExecutionContext }
  // By-nameのインポートと混在させる場合は、givens は最後に書く
  import Instances.{ im, given Ordering[?] }
  ```
- givens のインポートは、マイグレーションを考慮して古いスタイルの暗黙的な定義もスコープに含める
  - ただし、Scala 3.1 以降では非推奨となり段階的に削除される

:memo: [ImportingGivens.scala](/step06/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ImportingGivens.scala)


## Implicit Conversions

https://dotty.epfl.ch/docs/reference/contextual/conversions.html

- Scala 3 では暗黙の型変換は、`scala.Conversion` クラスの givens として定義する
  ```scala
  // 例えば、String から Token への暗黙の型変換を定義する場合
  given Conversion[String, Token] with
    def apply(str: String): Token = new KeyWord(str)
  ``` 
- エイリアスを使えば、より簡潔に書くことができる
  ```scala
  given Conversion[String, Token] = new KeyWord(_)
  ```
- `Predef` には、プリミティブな数値型を `java.lang.Number` のサブクラスにマッピングする "auto-boxing" 変換が定義されている。例えば、`Int` から `java.lang.Integer` への変換は次のように定義されている
  ```scala
  given int2Integer: Conversion[Int, java.lang.Integer] =
    java.lang.Integer.valueOf(_)
  ```

:memo: [ImplicitConversions.scala](/step06/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ImplicitConversions.scala)

