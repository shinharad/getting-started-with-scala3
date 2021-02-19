package com.github.shinharad.gettingStartedWithScala3
package droppedAutoApplication

type T = String

//---
// Scala2 系では、nullary method を引数なしで呼び出した場合、暗黙的に () が挿入されていたが、
// Scala3 では厳密にチェックされるようになった

// 以下はコンパイルエラー
// def no1_ng(): T =
//   def next(): T = ??? // nullary method
//   next  // is expanded to next()

// パラメータの syntax は正確に従う必要がある
def no1_ok(): T =
  def next(): T = ??? // nullary method
  next()

// 呼び出し側が呼び出し先のパラメータに正確に従っていればOK
def no2(): T =
  def next: T = ???
  next

//---
// Java の場合は、このルールに対して寛容になってる
def no3(): Unit =
  val xs = List(1, 2, 3)

  // これを、
  xs.toString().length()

  // こうに書くのは、uniform access principle に準拠しているため、Scala のイディオムとなっている
  // 副作用の無いメソッドはまるでフィールドにアクセスしているかのように () を省略できる
  xs.toString.length

  // Java で定義されたメソッドは、この区別がつかないため、ルールから除外されている
  val sb = new java.lang.StringBuilder()
  sb.length()
  sb.length

//---
// override するときメソッドの引数リストは一致している必要がある

// これはコンパイルエラー
// class A:
//   def next(): Int = ???
// class B extends A:
//   override def next: Int = ??? // overriding error: incompatible type

// これもコンパイルエラー
// class C:
//   def next: Int = ???
// class D extends C:
//   override def next(): Int = ??? 

// 一致していればOK
class E:
  def next(): Int = ???
class F extends E:
  override def next(): Int = ???

class G:
  def next: Int = ???
class H extends G:
  override def next: Int = ???
