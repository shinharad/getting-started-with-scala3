package com.github.shinharad.gettingStartedWithScala3
package droppedSymbolLiterals

// scala.Symbol は将来的に非推奨となり削除される予定
val no1 = Symbol("a")

// 代わりにプレーンな String を使用する
val no2 = "a"
