package com.github.shinharad.gettingStartedWithScala3
package typeLambdas

import com.github.shinharad.gettingStartedWithScala3.functors2.*

//---
// Type Lambdas

def no1(): Unit =
  import typeclass.Functor

  // Option や List は、型パラメータが1つなので Functor に設定できる
  type F1 = Functor[Option] // OK
  type F2 = Functor[List]   // OK

  // Map は型パラメータが2つなので、そのままでは Functor に設定できない
  // type F3 = Functor[Map] // !!

  // 代わりにKeyの型を確定させたtypeエイリアスを Functor に設定する
  type IntKeyMap[A] = Map[Int, A]
  type F3 = Functor[IntKeyMap]

  // typeエイリアスを定義せずに、部分適用で Functor に設定できないだろうか？
  // しかし、部分適用は関数には適用できるが、型パラメータには適用できない
  val cube = Math.pow(_: Double, 3)
  cube(2)
  // type F4 = Functor[Map[Int, _]] // !!

  // そこで、Type Lambdas を使用する
  type F5 = Functor[[A] =>> Map[Int, A]]

  // Scala 2 では、このような書き方をしていた
  type F6 = Functor[({ type T[A] = Map[Int, A] })#T]

  // もしくは、kind-projectorを使用してこのように書いていた
  // （"-Ykind-projector" を設定すれば、Scala 3 でもこのように書くことができる）
  type F7 = Functor[Map[Int, *]]

//---
// given の定義で Type Lambdas を使用する
def no2(): Unit =
  import typeclass.Functor

  given Functor[[A] =>> Map[Int, A]] with
    extension [A](m: Map[Int, A])
      def map[B](f: A => B): Map[Int, B] =
        m.map((k, v) => (k, f(v)))
