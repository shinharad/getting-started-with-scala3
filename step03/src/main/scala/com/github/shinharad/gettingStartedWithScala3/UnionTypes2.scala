package com.github.shinharad.gettingStartedWithScala3
package unionTypes2

//---
// Literal-based singleton types
// https://docs.scala-lang.org/sips/42.type.html
// (since Scala 2.13)

@main def no1(): Unit =
  println("-" * 50)

  val intValue = 10
  val ten: 10 = 10

  def printInt(n: Int) = println(n)
  printInt(50)
  printInt(ten)

  def printStrict(n: 10) = println(n)
  printStrict(ten)
  // printTen(50) // compile error

  println("-" * 50)

//---
// Literal-based singleton types と Union Types を組み合わせる

@main def no2(): Unit =
  println("-" * 50)

  def passStrict(n: 1 | 2 | 3) = println(n)
  passStrict(1) // ok
  passStrict(2) // ok
  passStrict(3) // ok
  // passStrict(4) // compile error

  println("-" * 50)

  type Valid = 1 | 2 | 3
  def passStrict2(n: Valid) = println(n)
  passStrict2(1) // ok
  passStrict2(2) // ok
  passStrict2(3) // ok
  // passStrict2(4) // compile error

  println("-" * 50)

  type ValidAnd[A] = A | 1 | 2 | 3
  def passStrict3(n: ValidAnd[4]) = println(n)
  passStrict3(1) // ok
  passStrict3(2) // ok
  passStrict3(3) // ok
  passStrict3(4) // ok
  // passStrict3(5) // compile error

  println("-" * 50)

//---
// Errorの文脈を表現

@main def no3(): Unit =
  println("-" * 50)

  type ErrorOr[A] = A | "error"

  def handle(value: ErrorOr[Int]): Unit =
    value match
      case _: "error" => println("error")
      case x: Int => println(s"value: $value")

  handle(10)
  handle("error")
  // handle("errorerror") // compile error

  println("-" * 50)