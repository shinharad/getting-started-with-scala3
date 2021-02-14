package com.github.shinharad.gettingStartedWithScala3
package matchExpressions

//---
// マッチ式を連鎖させることができる
// brasesは省略可能
def no1 =
  val xs: List[Int] = ???

  xs match {
    case Nil => "empty"
    case x :: xs1 => "nonempty"
  } match {
    case "empty" => 0
    case "nonempty" => 1
  }

  xs match
    case Nil => "empty"
    case x :: xs1 => "nonempty"
  match
    case "empty" => 0
    case "nonempty" => 1

//---
// ピリオドの後に書くことができる
def no2 =
  val xs: List[Int] = ???

  if xs.match
    case Nil => false
    case _ => true
  then "nonempty"
  else "empty"

//---
// `x: T match { ... }` はサポートされなくなったので、`(x: T) match { ... }` と書く
def no3 =
  val x: Int = ???

  // NG
  // x: Int match { case 0 => 1 }
  // x: Int match { case 0 => 1 }

  (x: Int) match { case 0 => 1 }
  (x: Int) match { case 0 => 1 }
