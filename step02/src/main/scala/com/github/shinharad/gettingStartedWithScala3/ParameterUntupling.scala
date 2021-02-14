package com.github.shinharad.gettingStartedWithScala3
package parameterUntupling

def parameterUntupling =
  val xs: List[(Int, Int)] = ???

  // 今までは
  xs map {
    case (x, y) => x + y
  }

  // Scala 3 からは
  xs map {
    (x, y) => x + y
  }

  xs map (_ + _)
