package com.github.shinharad.gettingStartedWithScala3
package traitParametersPart2

import scala.util.chaining.*

//---
// 番外編

trait Greeting(val name: String):
  def msg = s"How are you, $name"

trait FormalGreeting extends Greeting:
  override def msg = s"How do you do, $name"

// コンパイルエラー
// class A extends FormalGreeting

class B extends Greeting("Bob"), FormalGreeting
class C extends FormalGreeting, Greeting("Bob")

// コンパイルエラー
// trait FormalGreeting2(val name: String) extends Greeting:
//   def msg = s"How do you do, $name"

trait FormalGreeting3(override val name: String) extends Greeting:
  override def msg = s"How do you do, $name"

class D extends FormalGreeting3("Bill"), Greeting("Bob")
class E extends Greeting("Bob"), FormalGreeting3("Bill")

// コンパイルエラー
// class F extends FormalGreeting3("Bill")
// class G extends FormalGreeting3, Greeting("Bob")

// コンパイルエラー
// trait FormalGreeting4(override val name: String) extends Greeting(name):
//   override def msg = s"How do you do, $name"

@main def no1(): Unit =
  println("-" * 50)

  (new B).msg.tap(println)
  (new C).msg.tap(println)
  // => How do you do, Bob

  println("-" * 10)

  (new D).msg.tap(println)
  (new E).msg.tap(println)
  // => How do you do, Bill

  println("-" * 50)
