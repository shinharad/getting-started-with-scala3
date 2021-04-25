package com.github.shinharad.gettingStartedWithScala3
package patternBindings

import scala.util.chaining.*

//---
// Bindings in Pattern Definitions

// 以下のコードは、右辺の Any を pattern bindings で、具象型の String へ設定している
@main def no1(): Unit =

  // このコードは実行時エラーとなるが、Scala 2 ではコンパイル時に検出ができなかった
  // Scala 3.1 からはこのような場合に警告メッセージを出力するようになった
  // (Scala 3.0 の場合は、`-source future` を設定することで警告メッセージが出力されるようになる)
  
  // val xs: List[Any] = List(1, 2, 3)
  // val (x: String) :: _ = xs

  // Scala 3.1 の pattern bindings は、右辺の型がパターンの型に適合する場合にのみ許可される
  val pair = (1, true)
  val (x, y) = pair

// 時にはパターンが不適合であっても、pattern bindings したいことがある
// 例えば、ある時点でリストが空でないことがわかった場合、次のように pattern bindings する
def no2(): Unit =
  val elems = Seq(1, 2, 3)

  // これは警告メッセージが出力される
  // val first :: rest = elems

  // この場合、右辺に @unchecked を付けることで警告メッセージを避けることができる
  val first :: rest = elems: @unchecked   // OK

  // ただし、elems が空になる場合は、実行時エラーとなる

//---
// Pattern Bindings in for Expressions

// 同様の変更は、for 式のパターンにも適用される
@main def no3(): Unit =
  val elems: List[Any] = List((1, 2), "hello", (3, 4))

  // パターンの型 (Any, Any) は、右辺の式の型である Any よりも具象化されているため、
  // Scala 3.1 ではコンパイル時に警告メッセージを出力する

  // for (x, y) <- elems yield (y, x)

  // なお、上記のコードは、Scala 2 では、
  // パターン (x, y) にマッチするタプル型の要素のみを保持するようにリスト elems がフィルタリングされる
  // Scala 3.1 では、パターンの前に `case` を付けることで、フィルタリングすることができる

  // for case (x, y) <- elems yield (y, x)  // returns List((2, 1), (4, 3))
  //   .tap(println)

  // （ただ、警告メッセージは消えないっぽい）
