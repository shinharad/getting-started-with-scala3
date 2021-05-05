package com.github.shinharad.gettingStartedWithScala3
package functors2

// Extension Methods を使用することで、Functor のインスタンスに直接 map 関数を生やすことができる
// そうすれば、summon[Functor[F]] を取り除くことができる
object typeclass:
  trait Functor[F[_]]:
    extension [A](x: F[A])
      def map[B](f: A => B): F[B]

object instance:
  import typeclass.Functor

  given Functor[List] with
    extension [A](xs: List[A])
      def map[B](f: A => B): List[B] =
        xs.map(f) // List already has a `map` method

@main def no1(): Unit =
  import typeclass.Functor
  import instance.given

  println("-" * 50)

  def assertTransformation[F[_]: Functor, A, B](expected: F[B], original: F[A], mapping: A => B): Unit =
    assert(expected == original.map(mapping))

  assertTransformation(List("a1", "b1"), List("a", "b"), elt => s"${elt}1")

  println("-" * 50)
