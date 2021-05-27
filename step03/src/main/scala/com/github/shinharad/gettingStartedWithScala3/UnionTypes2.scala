package com.github.shinharad.gettingStartedWithScala3
package unionTypes2

import com.github.shinharad.gettingStartedWithScala3.unionTypes2.UsernameOrPassword

//---
// Scala 3 Book より
// https://docs.scala-lang.org/scala3/book/types-union.html

trait Hash

// Alternative to Union Types
// Union Types は、複数の異なる型の代替品を表現するために使用することができる

//---
// PRE-PLANNING THE CLASS HIERARCHY
// 今までは、次のようにクラス階層を事前に決める必要があった
trait UsernameOrPassword
case class Username(name: String) extends UsernameOrPassword
case class Password(hash: Hash) extends UsernameOrPassword
def help(id: UsernameOrPassword) = ???

// 例えば、APIのクライアントの要求は予測できないかもしれないので、事前に計画を立ててもあまりうまくいかない。
// また、UsernameOrPassword のようなマーカートレイトで型階層を乱雑にすると、コードが読みにくくなる。

//---
// TAGGED UNIONS
// もう一つの方法は、次のように別の列挙型を定義すること
enum UsernameOrPassword2:
  case IsUsername(u: Username)
  case IsPassword(p: Password)

// 列挙型の UsernameOrPassword は、Username と Passwordの tagged union を表しています。
// しかし、このような方法で Union をモデル化するには、明示的なラッピングとアンラッピングが必要であり、
// たとえば、Username は UsernameOrPassword のサブタイプではありません。

//---
// Union Types
// そこで、Union Types で表現する
type UsernameOrPassword3 = Username | Password
