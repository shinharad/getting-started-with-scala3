package com.github.shinharad.gettingStartedWithScala3
package explicitNulls3

import com.github.shinharad.explicitnulls.JavaClass

import scala.util.chaining.*
import scala.util.{ Try, Success }

//---
// Java の unsafe なメソッド呼び出しを Try でラップして、
// Flow Typing を試してみる

@main def no1(): Unit =
  println("-" * 50)

  val javaClass = JavaClass()

  val a: Try[String | Null] = Try(javaClass.unsafeMethod())

  // match式のガード条件ではFlow Typingが発動しないっぽい
  val result1: Int = a match
    case Success(r) if r != null  =>
      val b: String | Null = r // non-null とはならない

      // 改めて Flow Typing する
      if b != null then
        b.length
      else
        0

    case _ =>
      0

  println(result1)

  // 代わりにこのようにすると良さそう
  val result2: Int = a match
    case Success(r: String) =>
      r.length

    case _ => 0

  println(result2)

  // 高階関数の中でFlow Typingを使ってみる
  val result3: Try[Int] = a.map { x =>
    if x != null then
      x.length
    else
      0
  }

  println(result3)
  
  println("-" * 50)
