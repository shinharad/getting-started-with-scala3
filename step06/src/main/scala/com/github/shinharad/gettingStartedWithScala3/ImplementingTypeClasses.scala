package com.github.shinharad.gettingStartedWithScala3
package implementingTypeClasses

// Show（Haskellではよく知られた型クラス）の実装例

//---
// The type class

// 型クラスを作成する最初のステップは、1つまたは複数の抽象メソッドを持つパラメータ化された trait を宣言すること
// Showable は show という名前のメソッドを1つだけ持っているので、このように書く。

// 型クラス
trait Showable[A]:
  extension(a: A) def show: String

// 単なる trait
trait Show:
  def show: String

// 重要なポイント
// - Showable のような型クラスは、どの型に対して show の実装を提供するかを示す型パラメータ A を取る
//   一方、Show のような通常の trait はそうではない
// - ある型 A に show の機能を追加するには、通常の trait では A が show を拡張していることが必要だが、型クラスでは Showable[A] の実装が必要になる
// - Showable と Show のメソッド呼び出し構文を同じにするために、 Showable.show を extension method として定義している

//---
// Implement concrete instances

// 次のステップでは、アプリケーション内のどのクラスに対して Showable が機能するかを決定し、
// そのクラスに対してそのビヘイビアを実装する
// 例えば、Person クラスに Showable を実装するには、次のようにする。

case class Person(firstName: String, lastName: String)

// Showable[Person] に given instance を定義する
given Showable[Person] with
  extension(p: Person) def show: String =
    s"${p.firstName} ${p.lastName}"

//---
// Using the type class

// もしScalaがすべてのクラスに toString メソッドを用意していなかったら、
// このテクニックを使って、Stringに変換できるようにしたい任意のクラスに Showable のビヘイビアを追加することができる。

@main def no1(): Unit =
  val person = Person("John", "Doe")
  println(person.show)

//---
// Writing methods that use the type class

// 継承と同様に、Showable を型パラメータとして使用するメソッドを定義することができる

def showAll[S: Showable](xs: List[S]): Unit =
  xs.foreach(x => println(x.show))

@main def no2(): Unit =
  showAll(List(Person("Jane", "xxx"), Person("Mary", "yyy")))

//---
// A type class with multiple methods

// 複数のメソッドを持つ型クラスを作成する場合、次のようになる

trait HasLegs[A]:
  extension (a: A)
    def walk(): Unit
    def run(): Unit
