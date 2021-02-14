package com.github.shinharad.gettingStartedWithScala3
package varargSplices

/*
M3 ではまだコンパイルエラー
こちらがリリースされればコンパイルが通るはず
https://github.com/lampepfl/dotty/pull/11240
*/

// def varargSplices1_ng =
//   val arr = Array(1, 2, 3)
//   val lst = List(0, arr*)                 // vararg splice argument
//   lst match
//     case List(0, 1, xs*) => println(xs)   // binds xs to Seq(2, 3)
//     case List(1, _*) =>                   // wildcard pattern

// 今までの書き方
def varargSplices1 =
  val arr = Array(1, 2, 3)
  val lst = List(0, arr: _*)

  lst match
    case List(0, 1, xs: _*) => println(xs)   // binds xs to Seq(2, 3)
    case List(1, _*) =>                      // wildcard pattern
    case _ =>
