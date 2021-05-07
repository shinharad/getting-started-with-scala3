package com.github.shinharad.gettingStartedWithScala3
package extensionMethods

import scala.util.chaining.*

@main def no1(): Unit =
  case class Circle(x: Double, y: Double, radius: Double)

  // Circle に対して extension method を定義
  extension (c: Circle)
    def circumference: Double = c.radius * math.Pi * 2

  val circle = Circle(0, 0, 1)

  // 通常のメソッドと同じ様に infix `.` で呼び出すことができる
  circle.circumference
    .tap(println)

  // コンパイラは内部的にこのように解釈するので、
  // <extension> def circumference(c: Circle): Double = c.radius * math.Pi * 2
  //
  // circumference(circle) でも呼び出すことができる
  assert(circle.circumference == circumference(circle))

//---
// Operators

def no2(): Unit =
  case class Elem(v: Int)

  extension (x: String)
    def < (y: String): Boolean = ???
  extension (x: Elem)
    def +: (xs: Seq[Elem]): Seq[Elem] = ???
  extension (x: Number)
    infix def min (y: Number): Number = ???

  /*
  これは、コンパイラは内部的にこのように解釈する
  <extension> def < (x: String)(y: String): Boolean = ...
  <extension> def +: (xs: Seq[Elem])(x: Elem): Seq[Elem] = ...
  <extension> infix def min(x: Number)(y: Number): Number = ...

  `+:` は右結合演算子のため、順序が逆になっていることに注意
  `x +: xs` は、infix `.` で書くと `xs.+:(x)` となる
  */

  "ab" < "c"
  Elem(1) +: List(Elem(2), Elem(3))
  2 min 3

  // こう書くこともできる
  <("ab")("c")
  +:(List(Elem(2), Elem(3)))(Elem(1))
  min(2)(3)

  // Elem(1) +: List(Elem(2), Elem(3)) は、infix . で呼び出すと順序が逆になる
  List(Elem(2), Elem(3)).+:(Elem(1))

//----
// Generic Extensions
// （こちらの内容は、Contextual Abstractions の内容を含むので、今はさらっと見る程度でOK）

def no3(): Unit = 

  // extension methods に型パラメータを指定する場合
  extension [T](xs: List[T])
    def second = xs.tail.head

  // context bound で型クラスを指定する場合
  extension [T: Numeric](x: T)
    def + (y: T): T = summon[Numeric[T]].plus(x, y) // summon は Scala 2 の implicitly に相当し、型クラスのインスタンスをsummon（召喚）している

  // extension methods の型パラメータは、メソッドの型パラメータと組み合わせて定義できる
  extension [T](xs: List[T])
    def sumBy[U: Numeric](f: T => U): U = ???

  // sumBy を呼び出すときに、メソッドの型パラメータ `[U: Numeric]` を指定する
  // （Int は Numeric 型クラスのインスタンス）
  List("a", "bb", "ccc").sumBy[Int](_.length)
  // List("a", "bb", "ccc").sumBy(_.length) // （この場合、型推論に任せて省略できる）

  // sumBy を呼び出すときに、extension の型パラメータ `[T]` を指定する
  // extension の型パラメータは、extension としてではなく、通常のメソッド呼び出しとして参照している場合のみ指定できる
  sumBy[String](List("a", "bb", "ccc"))(_.length)

  // 両方の型パラメータを指定する場合
  sumBy[String](List("a", "bb", "ccc"))[Int](_.length)

  // extension は、using 句を使用することもできる
  // （using 句は、Scala 2 の implicit parameter に相当する）
  extension [T](x: T)(using n: Numeric[T])
    def + (y: T): T = n.plus(x, y)

//---
// Collective Extensions

// extension のパラメータに対して、複数の extension method（collective extensions）を定義できる
def no4_collective(): Unit =
  // extension のパラメータは、この場合 `ss: Seq[String]`
  extension (ss: Seq[String])
    def longestStrings: Seq[String] =
      val maxLength = ss.map(_.length).max
      ss.filter(_.length == maxLength)

    // 本来は `ss.longestStrings.head` とすべきだが、
    // 同一の extension ブロック内では、extension method を直接呼び出すことができる
    def longestString: String = longestStrings.head
  
// 上記の collective extensions は、個別に書くとこのようになる
def no4_expand(): Unit =
  extension (ss: Seq[String])
    def longestStrings: Seq[String] =
      val maxLength = ss.map(_.length).max
      ss.filter(_.length == maxLength)

  extension (ss: Seq[String])
    def longestString: String = ss.longestStrings.head

// ちなみに longestString を先に定義するとコンパイルエラー
def no4_expand_2(): Unit =
  // コンパイルエラー
  // extension (ss: Seq[String])
  //   def longestString: String = ss.longestStrings.head

  extension (ss: Seq[String])
    def longestStrings: Seq[String] =
      val maxLength = ss.map(_.length).max
      ss.filter(_.length == maxLength)

// Collective extensions は、型パラメータを取り、using 句を持つこともできる
def no5(): Unit =
  import math.Ordering.Implicits.given
  extension [T](xs: List[T])(using Ordering[T])
    def smallest(n: Int): List[T] = xs.sorted.take(n)
    def smallestIndices(n: Int): List[Int] =
      val limit = smallest(n).max
      xs.zipWithIndex.collect { case (x, i) if (x <= limit) => i }

//--
// Translation of Calls to Extension Methods

/*
extension methodsを参照可能にするためには4つの方法がある

1. スコープ内で定義、またはそれを継承、インポートしている
2. 参照先のスコープ内で型クラスのインスタンスが定義されている
3. extension method `m` を `r.m` で参照する場合、`r` の暗黙のスコープで定義されている
4. extension method `m` を `r.m` で参照する場合、型クラスのインスタンスが `r` の暗黙のスコープで定義されている
*/

// 1. スコープ内で定義、またはそれを継承、インポートしている
trait IntOps:
  extension (i: Int) def isZero: Boolean = i == 0

  // isZero は、同一スコープ内（IntOps 内）で定義されているので参照可能
  extension (i: Int) def safeMod(x: Int): Option[Int] =
    if x.isZero then None 
    else Some(i % x)

object IntOpsEx extends IntOps:
  // isZero は、IntOps を継承しているので参照可能
  extension (i: Int) def safeDiv(x: Int): Option[Int] =
    if x.isZero then None
    else Some(i / x)

trait SafeDiv:
  import IntOpsEx.*

  // safeDiv は、IntOpsEx をインポートしてるので参照可能
  extension (i: Int) def divide(d: Int): Option[(Int, Int)] =
    (i.safeDiv(d), i.safeMod(d)) match 
      case (Some(d), Some(r)) => Some((d, r))
      case _ => None

// 2. 参照先のスコープ内で型クラスのインスタンスが定義されている
def no6_2(): Unit =
  given ops1: IntOps with {} // given ... with は、型クラスのインスタンスを定義している
  // given ops1: IntOps() // TODO 今後こういう書き方ができるようになるっぽい

  1.safeMod(2)

// 3. extension method `m` を `r.m` で参照する場合、`r` の暗黙のスコープで定義されている
// 4. extension method `m` を `r.m` で参照する場合、型クラスのインスタンスが `r` の暗黙のスコープで定義されている
def no6_3_4(): Unit =

  // 例えば、標準ライブラリの List のコンパニオンオブジェクトでは、
  // extension method として、flatten が、
  // それと、Ordering 型クラスのインスタンスが定義されている

  /*
  class List[T]

  object List:
    extension [T](xs: List[List[T]])
      def flatten: List[T] = xs.foldLeft(List.empty[T])(_ ++ _)

    given [T: Ordering]: Ordering[List[T]] with
      extension (xs: List[T])
        def < (ys: List[T]): Boolean = ???
  */

  // コンパニオンオブジェクトは、暗黙のスコープに入っていてクラスから参照ができるので、

  // exntension method の flatten を利用できる
  List(List(1, 2), List(3, 4)).flatten

  // Ordering 型クラスのインスタンスも参照できるので、`<` も利用できる
  import math.Ordering.Implicits.given
  List(1, 2) < List(3)

//---
// 再帰的な呼び出しの場合

def no7_extension(): Unit =
  extension (s: String)
    def position(ch: Char, n: Int): Int =
      if n < s.length && s(n) != ch then position(ch, n + 1)
      else n

// コンパイラは内部的に通常のメソッド呼び出しとして解釈する
def no7_rewrite(): Unit =
  def position(s: String)(ch: Char, n: Int): Int =
    if n < s.length && s(n) != ch then position(s)(ch, n + 1)
    else n

//---
// 同一スコープ内で重複した定義ができるっぽいけど、呼び出そうとするとコンパイルエラー

@main def no8(): Unit =
  extension (s: String)
    def foo(): Unit = println("a")

  extension (s: String)
    def foo(): Unit = println("b")

  // 呼び出そうとするとコンパイルエラー
  // "x".foo() // foo is already defined as method foo
