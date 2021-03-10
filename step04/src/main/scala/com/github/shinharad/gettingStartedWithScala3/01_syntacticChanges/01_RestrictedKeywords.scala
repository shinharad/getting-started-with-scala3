package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `01_restrictedKeywords`

// Scala 3.0 Migration mode の動作確認をします
// 以下のコメントアウトを外して、
// build.sbt の step04 の scalacOptions の設定を変えてみてください

//---
// scalacOptions の以下を順番に有効にしてみてください
// - "-explain"
// - "-source:3.0-migration"
// - "-source:3.0-migration", "-rewrite"

// object given {
//   val enum = ???
//   println(enum)
// }

//---
// scalacOptions の以下を有効にしてみてください
// - "-explain"
// （これに関してはマイグレーションモードが効かないらしい）

// def then(): Unit = ()

object Dummy
