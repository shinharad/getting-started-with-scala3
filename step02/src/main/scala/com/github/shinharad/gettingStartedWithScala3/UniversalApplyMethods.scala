package com.github.shinharad.gettingStartedWithScala3
package universalApplyMethods

//---
// インスタンス生成で new が不要になった

class StringBuilder1(s: String):
   def this() = this("")

def no1(): Unit =

  // 今まではインスタンスの生成に new を書いていたが
  val sb1 = new StringBuilder1("abc")
  val sb2 = new StringBuilder1()

  // Scala 3 では、new が不要になった
  val sb3 = StringBuilder1("abc")
  val sb4 = StringBuilder1()

  /*
  これは、コンパニオンオブジェクトにこういうイメージの apply メソッドが自動的に追加されるため
  constructor proxies と呼ばれてる
  object StringBuilder1:
    inline def apply(s: String): StringBuilder = new StringBuilder(s)
    inline def apply(): StringBuilder = new StringBuilder()
  */

//---
// Javaのクラスも new が不要になった

def no2(): Unit =
  val list1 = java.util.ArrayList[String]()
  val map1 = java.util.HashMap[String, String]()

  // Scala 2 ではこうだった
  val list2 = new java.util.ArrayList[String]()
  val map2 = new java.util.HashMap[String, String]()

//---
// 既にコンパニオンオブジェクトに apply が実装されていた場合は、自動追加されない

class C(name: String)
object C

class D(name: String)
object D:
  def apply(name: String, age: Int): D = new D(name)

def no3(): Unit =

  // コンパニオンオブジェクトが存在していても apply は自動的に追加される
  val c = C("abc")

  // コンパニオンオブジェクトに apply が実装されていたら自動追加はされない

  // 以下はコンパイルエラー
  // val d1 = D("abc")

  val d2 = new D("abc")
