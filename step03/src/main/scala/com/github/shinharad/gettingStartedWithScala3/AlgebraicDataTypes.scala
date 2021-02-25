package com.github.shinharad.gettingStartedWithScala3
package algebraicDataTypes

// Enums を使用して、Algebraic Data Types（ADT、代数的データ型）を表現できる

//---
// Option 型を Enums で表現する

enum Option[+T]:
  case Some(x: T)
  case None

//---
// enum の型パラメータの変位指定が covariant（共変）の場合は、
// コンパイラの型推論により、列挙した型の extends を省略できる

def no2: Unit =

  // 省略した場合
  enum Option[+T]:
    case Some(x: T)
    case None

  // 明示的に書いた場合
  enum OptionV2[+T]:
    case Some(x: T) extends OptionV2[T]
    case None       extends OptionV2[Nothing]

  // なお、型パラメータの無い None は、Option[Nothing] と推論されていることに注意
  // （Nothing はすべての型のサブタイプであり、ボトム型と呼ばれている）

//---
// covariant（共変）以外の場合は、すべて明示的に書く必要がある
// 省略するとコンパイルエラー

def no3: Unit =

  // contravariant (反変) 
  enum View[-T]:
    case Refl[R](f: R => R) extends View[R]

  // invariant (非変) 
  enum Option[T]:
    case Some(x: T) extends Option[T]
    case None       extends Option[Nothing]

//---
// Enums で列挙した型は、Enums のコンパニオンオブジェクトで定義される

def no4: Unit =

  // つまり、このように使える
  Option.Some("hello")
  Option.None

  // 列挙した型は内部的にクラスになるので、new で生成することも一応できる
  val o1: Option.Some[Int] = new Option.Some(2)

//---
// Enums と同様、ADT は独自のメソッドを定義することができる

def no5: Unit =
  enum Option[+T]:
    case Some(x: T)
    case None

    def isDefined: Boolean = this match
      case None => false
      case _    => true

  object Option:
    def apply[T >: Null](x: T): Option[T] =
      if x == null then None else Some(x)

  Option(123).isDefined

//---
// ADT は、Enums と混在させることができる

// 以下は、3つの Enums (Red, Green, Blue) を持つか、
// またはパラメータ化された case を持つ Color か
enum Color(val rgb: Int):
  case Red   extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue  extends Color(0x0000FF)
  case Mix(mix: Int) extends Color(mix)

//---
// ADT の case の型パラメータは、親である enum の変位指定を引き継ぐ

def no6_ng: Unit =

  // つまりこれはコンパイルエラー
  // enum View[-T]:
  //   case Refl(f: T => T) extends View[T]

  // - Refl の T は、View で指定している contravariant (反変) を引き継いでいる
  // - 関数 f の結果型には、contravariant (反変) を指定できない

  // 上記のコードは、コンパイラにはこのように見えている
  // enum View[-T]:
  //   case Refl[-T1](f: T1 => T1) extends View[T1]

  ()

// Refl の f を正しく型付けするには、Refl 自身で non-variant 型パラメータを宣言する必要がある
def no6_ok: Unit =
  enum View[-T]:
    case Refl[R](f: R => R) extends View[R]

// さらにいくつかの変更を加えた後、View のより完全な実装は以下のようになり、
// 関数型 T => U として使用することができる

def no6_ok2: Unit =
  enum View[-T, +U] extends (T => U):
    case Refl[R](f: R => R) extends View[R, R]

    final def apply(t: T): U = this match
      case refl: Refl[r] => refl.f(t)
  
  View.Refl((x: Int) => x + 1)
