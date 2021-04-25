package com.github.shinharad.gettingStartedWithScala3
package changesInOverloadResolution

//---
// Looking Beyond the First Argument List

// Scala 3 のオーバーロードは、複数の引数リストが存在する場合、
// 最初の引数リストだけでなく、すべての引数リストの型を考慮するようになった
object No1:
  def f(x: Int)(y: String): Int = 0
  def f(x: Int)(y: Int): Int = 0

  f(3)("") // ok

  // Scala 2 でこのようなコードを書くとコンパイルエラーになっていた
  /*
  f(3)("")
  ^
  <pastie>error: ambiguous reference to overloaded definition,
  both method f in object No1 of type (x: Int)(y: Int): Int
  and  method f in object No1 of type (x: Int)(y: String): Int
  match argument types (Int)
  */

// 引数リストが3つ以上の場合でもオーバーロードが可能
object No2:
  def f(x: Int)(y: Int)(z: Int): Int = 0
  def f(x: Int)(y: Int)(z: String): Int = 0

  f(2)(3)(4)     // ok
  f(2)(3)("")    // ok

//---
// Parameter Types of Function Values

// Scala 3 では、オーバーロードされた最初の引数リストに、
// 欠損したパラメータ型（missing parameter types）を持つ関数値を渡せるようになった
object No3:
  def f(x: Int, f2: Int => Int) = f2(x)
  def f(x: String, f2: String => String) = f2(x)

  f("a", _.toUpperCase)
  f(2, _ * 2)

  // Scala 2 でこのようなコードを書くとコンパイルエラーになっていた
  /*
  missing parameter type for expanded function ((<x$1: error>) => x$1.toUpperCase)
  [error]   f("a", _.toUpperCase)
  [error]          ^
  [error] one error found
  */
