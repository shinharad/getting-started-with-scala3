package com.github.shinharad.gettingStartedWithScala3
package varargSplices

@main def no1(): Unit =
  val arr = Array(0, 1, 2, 3)
  val lst = List(arr*)                    // vararg splice argument
  lst match
    case List(0, 1, xs*) => println(xs)   // binds xs to Seq(2, 3)
    case List(1, _*) =>                   // wildcard pattern
    case _ =>

// 今までの書き方は段階的に廃止される予定
@main def no2(): Unit =
  val arr = Array(0, 1, 2, 3)
  val lst = List(arr: _*)

  lst match
    case List(0, 1, xs @ _*) => println(xs)   // binds xs to Seq(2, 3)
    case _ =>
