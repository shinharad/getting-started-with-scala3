package com.github.shinharad.gettingStartedWithScala3
package deprecatedNonlocalReturns

import scala.util.chaining.*

// ネストされた匿名関数からのreturn（大域脱出）
@main def no1(): Unit =
  def f(xs: List[Int]): Int =
    xs.foreach { x =>
      return x
    }
    0

  f(List(1, 2, 3)).tap(println)

//---
// try catch してみる
@main def no2(): Unit =
  def f(xs: List[Int]): Int =
    try
      xs.foreach { x =>
        return x
      }
    catch case e => e.getClass.tap(println) // scala.runtime.NonLocalReturnControl
    0

  f(List(1, 2, 3)).tap(println)

//---
// Scala 3 では、代替である scala.util.control.NonLocalReturns を使用する

import scala.util.control.NonLocalReturns.*

extension [T](xs: List[T])
  def has(elem: T): Boolean = returning {
    for x <- xs do
      if x == elem then throwReturn(true)
    false
  }

@main def no3(): Unit =
  val xs = List(1, 2, 3, 4, 5)
  assert(xs.has(2) == xs.contains(2))