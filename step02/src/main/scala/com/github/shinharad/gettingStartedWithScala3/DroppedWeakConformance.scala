package com.github.shinharad.gettingStartedWithScala3
package droppedWeakConformance

// weak conformanceの主な動機は、
// 以下のような式を List[Double] に型付けすることだった
val no1 = List(1.0, math.sqrt(3.0), 0, -3.3) // : List[Double]

// しかし、Scala2 では weak conformance の対象に Char も含まれていたため、
// 以下も List[Double] として型付けされていた
// これは、意図した使い方ではなかった
def no2 = 
  val n: Int = 3
  val c: Char = 'X' // Char が Double に変換されていた
  val d: Double = math.sqrt(3.0)
  List(n, c, d) // Scala2 では、List[Double]

// そこで、Scala3 では weak conformance を Int リテラルに限定した
// その結果、以下は Int、Char、Double の least upper bound である AnyVal に変換されるので、
// List[AnyVal] として型付けされる
def no3: List[AnyVal] = 
  val n: Int = 3
  val c: Char = 'X'
  val d: Double = math.sqrt(3.0)
  List(n, c, d) // used to be: List[Double], now: List[AnyVal]
