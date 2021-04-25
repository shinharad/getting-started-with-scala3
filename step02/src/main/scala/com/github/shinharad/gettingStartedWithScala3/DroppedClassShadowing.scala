package com.github.shinharad.gettingStartedWithScala3
package droppedClassShadowing

//----
// Scala 3 では、親クラスで定義されているクラスと同じ名前のクラスを子クラスで定義できなくなった
// 以下は、Scala 2 ではコンパイルが通るが、Scala 3 ではコンパイルエラーになる

class Base {
  class Ops { }
}

class Sub extends Base {
  // これはコンパイルエラーになる
  // class Ops { } 
}

//----
// 別の名前を付ける必要がある

class Sub2 extends Base {
  class OpsEx { } 
}