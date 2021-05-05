package com.github.shinharad.gettingStartedWithScala3
package importingGivens

//---
// Importing Givens
// givens をインポートするために使用する

object A:
  class TC
  given tc: TC = ???
  def f(using TC) = ???

// `import A.*` は、A の givens 以外のすべてをインポートする
def no1(): Unit =
  import A.*
  // import A.TC
  // import A.f

  // TC を参照できる
  val t = TC()

  // TC の givens をインポートしていないはずなのに使えてしまう...
  f

  def g(using TC) = ???


// `import A.given` は、A の givens のみをインポートする
def no2(): Unit =
  import A.given

  // TC は参照できない
  // val t = TC()

  // TC の givens はインポートしているので使える
  A.f

// `import A.{ given, * }` は、すべてのメンバをインポートする
def no3(): Unit =
  import A.{ given, * }

  // TC を参照できる
  val t = TC()

  // TC の givens をインポートしてるので使える
  f

//---
// Importing By Type

// 個別の givens をインポートする場合（By-typeインポート）
def no4(): Unit =
  import A.TC

  // By-typeインポート
  import A.given TC

  // 複数の型の givens をインポートする場合はこのように書く
  // import A.{given T1, ..., given Tn}

import scala.concurrent.ExecutionContext
trait Monoid[A]

object Instances:
  given intOrd: Ordering[Int] = ???
  given listOrd[T: Ordering]: Ordering[List[T]] = ???
  given ec: ExecutionContext = ???
  given im: Monoid[Int] = ???

// パラメータ化された型の givens をインポートするには、ワイルドカード引数を使用する
def no5(): Unit = 
  import Instances.{ given Ordering[?], given ExecutionContext }

  intOrd
  listOrd
  ec

  // この場合、Monoid はインポートしていないので参照できない
  // im

// By-typeインポートは、By-nameインポートと混在できる
def no6(): Unit =
  // import 句の中に By-type と By-name が混在する場合、By-type は最後に書く必要がある
  import Instances.{ im, given Ordering[?] }

  // 末尾にBy-nameを書くとコンパイルエラー
  // import Instances.{ given Ordering[?], im }

  intOrd
  listOrd
  im

  // ExecutionContext はインポートしていないので参照できない
  // ec

//---
// Migration

object B:
  class TC
  implicit val tc: TC = ???
  def f(implicit t: TC) = ???

// Importing Givens は、マイグレーションを考慮して古いスタイルの暗黙的な定義もスコープに含める
// ただし、Scala 3.1 以降では、非推奨となり段階的に削除される
// つまり、古いスタイルの暗黙的な定義をしているライブラリは、Scala 3.1 以降で使えなくなる可能性があるってこと？
def no7(): Unit =
  import B.given

  B.f
