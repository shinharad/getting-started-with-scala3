package com.github.shinharad.gettingStartedWithScala3
package monads

import com.github.shinharad.gettingStartedWithScala3.functors2.typeclass.Functor

//---
// Monads

object typeclass:

  // F[_] 型の Monad は、Functor[F] にさらに2つの演算子を加えたもの
  // - flatMap: A => F[B] 型の関数が与えられたとき、F[A] を F[B] に変換する
  // - pure: 単一の値 A から F[A] を生成する（ドキュメントでは、Applicative Functorが省略されている）
  trait Monad[F[_]] extends Functor[F]:
    def pure[A](x: A): F[A]

    extension [A](x: F[A])
      def flatMap[B](f: A => F[B]): F[B]
      def map[B](f: A => B) = x.flatMap(f.andThen(pure))

//---
// List

object instance1:
  import typeclass.Monad

  given listMonad: Monad[List] with
    def pure[A](x: A): List[A] =
      List(x)
    extension [A](xs: List[A])
      def flatMap[B](f: A => List[B]): List[B] =
        xs.flatMap(f) // rely on the existing `flatMap` method of `List`

@main def no1(): Unit = 
  import typeclass.Monad
  import instance1.given

  println("-" * 50)

  assert(List(1) == summon[Monad[List]].pure(1))

  def assertTransformation[F[_]: Monad, A, B](expected: F[B], original: F[A], mapping: A => F[B]): Unit =
    assert(expected == original.flatMap(mapping))

  assertTransformation(List(2, 4, 6), List(1, 2, 3), x => List(x * 2))

  println("-" * 50)

//---
// Option

object instance2:
  import typeclass.Monad

  given optionMonad: Monad[Option] with
    def pure[A](x: A): Option[A] =
      Option(x)
    extension [A](xo: Option[A])
      def flatMap[B](f: A => Option[B]): Option[B] = xo match
        case Some(x) => f(x)
        case None => None

@main def no2(): Unit =
  import typeclass.Monad
  import instance2.given

  println("-" * 50)

  assert(Option(1) == summon[Monad[Option]].pure(1))

  def assertTransformation[F[_]: Monad, A, B](expected: F[B], original: F[A], mapping: A => F[B]): Unit =
    assert(expected == original.flatMap(mapping))

  assertTransformation(Option(2), Option(1), x => Option(x * 2))

  println("-" * 50)

//---
// Reader

trait Config

def compute(i: Int)(config: Config): String = ???
def show(str: String)(config: Config): Unit = ???

object instance3:
  import typeclass.Monad

  type ConfigDependent[Result] = Config => Result

  given configDependentMonad: Monad[ConfigDependent] with

    def pure[A](x: A): ConfigDependent[A] =
      config => x

    extension [A](x: ConfigDependent[A])
      def flatMap[B](f: A => ConfigDependent[B]): ConfigDependent[B] =
        config => f(x(config))(config)

def no3(): Unit =
  import typeclass.Monad
  import instance3.given

  def before(i: Int)(config: Config): Unit =
    show(compute(i)(config))(config)

  def after(i: Int): Config => Unit =
    compute(i).flatMap(show)

//---
// Reader
// Type Lambdas を使用した場合

object instance4:
  import typeclass.Monad

  given configDependentMonad: Monad[[Result] =>> Config => Result] with

    def pure[A](x: A): Config => A =
      config => x

    extension [A](x: Config => A)
      def flatMap[B](f: A => Config => B): Config => B =
        config => f(x(config))(config)

def no4(): Unit =
  import typeclass.Monad
  import instance4.given

  def before(i: Int)(config: Config): Unit =
    show(compute(i)(config))(config)

  def after(i: Int): Config => Unit =
    compute(i).flatMap(show)
