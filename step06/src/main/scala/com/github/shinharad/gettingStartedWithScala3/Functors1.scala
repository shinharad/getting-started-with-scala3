package com.github.shinharad.gettingStartedWithScala3
package functors1

//---
// Functors

// - ある型の Functor は、その型の値を写像（mapped over）する機能を提供する
// - つまり、値の形状（shepe）を維持したまま値を変換する関数を適用することができる

object typeclass:

  // Functor 型クラスの定義
  // - F は型コンストラクタで、型パラメータを与えると、具体的な型となる
  // - F[_] と定義することで、F が任意の型を引数に取ることを表現している
  // - 型コンストラクタ F[_] の Functor は、型 A => B の関数 f を適用することで、F[A] を F[B] に変換する能力を表している
  trait Functor[F[_]]:
    def map[A, B](x: F[A], f: A => B): F[B]

object instances:
  import typeclass.Functor

  // List 型に対して Functor のインスタンスを定義
  given Functor[List] with
    def map[A, B](x: List[A], f: A => B): List[B] =
      x.map(f) // List already has a `map` method

@main def no1(): Unit =
  import typeclass.Functor
  import instances.given

  println("-" * 50)

  def assertTransformation[F[_]: Functor, A, B](expected: F[B], original: F[A], mapping: A => B): Unit =
    assert(expected == summon[Functor[F]].map(original, mapping))

  // このインスタンスがスコープに入っていると、Functor が期待される場所では、コンパイラは List を使用することを受け入れる
  assertTransformation(List("a1", "b1"), List("a", "b"), elt => s"${elt}1")

  println("-" * 50)
