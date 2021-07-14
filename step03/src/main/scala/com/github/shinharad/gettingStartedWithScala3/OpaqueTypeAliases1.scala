package com.github.shinharad.gettingStartedWithScala3
package opaqueTypeAliases1

import scala.util.chaining.*

//---
// 実装例その1

// - 以下は、Logarithm（対数）を抽象化したもので、実体は Double
// - Double であるという事実は、MyMath のスコープ内でのみ知られている
// - スコープ内では Logarithm は型エイリアスとして扱われるが、
//   スコープ外からは opaque（不透明）なので、Logarithm は抽象型として見られ、
//   実体が Double であることを認識することはできない
object MyMath:

  // 必ず何らかのスコープ内で定義する
  opaque type Logarithm = Double

  object Logarithm:

    def apply(d: Double): Logarithm = math.log(d)
  
    def safe(d: Double): Option[Logarithm] =
      if d > 0.0 then Some(math.log(d)) else None

  end Logarithm

  extension (x: Logarithm)
    def toDouble: Double = math.exp(x)
    def + (y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
    def * (y: Logarithm): Logarithm = x + y

end MyMath

@main def no1(): Unit =
  import MyMath.Logarithm

  println("-" * 50)

  // Logarithm の生成は、コンパニオンオブジェクトで定義された
  // apply か safe メソッドからのみ生成ができる
  val l = Logarithm(1.0)
  val l2 = Logarithm(2.0)
  val l3 = Logarithm.safe(3.0) // Option[Logarithm]

  // Logarithm は、Extension methods で公開したメソッドからのみアクセスが可能
  val l4 = l * l2
  val l5 = l + l2

  println(s"l: $l")
  println(s"l2: $l2")
  println(s"l3: $l3")
  println(s"l4: $l4")
  println(s"l5: $l5")

  //---
  // 以下はコンパイルエラー

  // Double のメソッドは外部から呼び出せない
  // l.floatValue

  // Logarithm は、Double 型の変数に設定できない
  // val d: Double = l // error: found: Logarithm, required: Double

  // Double 型で初期化はできない
  // val l6: Logarithm = 1.0 // error: found: Double, required: Logarithm

  // Double 型で計算はできない
  // l * 2 // error: found: Int(2), required: Logarithm

  // `/` というメソッドは Logarithm で定義されていないので使えない
  // l / l2 // error: `/` is not a member of Logarithm

  println("-" * 50)

//---
// Bounds For Opaque Type Aliases
// 実装例その2

object Access:

  // Permission: 1つのパーミッションを表す
  // Permissions: Permission のセットを表す
  // PermissionChoice: 「Permissions の内、少なくとも1つ」という意味で、Permission のセットを表す

  // - 3つの opaque type aliases はすべて同じ基礎表現の Int で定義されている
  // - Permission 型には、上限境界として Permissions & PermissionChoice が指定されている
  //   これにより、Permission が他の2つの型のサブタイプであることが
  //   Access オブジェクトの外部に知られるようになる

  opaque type Permissions = Int
  opaque type PermissionChoice = Int
  opaque type Permission <: Permissions & PermissionChoice = Int // `&` は Intersection Types

  // Accessオブジェクトの外側では、
  // - Permission 型の値は `&` 演算子を使用して結合することができる
  // - PermissionChoice 型の値は、`|` 演算子を使用して結合することができる

  // スコープの内側では、Permissions は Int の型エイリアスなので、
  // メソッド本体の `|` は、Int に対する論理演算子となる。（Union Types ではない）
  // つまり、Extension methods の `|` が呼ばれるわけではないので、無限の再帰を引き起こすことはない
  extension (x: Permissions)
    def & (y: Permissions): Permissions = x | y // `x | y` の `|` は論理演算子
  extension (x: PermissionChoice)
    def | (y: PermissionChoice): PermissionChoice = x | y // `x | y` の `|` は論理演算子
  extension (granted: Permissions)
    def is(required: Permissions) = (granted & required) == required
  extension (granted: Permissions)
    def isOneOf(required: PermissionChoice) = (granted & required) != 0

  val NoPermission: Permission = 0
  val Read: Permission = 1
  val Write: Permission = 2
  val ReadWrite: Permissions = Read | Write 
  val ReadOrWrite: PermissionChoice = Read | Write

end Access

@main def no2(): Unit =
  println("-" * 50)

  import Access._

  case class Item(rights: Permissions)

  val roItem = Item(Read)
  val rwItem = Item(ReadWrite)
  val noItem = Item(NoPermission)

  roItem.rights.is(ReadWrite)
    .tap(r => assert(!r))

  roItem.rights.isOneOf(ReadOrWrite)
    .tap(assert(_))

  rwItem.rights.is(ReadWrite)
    .tap(assert(_))

  rwItem.rights.isOneOf(ReadOrWrite)
    .tap(assert(_))

  noItem.rights.is(ReadWrite)
    .tap(r => assert(!r))

  noItem.rights.isOneOf(ReadOrWrite)
    .tap(r => assert(!r))

  // 一方で、Permission と PermissionChoice は Access の外では無関係な別の型なので、
  // これはコンパイルエラーになる
  // roItem.rights.isOneOf(ReadWrite)

  println("-" * 50)

//---
// Opaque Type Members on Classes
//
// Opaque Type Aliases は、class と一緒に使用することもできる

class MyMathClass:

   opaque type Logarithm = Double

   def apply(d: Double): Logarithm = math.log(d)

   def safe(d: Double): Option[Logarithm] =
      if d > 0.0 then Some(math.log(d)) else None

   def mul(x: Logarithm, y: Logarithm) = x + y

@main def no3(): Unit =
  println("-" * 50)

  // 異なるインスタンスの Opaque Type のメンバーは異なるものとして扱われる

  val l1 = new MyMathClass
  val l2 = new MyMathClass
  val x = l1(1.5)
  val y = l1(2.6)
  val z = l2(3.1)
  l1.mul(x, y) // type checks
  // l1.mul(x, z) // error: found l2.Logarithm, required l1.Logarithm

  println("-" * 50)

//---
// Opaque Type Aliase に case class を指定すると、copyメソッドを封じることができる

object CaseClassOpaque:

  final case class InternalPerson(name: String)

  opaque type Person = InternalPerson
  object Person:
    def apply(name: String): Person = InternalPerson(name)

  extension (p: Person)
    def withName(name: String): Person = p.copy(name = name)

@main def no4(): Unit =
  println("-" * 50)

  import CaseClassOpaque.Person

  val p = Person("hoge")
    .tap(println)

  val p2 = p.withName("aaaa")
    .tap(println)

  // copy は公開されていないのでコンパイルエラーになる
  // p.copy(name = "xxxx")

  println("-" * 50)

//---
// ちなみにトップレベルで定義してしまうと、実体がダダ漏れなので注意

opaque type Logarithm2 = Double

object Logarithm2:
  def apply(d: Double): Logarithm2 = math.log(d)

extension (x: Logarithm2)
  def + (y: Logarithm2): Logarithm2 = Logarithm2(math.exp(x) + math.exp(y))

@main def no5(): Unit = 
  println("-" * 50)

  val l = Logarithm2(1.0)

  // スコープの中なので、Extension Methods だって生やせちゃう
  extension (x: Logarithm2)
    def toDouble: Double = x
  
  l.toDouble

  println("-" * 50)

//---
// Value Classes も一応使える 

class ValueClasses(value: String) extends AnyVal
