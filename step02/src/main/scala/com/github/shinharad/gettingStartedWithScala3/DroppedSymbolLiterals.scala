package com.github.shinharad.gettingStartedWithScala3
package droppedSymbolLiterals

// シンボルリテラルは既に削除されているので、コンパイルエラーになる
// val no1 = 'xyz

// scala.Symbol は将来的に非推奨となり削除される予定
val no2 = Symbol("xyz")

// 代わりにプレーンな String を使用する
val no3 = "xyz"
