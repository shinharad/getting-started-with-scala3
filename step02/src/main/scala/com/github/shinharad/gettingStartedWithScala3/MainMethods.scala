package com.github.shinharad.gettingStartedWithScala3
package mainMethods

import scala.util.chaining._

/*
これで実行する

> runMain com.github.shinharad.gettingStartedWithScala3.mainMethods.happyBirthday 23 Lisa Peter

引数が足りないとエラーになる

> runMain com.github.shinharad.gettingStartedWithScala3.mainMethods.happyBirthday 22

引数の型が違う場合もエラーになる

> runMain com.github.shinharad.gettingStartedWithScala3.mainMethods.happyBirthday sixty Fred
*/

@main def happyBirthday(age: Int, name: String, others: String*) =
  println("-" * 50)

  val suffix =
    age % 100 match
    case 11 | 12 | 13 => "th"
    case _ =>
      age % 10 match
        case 1 => "st"
        case 2 => "nd"
        case 3 => "rd"
        case _ => "th"
  val bldr = new StringBuilder(s"Happy $age$suffix birthday, $name")
  for other <- others do bldr.append(" and ").append(other)
  bldr.toString.tap(println)

  println("-" * 50)

/*
上記のコードは、コンパイラがこんなイメージのコードを作成するらしい

final class happyBirthday:
  import scala.util.CommandLineParser as CLP
  <static> def main(args: Array[String]): Unit =
    try
      happyBirthday(
        CLP.parseArgument[Int](args, 0),
        CLP.parseArgument[String](args, 1),
        CLP.parseRemainingArguments[String](args, 2))
    catch
      case error: CLP.ParseError => CLP.showError(error)
*/

//---
// object の中で書くこともできる

object HappyBirth:
  @main def happyBirthday2(age: Int, name: String, others: String*) =
    happyBirthday(age, name, others: _*)

//---
// Scala2 系みたいに `App` を `extends` して書くこともできるが、 Scala3 的には `@main` を推奨している

object happyBirthday3 extends App:
  println("hello")
