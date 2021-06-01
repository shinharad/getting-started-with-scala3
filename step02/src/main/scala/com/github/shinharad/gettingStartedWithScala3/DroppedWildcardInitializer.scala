package com.github.shinharad.gettingStartedWithScala3
package droppedWildcardInitializer

// Scala 2 では、初期化されていないフィールドを示すために _ が使われていたが、
// Scala 3の将来のバージョンで廃止される予定
@main def no1(): Unit =
  println("-" * 50)

  class A:
    var x: String = _

    def initialize(): Unit =
      x = "abc"

  val a = new A
  println(a.x) // null

  a.initialize()
  println(a.x) // abc

  println("-" * 50)

// Scala 3 では uninitialized を使用する
@main def no2(): Unit =
  println("-" * 50)

  import scala.compiletime.uninitialized

  class A:
    var x: String = uninitialized

    def initialize(): Unit =
      x = "abc"

  val a = new A
  println(a.x) // null

  a.initialize()
  println(a.x) // abc

  println("-" * 50)
