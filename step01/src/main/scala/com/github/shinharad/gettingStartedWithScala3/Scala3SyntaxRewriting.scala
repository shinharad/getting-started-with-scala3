package com.github.shinharad
package gettingStartedWithScala3
package scala3SyntaxRewriting

// scalacOptions を変更すると、
// コンパイラがそれぞれのシンタックスに書き換えてくれます

object Counter {
  enum Protocol {
    case Reset
    case MoveBy(step: Int)
  }
}

case class Animal(name: String)

trait Incrementer {
  def increment(n: Int): Int
}

case class State(n: Int, minValue: Int, maxValue: Int) {
  def inc: State =
    if (n == maxValue)
      this
    else
      this.copy(n = n + 1)
  def printAll: Unit = {
    println("Printing all")
    for {
      i <- minValue to maxValue
      j <- 0 to n
    } println(i + j)
  }
}