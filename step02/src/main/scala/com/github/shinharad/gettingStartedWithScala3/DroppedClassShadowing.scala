package com.github.shinharad.gettingStartedWithScala3
package droppedClassShadowing

//----
// Scala3 では、親クラスで定義されているクラスと同じ名前のクラスを子クラスで定義できなくなった
// 以下は、Scala2 ではコンパイルが通るが、Scala3 ではコンパイルエラーになる

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