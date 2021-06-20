package com.github.shinharad.gettingStartedWithScala3
package unionTypes2

import com.github.shinharad.gettingStartedWithScala3.unionTypes2.UsernameOrPassword

//---
// Scala 3 Book より
// https://docs.scala-lang.org/scala3/book/types-union.html

trait Hash

// Alternative to Union Types
//
// Union Types は、複数の異なる型の代替品を表現するために使用することができる
// https://docs.scala-lang.org/scala3/book/types-union.html

//---
// Pre-Planning the Class Hierarchy
// 今までは、次のようにクラス階層を事前に決める必要があった
trait UsernameOrPassword
case class Username(name: String) extends UsernameOrPassword
case class Password(hash: Hash) extends UsernameOrPassword

def help(id: UsernameOrPassword) = id match
  case Username(name) => ???
  case Password(hash) => ???

// 例えば、APIのクライアントの要求は予測できないかもしれないので、事前に計画を立ててもあまりうまくいかない。
// また、UsernameOrPassword のようなマーカートレイトで型階層を乱雑にすると、コードが読みにくくなる。

//---
// Tagged Unions
// もう一つの方法は、列挙型で分離して定義すること
enum UsernameOrPassword2:
  case IsUsername(u: Username)
  case IsPassword(p: Password)

def help2(id: UsernameOrPassword2) = id match
  case UsernameOrPassword2.IsUsername(u) => u.name
  case UsernameOrPassword2.IsPassword(h) => h.hash

// 列挙型の UsernameOrPassword2 は、Username と Password の tagged union を表している。
// しかし、このような方法で Union をモデル化するには、明示的なラッピングとアンラッピングが必要であり、
// たとえば、Username は UsernameOrPassword2 のサブタイプではない。

//---
// Union Types
// そこで、Union Types で表現する

def help3(id: Username | Password) = id match
  case Username(name) => ???
  case Password(hash) => ???
