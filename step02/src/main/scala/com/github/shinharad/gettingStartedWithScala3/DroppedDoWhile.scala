package com.github.shinharad.gettingStartedWithScala3
package droppedDoWhile

// do-while は廃止された
def droppedDoWhile1_ng(): Unit =
   var i = 0
   def f(v: Int): Int = v / 10

  // コンパイルエラー
  //  do {
  //    i += 1
  //  } while (f(i) == 0)

  //  do
  //    i += 1
  //  while (f(i) == 0)

// これからはこのように書く
@main def droppedDoWhile2_ok(): Unit =
   var i = 0
   def f(v: Int): Int = v / 10

   while
     i += 1      // <body>
     f(i) == 0   // <cond>
   do ()

// while の条件をブロック内で書けることは、"loop-and-a-half" 問題の解決にもなる
@main def droppedDoWhile3(): Unit =
   val iterator = List(1, 2, 3, -1, 2, 3).iterator
   while
     val x: Int = iterator.next // <body>
     x >= 0                     // <cond>
   do print(".")
