package com.github.shinharad.gettingStartedWithScala3
package explicitNulls2

//---
// Flow Typing

// nullable な変数が if 式などで non-null であると判定した場合、
// そのスコープ内では変数を non-null として扱える（Flow Typing）

def no1_1(): Unit =
  val s: String | Null = ???

  if s != null then
    // if の中では non-null であることが自明なので、String として使用できる
    s.length // s: String

  // if の外では nullable
  // s.length // これはコンパイルエラー
  s.nn.length

  // assert の後も non-null は自明なので、String として使用できる
  assert(s != null)
  s.length // s: String

// else でも同様に Flow Typing できる
def no1_2(): Unit =
  val s: String | Null = ???

  if s == null then
    s.nn.length  // s: String | Null
  else
    // s は、non-null なので、String として使用可能
    s.length // s: String

//---
// Logical Operators

// 論理演算子でも Flow Typing できる
def no2(): Unit =
  val s: String | Null = ???
  val s2: String | Null = ???

  if s != null && s2 != null then
    s.length // s: String
    s2.length // s2: String

  if s == null || s2 == null then
    // s: String | Null
    // s2: String | Null
    s.nn.length
    s2.nn.length
  else
    s.length // s: String
    s2.length // s2: String

//---
// Inside Conditions

// 論理演算子の短絡評価が、Flow Typing にも反映する
def no3(): Unit =
  val s: String | Null = ???

  if s != null && s.length > 0 then
    s.length // s: String

  if s == null || s.length > 0 then
    s.nn.length // s: String | Null
  else
    s.length // s: String

//---
// Match Case

// match でも Flow Typing できる
def no4(): Unit =
  val s: String | Null = ???

  s match
    case _: String => s.length // s: String
    case _ => s.nn.length // s: String | Null

//---
// Mutable Variable

// mutable 変数の場合は、変数が null かどうかをコンパイラが追跡可能であれば Flow Typing できる
def no5_1(): Unit =

  class C(val x: Int, val next: C | Null)

  var xs: C | Null = C(1, C(2, null))

  while xs != null do
    // xs は non-null と推論される

    val xsx: Int = xs.x
    val xscpy: C = xs

    // xscpy は non-null なので、それを再代入した xs も non-null
    xs = xscpy // xs: C

    // nullable な xs.next を再代入すると、xs は nullable になる
    xs = xs.next // xs: C | Null

// 変数が null かどうかをコンパイラが追跡できない場合、Flow Typing できない
// 例えば、変数がクロージャで代入される場合
def no5_2(): Unit =
  var x: String | Null = ???
  def y =
    x = null

  if x != null then
    // x は、クロージャ y で代入されているので、x に対して Flow Typing を行わない
    // つまり、x が例え if で non-null と判定されたとしても、nullable のままとなる

    // val a1: String = x // コンパイルエラー
    val a2: String | Null = x

// 変数がクロージャで代入されていない場合、ローカル変数の定義と同じメソッドに属する箇所では Flow Typing できる
// ただし、クロージャの中では、例え non-null と判定したとしても、安全ではないので Flow Typing できない
def no5_3(): Unit =
  var x: String | Null = ???

  def y =
    if x != null then
      // クロージャ y がどのタイミングで実行されるか分からず、かつ x の状態は変わる可能性があるので、
      // non-null として扱うことは安全ではなく、この場合は Flow Typing できない

      x.nn.length // x: String | Null

  if x != null then
    // ローカル変数の定義と同じメソッドに属するので、Flow Typing できる
    val a: String = x // x: String

    // null を再代入すると再び nullable に
    x = null

    val c: String | Null = x // x: String | Null

//---
// Unsupported Idioms

// Flow Typing は nullability と関係ない場合は使えない
def no6_1(): Unit =
  val x: Int = ???
  if x == 0 then
    // x: 0.type とは推論されない
    ()

// non-null の変数との比較では Flow Typing を使えない
def no6_2(): Unit =
  val s: String | Null = ???
  val s2: String | Null = ???

  if s != null && s == s2 then
    // s は String と推論されるが、
    s.length

    // non-null な s と比較したからといって、s2 が String と推論されるわけではない
    // s2.length // コンパイルエラー

//---
// UnsafeNulls

// 多くの nullable な値を扱うのは時として困難になるので、language feature として `unsafeNulls` が提供されている
// unsafeNulls スコープの中では、すべての `T | Null` は `T` として使用することができる。

// （※現状は unsafeNulls が機能していないっぽく、以下のコードはコンパイルが通らない）

def no7_1(): Unit =
  def f(x: String): String = ???
  def nullOf[T >: Null]: T = null

  import scala.language.unsafeNulls

  val s: String | Null = ???

  // nullable を non-null に設定する
  // val a: String = s

  // nullable を non-null として扱える
  // val b1 = s.trim
  // val b2 = b1.length

  // nullable を non-null な引数に渡せる
  // f(s).trim

  // non-null に null を渡せる
  // val c: String = null

  val d1: Array[String] = ???

  // non-null の型パラメータを nullable な型パラメータの変数に設定できる
  // val d2: Array[String | Null] = d1

  // Arrayの要素に null を設定したとしても non-null な型パラメータとして扱える
  // val d3: Array[String] = Array(null)

  // Null は Any のサブタイプだが、AnyRef として使用できるので、
  // このような安全でない上限型境界を指定できる
  // class C[T >: Null <: String] // define a type bound with unsafe conflict bound

  // Null は Any のサブタイプだが、AnyRef として使用できるので、任意の参照型を渡せる
  // val n = nullOf[String]

// unsafeNuls を適用した場合、通常の Scala と同様のセマンティックを持つが、同等ではない
// 例えば、以下のようなコードは unsafeNulls を使ってもコンパイルはできない
def no7_2(): Unit = 
  import scala.language.unsafeNulls

  // Javaとの相互運用のため，getの結果型は `T ｜ Null` になるが、
  // コンパイラは `T` が参照型であるかどうかを知らないため `T | Null` を `T` にキャストすることができない
  // これはコンパイルエラー
  // def head[T](xs: java.util.List[T]): T = xs.get(0)

  // これは、`xs.get(0)` の後に `.nn` を明示的に書く必要がある
  def head[T](xs: java.util.List[T]): T = xs.get(0).nn
