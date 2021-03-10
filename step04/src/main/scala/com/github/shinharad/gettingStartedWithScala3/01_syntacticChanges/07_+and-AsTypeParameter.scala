package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `07_+and-AsTypeParameter`

// Scala2 では、型パラメータに `+` や `-` を指定してもコンパイルが通ったが、Scala3 ではエラーになる
// def foo[+]: Unit = ???
// def foo[-]: Unit = ???

// Scala3 ではこのようにする
def foo[T]: Unit = ???

// ただし、型の識別子としての `+` や `-` はまだ有効
type + = String
type - = Int