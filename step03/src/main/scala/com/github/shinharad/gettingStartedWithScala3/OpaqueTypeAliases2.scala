package com.github.shinharad.gettingStartedWithScala3
package opaqueTypeAliases2

object Scope:

  opaque type Amount1 = Double
  object Amount1:
    def apply(amount: Double): Amount1 = amount

  opaque type Amount2 = Double
  object Amount2:
    def apply(amount: Double): Amount2 = amount

end Scope

def no1(): Unit =
  import Scope.*

  val a1 = Amount1(100.0)
  val a2 = Amount2(100.0)

  def f(x1: Amount1, x2: Amount2): Boolean = ???

  f(a1, a2)    // OK
  // f(a2, a1) // NG

@main def no2(): Unit =
  import Scope.*

  val a1 = Amount1(100.0)
  val a2 = Amount2(100.0)

  println(a1 == a2) // true