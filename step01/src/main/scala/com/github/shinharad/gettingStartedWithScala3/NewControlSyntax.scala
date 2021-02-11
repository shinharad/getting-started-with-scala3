package com.github.shinharad.gettingStartedWithScala3
package newControlSyntax

@main def no1 =
  println("-" * 20)

  val x = 10

  val r = if x < 0 then
    "negative"
  else if x == 0 then
    "zero"
  else
    "positive"

  println(r)
  println("-" * 20)

@main def no2 =
  println("-" * 20)

  val x = 10

  val r = if x < 0 then -x else x

  println(r)
  println("-" * 20)

// while
@main def no3 =
  println("-" * 20)

  var x = 10
  val f: Int => Int = _ - 1

  while x >= 0 do x = f(x)

  println(x)
  println("-" * 20)

// for
@main def no4 =
  println("-" * 20)

  val xs = List(1, 2, -1, 3, -3, 4, 5)

  val r =
    for x <- xs if x > 0
    yield x * x

  println(r)
  println("-" * 20)

@main def no5 =
  println("-" * 20)

  val xs = List(1, 2, 3)
  val ys = List(3, 4, 6)

  for
    x <- xs
    y <- ys
  do
    println(x + y)

  println("-" * 20)

// try
@main def no6 =
  println("-" * 20)

  val x = 10

  import java.io.IOException
  def body: Unit = throw IOException("io exception")
  def handle: Unit = println("# error")

  try body
  catch case ex: IOException => handle

  println("-" * 20)
