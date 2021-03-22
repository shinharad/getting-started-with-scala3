package com.github.shinharad.gettingStartedWithScala3
package exportClausesPackageObject

//---
// パッケージオブジェクトからトップレベル定義への移行によってできたギャップを埋める

// 移行前
// Facade クラスを継承したパッケージオブジェクトを使用
def no1(): Unit =
  import com.github.shinharad.gettingStartedWithScala3.before._

  foo()

// 移行後
// Facade クラスを export でトップレベルで定義したものを使用
def no2(): Unit =
  import com.github.shinharad.gettingStartedWithScala3.after._

  foo()

