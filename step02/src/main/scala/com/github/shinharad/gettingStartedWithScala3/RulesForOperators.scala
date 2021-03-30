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

def no1: Unit =
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

// typeでも使用できる
def no2: Unit =
  infix type or[X, Y]
  val x: String or Int = ???

// two parameters はコンパイルエラーになるはずが、そうならない...
trait Infix:

  infix def op1(x: Int): Int            // ok
  infix def op2(x: Int)(y: Int): Int    // ok
  infix def op3(x: Int, y: Int): Int    // error: two parameters

  extension (x: Infix)
    infix def op4(y: Int): Int            // ok
    infix def op5(y1: Int, y2: Int): Int  // error: two parameters

//---
// Syntax Change

// infix 演算子を複数行の行頭で書けるようになった
def no3: Unit =
  val x: Int = ???
  val xs: List[Int] = ???

  val str = "hello"
    ++ " world"
    ++ "!"

  def condition =
    x > 0
    ||
    xs.exists(_ > 0)
    || xs.isEmpty

  // これまで通り、infix 演算子を行末に書いても一応コンパイルは通る
  val str2 = "hello" ++
    " world" ++
    "!"

  def condition2 =
    x > 0 ||
    xs.exists(_ > 0) ||
    xs.isEmpty

// 行頭に infix 演算子を書く場合、その後の式の前にスペースを少なくとも1つ入れる必要がある
def no4(): Int =
  val freezing = true
  val boiling = true

  // `|` の後にスペースがあるので、これは1つのステートメントだけど
    freezing
  | boiling

  // これは、`!` の後にスペースが無いので、別々のステートメントとして扱われる
  //   freezing
  // !boiling

  // これは3つのステートメントとして扱われる
  // `???` は構文的にはシンボリック演算子だけど、
  // どちらの出現箇所にもスペースとその後ろの式が無い
  println("hello")
  ???
  ??? match { case 0 => 1 }
