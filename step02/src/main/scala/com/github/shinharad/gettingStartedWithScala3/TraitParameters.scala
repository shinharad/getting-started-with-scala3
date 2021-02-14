package com.github.shinharad.gettingStartedWithScala3
package traitParameters

trait Greeting(val name: String):
  def msg = s"How are you, $name"

class C extends Greeting("Bob"):
  println(msg)

//---
// 曖昧な定義はコンパイルエラー

// C は既に Greeting を extends してるので、別の Greeting を extends することはできない
// Bob か Bill か分からん、という曖昧な状態になってしまう

// class D extends C, Greeting("Bill")

//---
// Greeting の パラメータは未指定のまま extends すると、

trait FormalGreeting extends Greeting:
  override def msg = s"How do you do, $name"

// 具象化するにしてもパラメータが未解決なので、Greeting を extends しないとダメだよと怒られる
// class E extends FormalGreeting

// パラメータを設定した Greeting を extends してあげるとコンパイルが通る
class E extends Greeting("Bob"), FormalGreeting

// もしくは override で
class E2 extends FormalGreeting:
  override val name: String = "Bob"

// 色々応用できそう
 
//---
// 同一 trait を直接 extends するのはコンパイルエラー

// class F extends Greeting, Greeting:
//   override val name: String = "Bob"
//
// class F extends Greeting, Greeting("Bob")

//---
// 同一メンバーが存在する場合はコンパイルエラー

// trait Greeting2(val name: String):
//   def msg = s"How are you, $name"

// class G extends Greeting("Bob"), Greeting2("Bill")

//---
// メンバーが重複していなければOK

trait Greeting2(val namename: String):
  def msgmsg = s"How are you, $namename"

class G extends Greeting("Bob"), Greeting2("Bill")
