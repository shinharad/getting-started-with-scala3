package com.github.shinharad.gettingStartedWithScala3
package droppedPackageObjects

// 現時点でまだ Package Objects は使えるが、今後非推奨となり削除される予定
package object p:
  val a = ???
  def b = ???

//---
// あらゆる種類の定義がトップレベルで書けるようになったので、Package Objects は不要になった
type Labelled[T] = (String, T)
val a: Labelled[Int] = ("count", 1)
def b = a._2

case class C()

extension (x: C) def pair(y: C) = (x, y)

//---
package p2:
  case class D()
  extension (x: D) def pair(y: D) = (x, y)

package p3:
  import p2.*
  def droppedPackageObjects: (D, D) =
    D().pair(D())
