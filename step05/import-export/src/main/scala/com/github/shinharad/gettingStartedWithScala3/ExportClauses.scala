package com.github.shinharad.gettingStartedWithScala3
package exportClauses

import scala.util.chaining.*

class BitMap
class InkJet

class Printer:
  type PrinterType
  def print(bits: BitMap): Unit =
    println(s"print: - $bits")
  def status: List[String] =
    List("printer status")

class Scanner:
  def scan(): BitMap =
    new BitMap
  def status: List[String] =
    List(s"scanner status")

//---
// export 句を使うと、オブジェクトのメンバのエイリアス（export aliases）を定義できる

@main def no1(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    export scanUnit.scan
    export printUnit.print

    // 上記はこのような export aliases を生成する
    // final def scan(): BitMap            = scanUnit.scan()
    // final def print(bits: BitMap): Unit = printUnit.status

  val copier = new Copier
  copier.print(copier.scan()) // export aliasesでアクセスする

//---
// export 句は、trait や object でも定義できる

@main def no2(): Unit =

  trait Copier1:
    private val scanUnit = new Scanner
    export scanUnit.scan

  object Copier2:
    private val scanUnit = new Scanner
    export scanUnit.scan

//---
// export 句のワイルドカード指定

@main def no3(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    export scanUnit.*
    export printUnit.print

    // ドキュメントの通りに書くとワイルドカードは `_` だけど、Imports と合わせるなら `*` だと思う
    // export scanUnit._

    // 上記はこのような export aliases を生成する
    // final def scan(): BitMap            = scanUnit.scan()
    // final def status: List[String]      = scanUnit.status
    // final def print(bits: BitMap): Unit = printUnit.print(bits)

  val copier = new Copier
  copier.print(copier.scan()) // export aliasesでアクセスする

//---
// `as` でリネームできる

@main def no4(): Unit =

  class Copier:
    private val scanUnit = new Scanner

    export scanUnit.scan as sc

    // 上記はこのような export aliases を生成する
    // final def sc(): BitMap            = scanUnit.scan()

  val copier = new Copier
  copier.sc() // リネームしたメソッドを呼び出す

//---
// export 句で特定のメンバを除外する

@main def no5(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    export scanUnit.scan
    export printUnit.status as _ // status を除外（敢えてこう書く必要は無いけど分かりやすさのため）
    export printUnit.print

    // ドキュメントの通りに書くとこうだけど、Imports のリネームが as なので
    // export printUnit.{ status => _ }

    // 上記はこのような export aliases を生成する
    // final def scan(): BitMap            = scanUnit.scan()
    // final def print(bits: BitMap): Unit = printUnit.print(bits)

  val copier = new Copier

  // これはアクセスできるけど
  copier.print(copier.scan())

  // status は export してないのでアクセスできない
  // copier.status

//---
// 特定の定義以外を export する場合

@main def no6(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    export scanUnit.scan
    export printUnit.{ status as _, * } // status 以外をexport

    // ドキュメントではこう書いてあるけど、Importsと合わせるなら上の書き方だと思う
    // export printUnit.{ status => _, _ }

    // 上記はこのような export aliases を生成する
    // final def scan(): BitMap            = scanUnit.scan()
    // final def print(bits: BitMap): Unit = printUnit.print(bits)
    // final type PrinterType              = printUnit.PrinterType

  val copier = new Copier

  // これはアクセスできるけど
  copier.print(copier.scan())

  // status は export してないのでアクセスできない
  // copier.status

//---
// export 句によるエイリアスと既存のメンバとの定義が重複する場合はコンパイルエラー

def no7(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    // status は既存メンバの定義と重複するのでコンパイルエラー
    // export printUnit.status 

    // この場合は as でリネームする
    export printUnit.status as st

    def status: List[String] = printUnit.status ++ scanUnit.status

  val copier = new Copier

  copier.st
  copier.status

//---
// overload はOK

def no8(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }

    export printUnit.print

    // 上記はこのような export aliases を生成する
    // final def print(bits: BitMap): Unit = printUnit.print(bits)

    final def print(inkJet: InkJet): Unit = ??? // overload

//---
// override はNG
// (export 句は final def のエイリアスを作成するため)

def no9(): Unit =

  class Copier:
    private val printUnit = new Printer { type PrinterType = InkJet }

    export printUnit.print

    // 上記はこのようなエイリアスを生成する
    // final def print(bits: BitMap): Unit = printUnit.print(bits)

  class Child extends Copier:
    type Dummy = Int

    // override はできない
    // override def print(bits: BitMap): Unit = ???
