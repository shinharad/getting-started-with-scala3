package com.github.shinharad.gettingStartedWithScala3
package universalApplyMethods

//---
// 今までの定義では new を書く必要があった

class StringBuilder(s: String):
  def this() = this("")

def no1(): Unit =
  val sb1 = new StringBuilder("abc")
  // val sb2 = StringBuilder("abc") // compile error
  val sb2 = new StringBuilder()
  // val sb2 = StringBuilder() // compile error

//---
// inline def apply を定義すると、コンパニオンオブジェクトに apply メソッドが暗黙的に追加される
// これは、constructor proxies と呼ばれてる

class StringBuilder2(s: String):
  def this() = this("")
  inline def apply(s: String): StringBuilder2 = new StringBuilder2(s)
  inline def apply(): StringBuilder2 = new StringBuilder2()

// その結果、new を書かなくてもインスタンスが生成できるようになる
def no2(): Unit =
  val sb1 = StringBuilder2("abc")
  val sb2 = StringBuilder2()

//---
// もしも、コンパニオンオブジェクトに apply が明示的に定義されていたら？

// コンパニオンオブジェクトで既に apply が定義されてる場合は、暗黙的に追加されない
class StringBuilder3(s: String):
  def this() = this("")
  inline def apply(s: String): StringBuilder3 = new StringBuilder3(s)
  inline def apply(): StringBuilder3 = new StringBuilder3()

object StringBuilder3:
  // 暗黙的に追加されないので、
  // どちらか一方をコメントアウトするとコンパイルエラーになる
  def apply(s: String): StringBuilder3 = new StringBuilder3(s)
  def apply(): StringBuilder3 = new StringBuilder3()

def no3(): Unit =
  val sb1 = StringBuilder3("abc")
  val sb2 = StringBuilder3()
