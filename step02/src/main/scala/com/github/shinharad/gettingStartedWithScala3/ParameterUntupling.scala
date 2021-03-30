package com.github.shinharad.gettingStartedWithScala3
package parameterUntupling

def parameterUntupling(): Unit =
  val xs: List[(Int, Int)] = ???

  // 今までは
  xs map {
    case (x, y) => x + y
  }

  // Scala 3 からは
  xs map {
    (x, y) => x + y
  }

  // 省略してこんな感じに書ける
  xs map (_ + _)
