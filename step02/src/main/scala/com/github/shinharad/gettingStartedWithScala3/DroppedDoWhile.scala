package com.github.shinharad.gettingStartedWithScala3
package droppedDoWhile

// do-while は廃止された
def no1(): Unit =
  var i = 0
  def f(v: Int): Int = v / 10

  // コンパイルエラー
  // do {
  //   i += 1
  // } while (f(i) == 0)

  // インデントベースの場合
  // do
  //   i += 1
  // while (f(i) == 0)

// これからはこのように書く
@main def no2(): Unit =
  println("-" * 50)

  var i = 0
  def f(v: Int): Int = v / 10

  while
    i += 1      // <body>
    f(i) == 0   // <cond>
  do ()

  println(i)
  println("-" * 50)

// while の条件をブロック内で書けることは、"loop-and-a-half" 問題の解決にもなる
@main def no3(): Unit =
  println("-" * 50)

  val iterator = List(1, 2, 3, -1, 2, 3).iterator
  while
    val x: Int = iterator.next // <body>
    x >= 0                     // <cond>
  do print(".")

  println()
  println("-" * 50)