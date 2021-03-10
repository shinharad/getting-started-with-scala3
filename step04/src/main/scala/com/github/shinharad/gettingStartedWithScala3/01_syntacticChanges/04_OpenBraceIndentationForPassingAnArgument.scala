package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `04_openBraceIndentationForPassingAnArgument`

def test(name: String)(f: => Unit) = ???

//---
// scalacOptions の以下を順番に有効にしてみてください
// - "-explain"
// - "-source:3.0-migration"

// Scala2 では、改行後の引数を中括弧で囲めるが推奨されていない
// Scala3 では警告が表示される
// def no1(): Unit =
//   test("my test")
//   {
//     assert(1 == 1)
//   }

// Scala2 では、このように書くのが正しい
def no2(): Unit =
  test("my test") {
    assert(1 == 1)
  }

// no1 を Scala3 で敢えて書くならこう書くのが正しい
def no3(): Unit =
  test("my test")
    {
    assert(1 == 1)
  }
