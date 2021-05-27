package com.github.shinharad.gettingStartedWithScala3
package unionTypes

case class UserName(value: String)
case class Email(value: String)

case class User(name: UserName)

def lookupName(name: String): Unit = println("call lookupName")
def lookupEmail(email: String): Unit = println("call lookupEmail")

//---
// Union Types をメソッドの引数に使用する

// 引数 id は、UserName か Email のどちらかの型
def help(id: UserName | Email): Unit =
  id match
    case UserName(value) => lookupName(value)
    case Email(value) => lookupEmail(value)

//---
// どちらの型でも渡すことができる

@main def no1(): Unit =
  val name = UserName("Eve")
  val email = Email("abc@xxx.xxx")

  help(name) // call lookupName
  help(email) // call lookupEmail

//---
// Union Types は、型を省略せずに明示する必要がある

@main def no2(): Unit =
  val name = UserName("Eve")
  val email = Email("abc@xxx.xxx")

  // これは明示してないので、Object & Product になる
  // （UserName と Email の least upper bound である、Object & Product）
  val result1 = if true then name else email

  // これはコンパイルエラー
  // help(result1)

  // これは明示してるので、 Union Types になる
  val result2: UserName | Email = if true then name else email

  help(result2) // call lookupName

//---
// | 演算子は可換的なので逆にしてもOK

def no3(): Unit =
  val name = UserName("Eve")
  val email = Email("abc@xxx.xxx")

  def help(id: Email | UserName): Unit =
    id match
      case UserName(value) => lookupName(value)
      case Email(value) => lookupEmail(value)
  
  val result: UserName | Email = if true then name else email

  help(result)
  
//---
// どちらかの型、なので共通する振る舞いを持っているわけではない
// つまり、型ごとに振る舞いが違う

@main def no4(): Unit =
  trait A:
    def foo(): Unit = println("foo")

  trait B:
    def fuga(): Unit = println("fuga")

  class C extends A, B
  
  // A か B のどちらかなので、これはできない
  // def f(x: A | B) =
  //   x.foo()
  
  // パターンマッチで型を判別する必要がある
  def f(x: A | B) =
    x match
      case a: A => a.foo()
      case b: B => b.fuga()
  
  f(new A {}) // foo
  f(new B {}) // fuga
  f(new C) // foo

//---
// メソッドの結果型として、Union Types を返すこともできる

def no5(): Unit =
  trait A:
    def foo(): A = ???

  trait B:
    def fuga(): B = ???

  def f(x: A | B): A | B =
    x match
      case a: A => a.foo()
      case b: B => b.fuga()

//---
// 3種類以上の型でも Union Types を作れる

def no6() : Unit = 
  trait A
  trait B
  trait C

  def f(x: A | B | C): Unit = ???

  f(new A {})
  f(new B {})
  f(new C {})

//---
// ヘテロジニアスなListも作れる

def no7() : Unit = 
  val xs: List[Int | String] = List("a", 1, "b", 2)

  trait A
  trait B
  class C extends A, B

  // Int または、 A であり B でもある型
  val ys: List[Int | A & B] = List(1, new C, 2, 3)
