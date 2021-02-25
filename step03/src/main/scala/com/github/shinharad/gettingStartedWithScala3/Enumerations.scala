package com.github.shinharad.gettingStartedWithScala3
package enumerations

import scala.util.Try
import scala.util.chaining.*

// Scala2 まではこんな感じで定義していたけど、正直使いづらかった
object ColorOld extends Enumeration {
  val Red, Green, Blue = Value
}

//---
// Scala3 の列挙型

// 上記のコードは、Scala3 ではこのように書けるようになった
enum Color:
  case Red, Green, Blue

// パラメータを持つこともできる
enum ColorWithParameter(val rgb: Int):
  case Red   extends ColorWithParameter(0xFF0000)
  case Green extends ColorWithParameter(0x00FF00)
  case Blue  extends ColorWithParameter(0x0000FF)

@main def no1(): Unit =
  println("-" * 50)

  val red: Color = Color.Red
    .tap(println)

  println("-" * 50)

//---
// 列挙した型は一意の Int に対応していて、ordinal で取得ができる
@main def no2(): Unit =
  println("-" * 50)

  Color.Red.ordinal
    .tap(println) // 0
  Color.Green.ordinal
    .tap(println) // 1
  Color.Blue.ordinal
    .tap(println) // 2

  println("-" * 50)

//---
// コンパニオンオブジェクトのユーティリティメソッド
@main def no3(): Unit =
  println("-" * 50)

  println("valueOf:")
  val blue: Color = Color.valueOf("Blue")
    .tap(println)

  // 未定義の値を渡した場合は、java.lang.IllegalArgumentException
  Try(Color.valueOf("Yellow")).tap(println)
  println("-" * 20)

  println("values:")
  val values: Array[Color] = Color.values
    .tap(_.foreach(println))
  println("-" * 20)

  println("fromOrdinal:")
  val color: Color = Color.fromOrdinal(0)
    .tap(println)

  // 未定義の値を渡した場合は、java.util.NoSuchElementException
  Try(Color.fromOrdinal(4)).tap(println)

  println("-" * 50)

//---
// Enums には独自のメソッドを定義できる

@main def no4(): Unit =
  enum Planet(mass: Double, radius: Double):
    private final val G = 6.67300E-11
    def surfaceGravity = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

    case Mercury extends Planet(3.303e+23, 2.4397e6)
    case Venus   extends Planet(4.869e+24, 6.0518e6)
    case Earth   extends Planet(5.976e+24, 6.37814e6)
    case Mars    extends Planet(6.421e+23, 3.3972e6)
    case Jupiter extends Planet(1.9e+27,   7.1492e7)
    case Saturn  extends Planet(5.688e+26, 6.0268e7)
    case Uranus  extends Planet(8.686e+25, 2.5559e7)
    case Neptune extends Planet(1.024e+26, 2.4746e7)

  object Planet:
    def reportWeight(earthWeight: Double): Unit =
      val mass = earthWeight / Earth.surfaceGravity
      for p <- values do
        println(s"Your weight on $p is ${p.surfaceWeight(mass)}")

  // 呼び出す
  Planet.reportWeight(60)

//---
// Enums の一部を deprecated にした場合、
// スコープの外で使用すると警告メッセージを表示するが、
// スコープの中では警告メッセージを表示しない
// （deprecated にしてから削除するまでの移行期間として便利）

@main def no5(): Unit =
  enum Planet(mass: Double, radius: Double):
    private final val G = 6.67300E-11
    def surfaceGravity = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

    case Mercury extends Planet(3.303e+23, 2.4397e6)
    case Venus   extends Planet(4.869e+24, 6.0518e6)
    case Earth   extends Planet(5.976e+24, 6.37814e6)
    case Mars    extends Planet(6.421e+23, 3.3972e6)
    case Jupiter extends Planet(1.9e+27,   7.1492e7)
    case Saturn  extends Planet(5.688e+26, 6.0268e7)
    case Uranus  extends Planet(8.686e+25, 2.5559e7)
    case Neptune extends Planet(1.024e+26, 2.4746e7)

    @deprecated("refer to IAU definition of planet")
    case Pluto   extends Planet(1.309e+22, 1.1883e3)
  
  trait Deprecations[T <: reflect.Enum]:
    extension (t: T) def isDeprecatedCase: Boolean

  object Planet:
    // これは型クラスのインスタンスの定義だけど、今後取り上げるので現段階ではさらっと見る程度で
    given Deprecations[Planet] with
      extension (p: Planet)
        // deprecated な Pluto はスコープ外では警告メッセージを表示するが、
        // スコープの中では警告メッセージを表示しない
        def isDeprecatedCase = p == Pluto

  // スコープの外では警告メッセージを表示する
  // Planet.Pluto.isDeprecatedCase
  //   .tap(x => println(s"Pluto: $x"))

//---
// Scala で定義した Enums は、 `java.lang.Enum` を継承することで、
// Java の Enums として使用することができる

enum JColor extends java.lang.Enum[JColor] { case Red, Green, Blue }

@main def no6(): Unit =
  println("-" * 50)

  JColor.Red.compareTo(JColor.Green)
    .tap(println)
    .pipe(r => assert(r == -1))

  println("-" * 50)

//---
// Enums は継承できない
// enum Child extends Color:
//   case Yellow
