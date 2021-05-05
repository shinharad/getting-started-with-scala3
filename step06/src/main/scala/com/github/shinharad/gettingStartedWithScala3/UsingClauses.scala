package com.github.shinharad.gettingStartedWithScala3
package usingClauses

import givenInstances.Ord
import givenInstances.given

//---
// Using Clauses

// Scala 2 の implicit parameter に代わるもので、
// メソッドに対して using 句を使用してコンテキストパラメータを暗黙的に指定する

object no1:
  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  // Ord[T] の givens がスコープ内にあるので省略できる
  max(2, 3)
  max(List(1, 2, 3), Nil)

  // もしも明示する場合は、このように書く
  max(2, 3)(using intOrd)

//---
// Anonymous Context Parameters

// using 句の名前は省略できる
// コンテキストパラメータの名前は、多くの状況で他のコンテキストパラメータの合成された引数でのみ使用されるため、明示する必要はない

object no2:
  import no1.max

  def maximum[T](xs: List[T])(using Ord[T]): T =
    xs.reduceLeft(max)

// Varargパラメータは、using 句をサポートしない
object no3:
  import no1.*

  // def maximum[T](xs: List[T])(using Ord[T]*): T = ??? // NG

//---
// Inferring Complex Arguments

object no4:
  import no1.max
  import no2.maximum

  val xs = List(1, 2, 3)

  def descending[T](using asc: Ord[T]): Ord[T] = new Ord[T]:
    def compare(x: T, y: T) = asc.compare(y, x)

  // minimum メソッドの右辺は、maximum(xs) への明示的な引数として descending を渡している
  def minimum[T](xs: List[T])(using Ord[T]) =
    maximum(xs)(using descending)

  // この設定により、以下の呼び出しはすべて最後の呼び出しに正規化される
  minimum(xs)
  maximum(xs)(using descending)
  // maximum(xs)(using descending(using listOrd))
  // maximum(xs)(using descending(using listOrd(using intOrd)))

//---
// Summoning Instances
// summon は特定の型の given を返す

// Ord[List[Int]] の given instance はこのように取得する
def no5(): Unit =
  summon[Ord[List[Int]]]

  summon[Ord[List[Int]]]
    .compare(List(1, 2, 3), List(1, 2, 3))

  // Scala 2 の implicitly は将来的に非推奨となり廃止される
  implicitly[Ord[List[Int]]]
    .compare(List(1, 2, 3), List(1, 2, 3))

//---
// 複数のコンテキストパラメータを指定する場合
def no6(): Unit =
  trait C1[T]
  trait C2[T]

  def f[T](x: T)(using C1[T], C2[T]): T = ???

//---
// using は、Scala 2 の implicit parameter の構文上の欠点を解決している

def no7(): Unit =
  trait Context
  given context: Context = ???

  // Scala 2 の implicit parameter
  def currentMapLegacy(implicit ctx: Context): Map[String, Int] = ???

  // currentMapLegacy("abc")    // NG
  currentMapLegacy.apply("abc") // OK

  // Scala 3 の using
  def currentMap(using Context): Map[String, Int] = ???

  currentMap("abc")       // OK
  currentMap.apply("abc") // OK

//---
// Scala 3 へのマイグレーションのために、Scala 2 の暗黙の定義との相互運用性を確認する

// Scala 2 の implicit 定義でも using 句に渡すことができる
def no8(): Unit =
  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  implicit val intOrd: Ord[Int] = new Ord[Int]:
    def compare(x: Int, y: Int) =
      if x < y then -1 else if x > y then +1 else 0

  max(2, 3)

// given instance を Scala 2 の implicit parameter に渡すことができる
def no9(): Unit =
  def max[T](x: T, y: T)(implicit ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  max(2, 3)
