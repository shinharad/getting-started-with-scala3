package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `05_wrongIndentation`

// 以下のコメントアウトしているコードは、Scala 2 ではコンパイルが通るが、
// Scala 3 ではインデントが間違ってるのでコンパイルが通らない
// この場合は、scalafmt などでコードフォーマットする必要がある

// val foo_wrong =
//     Vector(1) ++
//   Vector(2) ++
//     Vector(3)

// def bar_wrong: (Int, Int) = {
//   val foo = 1.0
//   val bar = foo
//     (1, 1)
// }

val foo =
  Vector(1) ++
  Vector(2) ++
  Vector(3)

def bar: (Int, Int) = {
  val foo = 1.0
  val bar = foo
  (1, 1)
}
