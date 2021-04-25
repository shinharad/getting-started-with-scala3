package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `07_+and-AsTypeParameter`

// Scala 2 では、型パラメータに `+` や `-` を指定してもコンパイルが通ったが、Scala 3 ではエラーになる
// def foo[+]: Unit = ???
// def foo[-]: Unit = ???

// Scala 3 ではこのようにする
def foo[T]: Unit = ???

// ただし、型の識別子としての `+` や `-` はまだ有効
type + = String
type - = Int