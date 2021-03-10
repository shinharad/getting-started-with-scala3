package com.github.shinharad.gettingStartedWithScala3
package `02_droppedFeatures`
package `02_DoWhileConstruct`

//---
// scalacOptions の以下を順番に有効にしてみてください
// - "-source:3.0-migration"
// - "-source:3.0-migration", "-rewrite"

def no1(): Unit = {
  def f(i: Int): Int = ???
  var i = 1

  // do {
  //   i += 1
  // } while (f(i) == 0)
}
