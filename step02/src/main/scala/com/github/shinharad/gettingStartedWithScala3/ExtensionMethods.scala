package com.github.shinharad.gettingStartedWithScala3
package extensionMethods

import scala.util.chaining.*

case class Circle(x: Double, y: Double, radius: Double)

extension (c: Circle)
  def circumference: Double = c.radius * math.Pi * 2

@main def no1() =
  val circle = Circle(0, 0, 1)

  // infix `.` で呼び出すことができる
  circle.circumference.tap(println)

  // コンパイラは内部的にこのように解釈するので、
  // <extension> def circumference(c: Circle): Double = c.radius * math.Pi * 2
  //
  // circumference(circle) でも呼び出すことができる
  assert(circle.circumference == circumference(circle))

case class Elem(v: Int)

//---
// Operators

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
*/

def no2() =

  "ab" < "c"
  Elem(1) +: List(Elem(2), Elem(3))
  2 min 3

  // こう書くこともできる
  <("a")("c")
  +:(List(Elem(2), Elem(3)))(Elem(1))
  min(2)(3)
