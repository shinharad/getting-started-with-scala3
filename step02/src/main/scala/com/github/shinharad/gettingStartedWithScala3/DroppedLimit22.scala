package com.github.shinharad.gettingStartedWithScala3
package droppedLimit22

import scala.util.chaining.*

// 22の制限は廃止された！

val function23 = (
  x1: Int, x2: Int, x3: Int, x4: Int, x5: Int,
  x6: Int, x7: Int, x8: Int, x9: Int, x10: Int,
  x11: Int, x12: Int, x13: Int, x14: Int, x15: Int,
  x16: Int, x17: Int, x18: Int, x19: Int, x20: Int,
  x21: Int, x22: Int, x23: Int) =>
  x1 + x2 + x3 + x4 + x5
  + x6 + x7 + x8 + x9 + x10
  + x11 + x12 + x13 + x14 + x15
  + x16 + x17 + x18 + x19 + x20
  + x21 + x22 + x23

val tuple23 =
  (1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
  11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
  21, 22, 23)

@main def no1(): Unit =
  println("-" * 50)

  function23(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)
    .tap(println)

  println(tuple23)

  println("-" * 50)