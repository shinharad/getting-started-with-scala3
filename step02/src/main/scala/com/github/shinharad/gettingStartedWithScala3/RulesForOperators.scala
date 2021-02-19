package com.github.shinharad.gettingStartedWithScala3
package rulesForOperators

import scala.annotation.targetName

//---
// The infix Modifier

trait MultiSet[T]:

  infix def union(other: MultiSet[T]): MultiSet[T]

  // 記号メソッドにも infix を付けられるが冗長
  infix def **(other: MultiSet[T]): MultiSet[T]

  def difference(other: MultiSet[T]): MultiSet[T]

  @targetName("intersection")
  def *(other: MultiSet[T]): MultiSet[T]

def no1 =
  val s1, s2: MultiSet[Int] = ???

  s1 union s2         // OK
  s1 `union` s2       // also OK but unusual
  s1.union(s2)        // also OK

  s1.difference(s2)   // OK
  s1 `difference` s2  // OK
  s1 difference s2    // gives a deprecation warning

  s1 * s2             // OK
  s1 `*` s2           // also OK, but unusual
  s1.*(s2)            // also OK, but unusual

// 型にも使用できる
def no2 =
  infix type or[X, Y]
  val x: String or Int = ???

// two parameters はコンパイルエラーになるはずが、そうならない...
trait Infix:

  infix def op1(x: Int): Int            // ok
  infix def op2(x: Int)(y: Int): Int    // ok
  infix def op3(x: Int, y: Int): Int    // error: two parameters

  extension (x: Infix)
    infix def op4(y: Int): Int          // ok
    infix def op5(y1: Int, y2: Int): Int  // error: two parameters

//---
// Syntax Change

// infix 演算子が、複数行式の行頭に現れるようになった

def no3 =
  val str = "hello"
    ++ " world"
    ++ "!"

def no4 =
  val x: Int = ???
  val xs: List[Int] = ???

  // infix 演算子を行頭に書く
  def condition1 =
    x > 0
    ||
    xs.exists(_ > 0)
    || xs.isEmpty

  // 今までの書き方もコンパイルが通る
  def condition =
    x > 0 ||
    xs.exists(_ > 0) ||
    xs.isEmpty

def no5() =

  // Scala3 では ??? を infix 演算子として扱うのでこれはコンパイルエラー
  // println("hello")
  // ???
  // ??? match { case 0 => 1 }

  // それを望まない場合は、

  // 前に ; を入れるとか
  println("hello")
  ;???
  ??? match { case 0 => 1 }

  // 前に空行を入れるとか
  println("hello")

  ???
  ??? match { case 0 => 1 }
