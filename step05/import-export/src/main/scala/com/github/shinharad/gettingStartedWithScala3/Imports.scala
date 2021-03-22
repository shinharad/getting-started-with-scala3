package com.github.shinharad.gettingStartedWithScala3
package imports

//---
// Wildcard Imports

// Scala3 の import 文のワイルドカードは、`*` になった
// （今までのサンプルコードでも出てきたし、今更感がありますが...）
import scala.util.chaining.*

// 一応、`_` もまだ使えるけど、将来のバージョンで廃止される予定
import scala.util.chaining._

//---
// `*` というメソッドを import するには？

object A:
  def * = "*"
  def min = "min"

object B:
  import A.`*` // A のメソッド `*` をimport

  def show = *

  // def minimum = min // `min` は import してないのでこれはコンパイルエラー

object C:
  import A.* // A のメンバをすべて import

  def show = * + min

@main def no1(): Unit =
  println("-" * 50)

  B.show.tap(println)
  C.show.tap(println)

  println("-" * 50)

//---
// Renaming Imports

// import のリネームは、`=>` から `as` に変わった
@main def no2(): Unit =
  println("-" * 50)

  // 単一のリネームであれば `{ .. }` は不要
  import A.min as minimum1
  import A.{ min => minimum2 }  // Scala2 ではこうだった

  // 両方使える
  minimum1.tap(println)
  minimum2.tap(println)

  // `{ .. }` が不要なので簡潔に書ける
  import scala.annotation as ann
  import java as j

  val list = j.util.ArrayList[String]

  println("-" * 10)

  // import するものが複数の場合はこう
  import A.{ min as minimum, `*` as multiply }

  minimum.tap(println)
  multiply.tap(println)

  println("-" * 50)

// import するものを除外したい場合
def no3(): Unit =
  import Predef.{ augmentString as _, * } // augmentString 以外のすべてを import する

  // StringOps に変換されないのでコンパイルエラー
  // val a = "-" * 50 // value * is not a member of String
