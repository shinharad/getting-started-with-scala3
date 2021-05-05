package com.github.shinharad.gettingStartedWithScala3
package contextBounds

import givenInstances.Ord
import givenInstances.given

//---
// Context Bounds

// 型パラメータに依存するコンテキストパラメータの共通パターンを表現するための省略形

def max[T](x: T, y: T)(using ord: Ord[T]): T =
  if ord.compare(x, y) < 0 then y else x

// Context Bounds を使用するとこのように書き換えることができる
def no1(): Unit =
  // using でコンテキストパラメータを渡す場合
  // def maximum[T](xs: List[T])(using Ord[T]): T = xs.reduceLeft(max)

  // Context Bounds を使用した場合
  def maximum[T: Ord](xs: List[T]): T = xs.reduceLeft(max)

//---
// Context Bounds から生成されるコンテキストパラメータは、含まれるメソッドやクラスの定義の最後に展開される
def no2(): Unit =
  trait C1[T]
  trait C2[T]
  trait C3[T]
  type R
  type V

  // メソッドの場合
  def f1[T: C1 : C2, U: C3](x: T)(using y: U, z: V): R = ???
  // このように展開される
  def f2[T, U](x: T)(using y: U, z: V)(using C1[T], C2[T], C3[U]): R = ???

  // クラスの場合
  class class1[T: C1 : C2, U: C3](x: T)(using y: U, z: V)
  // このように展開される
  class class2[T, U](x: T)(using y: U, z: V)(using C1[T], C2[T], C3[U])

//---
// Context Bounds はサブタイプ境界と組み合わせることができる
// 両方が存在する場合は、サブタイプ境界を先に書く

def no3(): Unit =
  trait C[T]
  type B
  type R

  def g[T <: B : C](x: T): R = ???
