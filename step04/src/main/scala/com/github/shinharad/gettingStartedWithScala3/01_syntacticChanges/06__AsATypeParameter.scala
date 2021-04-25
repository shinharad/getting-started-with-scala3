package com.github.shinharad.gettingStartedWithScala3
package `01_syntacticChange`
package `06__AsATypeParameter`

trait Foo[T]

// Scala 2 では、これはコンパイルが通る
// def foo[_: Foo]: Unit = ???

// これは、fooというメソッドの型パラメータ `_` に Context Bound として `trait Foo[_]` を指定したものだが、
// このような書き方は、Scala コンパイラのバグを巧みに利用したもので、想定外な使われ方だったため、Scala 3 では廃止された
// 
// 参考)
// https://www.reddit.com/r/scala/comments/fczcvo/mysterious_context_bounds_in_fastparse_2/fjecokn/

// Scala 3 からは型パラメータを明示する
def foo[T: Foo]: Unit = ???
