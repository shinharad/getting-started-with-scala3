package com.github.shinharad.gettingStartedWithScala3
package implicitConversions

import scala.language.implicitConversions

trait Token
final class KeyWord(value: String) extends Token

//---
// Scala 3 から Implicit Conversion は、scala.Conversion を使用する

def no1(): Unit =
  
  // String から Token への Implicit Conversion を定義
  given Conversion[String, Token] with
    def apply(str: String): Token = KeyWord(str)

  val a: Token = "abc"

//---
// エイリアスを使えばより簡潔に表現できる

def no2(): Unit =

  given Conversion[String, Token] = KeyWord(_)

  val a: Token = "abc"

//---
// Implicit Conversion は、次の3つの場合にコンパイラによって自動的に適用される

// 1. 式 `e` が型 `T` を持ち、`T` が式の期待される型 `S` に適合しない場合
def no3(): Unit =
  class T
  class S

  given Conversion[T, S] = _ => S()

  val e: T = T()
  val s: S = e

// 2. `e.m` では、`e` は `T` 型だが、`T` はメンバー `m` を定義していない場合
def no4(): Unit =
  class T
  class S(val m: String)

  given Conversion[T, S] = _ => S("abc")

  val e: T = T()

  val m = e.m

// 3. `T` 型の `e` を持つ `e.m(args)` において、`T` が `m` という名前のメンバを定義していても、
//    そのメンバーのどれもが引数 `args` に適用できない場合
def no5(): Unit =
  class T:
    def m(): String = ???
  class S:
    def m(args: Array[String]): String = ???

  given Conversion[T, S] = _ => S()

  val e: T = T()

  e.m(Array("abc"))

//---
// magnet パターン
// オーバーロードされたメソッドを定義する代わりに、
// Implicit Conversionを使用することで、様々な引数の型に対応できる

def no6(): Unit =
  import scala.concurrent.Future

  case class HttpResponse()
  case class StatusCode()

  object Completions:

    enum CompletionArg:
      case Error(s: String)
      case Response(f: Future[HttpResponse])
      case Status(code: Future[StatusCode])

    object CompletionArg:
      given fromString: Conversion[String, CompletionArg]                 = Error(_)
      given fromFuture: Conversion[Future[HttpResponse], CompletionArg]   = Response(_)
      given fromStatusCode: Conversion[Future[StatusCode], CompletionArg] = Status(_)

    import CompletionArg.*

    def complete[T](arg: CompletionArg) = arg match
      case Error(s) => ???
      case Response(f) => ???
      case Status(code) => ???

  import Completions.*

  complete("result")
  complete(Future.successful(HttpResponse()))
  complete(Future.successful(StatusCode()))
