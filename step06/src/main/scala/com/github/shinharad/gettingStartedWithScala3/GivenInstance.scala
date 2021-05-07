package com.github.shinharad.gettingStartedWithScala3
package givenInstances

//---
// Given instances (またはシンプルに "givens") は、特定の型に対する振る舞いを定義し、
// コンテキストパラメータへの引数を合成する役割を果たす

trait Ord[T]:
  def compare(x: T, y: T): Int
  extension (x: T) def < (y: T) = compare(x, y) < 0
  extension (x: T) def > (y: T) = compare(x, y) > 0

// Ord[Int] の givens
given intOrd: Ord[Int] with
  def compare(x: Int, y: Int) =
    if x < y then -1 else if x > y then +1 else 0

// Ord[List[T]] の givens
// この givens が存在するためには using で指定した Ord[T] の givens が存在しなければならない
given listOrd[T](using ord: Ord[T]): Ord[List[T]] with
  def compare(xs: List[T], ys: List[T]): Int = (xs, ys) match
    case (Nil, Nil) => 0
    case (Nil, _) => -1
    case (_, Nil) => +1
    case (x :: xs1, y :: ys1) =>
      val fst = ord.compare(x, y)
      if fst != 0 then fst else compare(xs1, ys1)

//---
// Anonymous Givens

// givens は、名前を省略することができる
object no2:
  given Ord[Int] with
    def compare(x: Int, y: Int) = ???

  given [T](using Ord[T]): Ord[List[T]] with
    def compare(xs: List[T], ys: List[T]): Int = ???

  // この場合、コンパイラは自動的にこのような名前を付与する
  given_Ord_Int
  given_Ord_List

//---
// Alias Givens

// givens のエイリアスを定義することができる
def no3(): Unit =
  import java.util.concurrent.ForkJoinPool
  import scala.concurrent.ExecutionContext

  // import scala.concurrent.JavaConversions.asExecutionContext
  // given global: ExecutionContext = ForkJoinPool() // method asExecutionContext in object JavaConversions is deprecated since 2.13.0: Use `ExecutionContext.fromExecutorService` instead

  // givens のエイリアスはメモ化される
  // つまり、最初にアクセスしたときに評価され、以降はそれを使い回す
  // この操作はスレッドセーフ
  given global: ExecutionContext = ExecutionContext.fromExecutorService(ForkJoinPool())

// エイリアスは匿名にすることもできる
def no4(): Unit =
  trait Position
  class EnclosingTree:
    def position: Position = ???

  val enclosingTree = EnclosingTree()

  // Anonymous
  given Position = enclosingTree.position

  trait Config
  trait Factory
  class MemoizingFactory(config: Config) extends Factory

  // Anonymous
  given (using config: Config): Factory = MemoizingFactory(config)

//---
// Pattern-Bound Given Instances

// given は、パターンの中にも書くことができる
def no5(): Unit =

  trait Context
  val applicationContexts: Seq[Context] = ???
  def f(using Context): String = ???

  // for の中で
  for given Context <- applicationContexts do f

  // パターンマッチの中で
  val pair: (Context, Int) = ???
  pair match
    case (ctx @ given Context, y) => f

//---
// Negated Givens

// scala.util.NotGiven を使用することで、
// givens がスコープに存在しないかどうかを調べることができる

@main def no6(): Unit =
  import scala.util.NotGiven

  trait Tagged[A]

  case class Foo[A](value: Boolean)
  object Foo:
    given fooTagged[A](using Tagged[A]): Foo[A] = Foo(true)
    given fooNotTagged[A](using NotGiven[Tagged[A]]): Foo[A] = Foo(false)

  given Tagged[Int] with {}
  // given Tagged[Int]() // TODO 今後こういう書き方ができるようになるっぽい

  println(summon[Foo[Int]].value)
  println(summon[Foo[String]].value)

  assert(summon[Foo[Int]].value) // fooTagged is found
  assert(!summon[Foo[String]].value) // fooNotTagged is found
