package com.github.shinharad.gettingStartedWithScala3
package semigroupsAndMonoids

//---
// Semigroups and monoids

trait Semigroup[T]:
  extension (x: T) def combine (y: T): T

trait Monoid[T] extends Semigroup[T]:
  def unit: T

// Monoid 型クラスのインスタンス
// この Monoid 型クラスの String 型と Int 型に対するインスタンスの実装は以下の通り
given Monoid[String] with
  extension (x: String) def combine (y: String): String = x.concat(y)
  def unit: String = ""

given Monoid[Int] with
  extension (x: Int) def combine (y: Int): Int = x + y
  def unit: Int = 0

// この Monoid は、このように Context bound として使用できる
def combineAll[T: Monoid](xs: List[T]): T =
  xs.foldLeft(summon[Monoid[T]].unit)(_.combine(_))

// summon[...] を無くすには、Monoid のコンパニオンオブジェクトをこのように定義する
object Monoid:
  def apply[T](using m: Monoid[T]) = m

def combineAll2[T: Monoid](xs: List[T]): T =
  xs.foldLeft(Monoid[T].unit)(_.combine(_))
