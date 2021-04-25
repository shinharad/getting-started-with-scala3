package com.github.shinharad.gettingStartedWithScala3
package intersectionTypes

trait Resettable:
  def reset(): Unit

trait Growable[T]:
  def add(t: T): Unit

//---
// Intersection Types をメソッドの引数に使用する

// 引数 x は、Resettable であり、Growable[String] でもある型
def f(x: Resettable & Growable[String]) =
  x.reset()
  x.add("first")

@main def no1(): Unit =

  class A extends Resettable, Growable[String]:
    override def reset(): Unit = println("reset")
    override def add(t: String): Unit = println(s"add: $t")

  f(new A)

  // & 演算子は可換的なので、逆にしてもOK
  class B extends Growable[String], Resettable:
    override def reset(): Unit = println("reset")
    override def add(t: String): Unit = println(s"add: $t")

  f(new B)

  // メソッドの引数の型を逆にしてもOK
  def g(x: Growable[String] & Resettable) =
    x.reset()
    x.add("first")

  g(new A)
  g(new B)

  // 両方を満たしていない場合はコンパイルエラー
  // class C extends Resettable:
  //   override def reset(): Unit = println("reset")

  // f(new C)

  // これもエラー
  // class D extends Growable[String]:
  //   override def add(t: String): Unit = println(s"add: $t")

  // f(new D)

//---
// A と B に同一のメンバが存在する場合、
// A と B を継承したメンバの型は、それぞれのメンバの型の Intersection Types になる

def no2(): Unit =
  trait A:
    def foo: String

  trait B:
    def foo: Int

  // メンバの型が String と Int なので、String & Int になる
  trait C extends A, B:
    override def foo: String & Int

// List の場合は、List[A] & List[B] となるが、
// List は、covariant（共変）なので、これらをさらに単純化して
// List[A & B] とすることができる

def no3(): Unit =
  trait A:
    def children: List[A]

  trait B:
    def children: List[B]

  class C extends A, B:
    override def children: List[A & B] = ???

  val x: A & B = new C
  val ys: List[A & B] = x.children

//---
// 3種類以上の型でも Intersection Types を作れる

def no4(): Unit = 
  trait A
  trait B
  trait C

  def f(x: A & B & C): Unit = ???

  class D extends A, B, C
  f(new D)

//---
// Scala 2 では、with を使って Compound Types （複合型）で表現していた
// ただし、Compound Types は、Intersection Types とは異なり、
// 同一メンバで異なる型が存在する場合は、Linearization のルールでどちらかの型に推論されてしまう

def no5(): Unit =

  //---
  // 以下は Scala 2 での話

  trait A {
    def foo: String
  }
  trait B {
    def foo: Int
  }

  def f(x: A with B): Unit = {
    // Linearization のルールで Int 型になる（String with Int とはならない）
    val a: Int = x.foo
  }

  def g(x: B with A): Unit = {
    // Linearization のルールで String 型になる（Int with String とはならない）
    val a: String = x.foo
  }

  //---
  // ここから Scala 3 の話

  // Scala 3 では with を & に置き換えるので、
  // この場合は Intersection Types になる

  trait C:
    def foo: String
  trait D:
    def foo: Int

  def ff(x: C with D): Unit =
    val a: String with Int = x.foo
    val b: Int with String = x.foo // 可換的なのでこれもOK

    val c: String & Int = x.foo
    val d: Int & String = x.foo

  def gg(x: D with C): Unit =
    val a: Int with String = x.foo
    val b: String with Int = x.foo // 可換的なのでこれもOK

    val c: Int & String = x.foo
    val d: String & Int = x.foo

//---
// Stackable Modifications としての with は今まで通り Linearization のルールに従う

@main def no6(): Unit =
  class Animal:
    def action(ball: String) = println("Action : " + ball)

  trait Cat extends Animal:
    override def action(ball: String) = super.action("Cat-" + ball)

  trait Dog extends Animal:
    override def action(ball: String) = super.action("Dog-" + ball)

  trait Monkey extends Animal:
    override def action(ball: String) = super.action("Monkey-" + ball)

  val animal1 = new Animal with Monkey with Dog
  animal1.action("action-1") // "Action : Monkey-Dog-action-1"

  val animal2 = new Animal with Dog with Monkey
  animal2.action("action-2") // "Action": Dog-Monkey-action-2"
