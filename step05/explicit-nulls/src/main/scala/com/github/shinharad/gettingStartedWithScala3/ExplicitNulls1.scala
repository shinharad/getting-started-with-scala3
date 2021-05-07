package com.github.shinharad.gettingStartedWithScala3
package explicitNulls1

import scala.util.chaining.*
import com.github.shinharad.explicitnulls.JavaClass

//---
// Explicit Nulls (-Yexplicit-nulls) を有効にすると、Scala の型システムが変更され、
// Null は Any のサブタイプとなる。そのため、参照型（AnyRef のサブタイプ）に null を設定できなくなる

def no1(): Unit =
  // これはコンパイルエラー
  // val x: String = null // error: found `Null`, but required `String`

  // 代わりに Union Types を使用して nullable であることを明示する必要がある
  val x: String | Null = null // ok

  // nullable 型は、null チェックをせずにメンバーを使用することはできない
  // これはコンパイルエラー
  // x.trim // error: trim is not member of String | Null

//---
// Working with Null

// `.nn`（cast away = 捨て去る）という extension method が提供されている
// これは、nullable な値を簡単に扱えるようにするためのもの

@main def no2(): Unit = 
  val x: String | Null = " abc "
  x.nn.trim
    .tap(r => println(s"[$r]"))

// null の場合は実行時エラー
@main def no2_error(): Unit =
  val x: String | Null = null
  x.nn // java.lang.NullPointerException: tried to cast away nullability, but value is null

// その他、language feature の `unsafeNulls` を使用すると、
// `T | Null` を強制的に `T` として扱える。（詳細は後述）

//---
// Unsoundness

// String のような nullable ではない型であっても、クラス内の初期化されてないフィールドは、null になる場合がある
// このような場合、コンパイラオプションの `-Ysafe-init` を有効にすることで、コンパイラが補足してくれるようになる

def no3(): Unit =
  // 以下のコメントアウトを外すと警告メッセージが表示される
  // class C:
  //   val f: String = foo(f) // Access non-initialized field f. Calling trace
  //   def foo(f2: String): String = f2

  // val c = new C()
  // c.f == "field is null"
  ()

//---
// Equality

// null は、Null、nullable union (T | Null)、または Any としか比較できない
def no4(): Unit =
  val x: String = ???
  val y: String | Null = ???

  // 参照型は null と比較できないので、これらはコンパイルエラー
  // x == null       // error: Values of types String and Null cannot be compared with == or !=
  // x eq null       // error
  // "hello" == null // error

  // nullable は null と比較できる
  y == null       // ok
  y == x          // ok

  // どうしても比較したい場合は、型ヒント（例 : Any）を付ける
  (x: String | Null) == null  // ok
  (x: Any) == null            // ok

//---
// Java Interoperability

// Scala コンパイラは、ソースコードとバイトコードの両方から Java クラスをロードすることができる
// どちらの場合もコンパイラは、Java クラスのメンバー型が暗黙的に nullable であることを反映してパッチを当てる

// 値型はそのままだが、参照型は nullable となる
def no5_1(): Unit =

  // このような Java クラスをロードすると、
  /*
  class C {
    String s;
    int x;
  }
  */

  // コンパイラはこのようにパッチを当てる
  class C:
    val s: String | Null = ???
    val x: Int = ???

// Java の型パラメータは常に nullable になる
def no5_2(): Unit =

  // このような Java クラスをロードすると、
  // class C<T> { T foo() { return null; } }

  // コンパイラはこのようにパッチを当てる
  class C[T] { def foo(): T | Null = ??? }

  // 例えば、`C[Boolean]` としたとき、
  val c: C[Boolean] = ???

  // 型パラメータは Boolean だけど、foo の結果型は nullable となるので、これは間違い
  // val b: Boolean = c.foo() 

  // 正しくはこう
  val b: Boolean | Null = c.foo()

// Java クラスの場合、冗長な nullable を削減してくれる
def no5_3(): Unit =

  // このような Java クラスをロードすると、
  /*
  class Box<T> { T get(); }
  class BoxFactory<T> { Box<T> makeBox(); }
  */

  // コンパイラはこのようにパッチを当てる
  class Box[T] { def get(): T | Null = ??? }
  class BoxFactory[T] { def makeBox(): Box[T] | Null = ??? }

  // 例えば、`BoxFactory[String]` を生成して、
  val factory = BoxFactory[String]

  // makeBox() を呼び出す場合、
  // Java の型パラメータが nullable であることを考えると、
  // 結果型は `Box[String | Null] | Null` になりそうだけど、そうはならない。

  // こうではなく、
  // val a1: Box[String | Null] | Null = factory.makeBox()

  // 実際には `Box[String] | Null` になる
  val a2: Box[String] | Null = factory.makeBox()

  // 更に `Box[String]` に対して get() を呼び出すと `String | Null` を返すので、
  // Java の型パラメータが nullable であることの安全性がここで保証されている
  val b: String | Null = a2.nn.get()

// 型パラメータを持つクラスを Scala で定義している場合は、nullable を明示する
def no5_4(): Unit =

  // 型パラメータを持つクラスを Scala で定義している
  class Box[T] { def get(): T = ??? }

  // それを Java クラスのメンバとして定義していた場合
  /*
  class BoxFactory<T> {
    Box<T> makeBox(); // Box is Scala-defined
    List<Box<List<T>>> makeCrazyBoxes(); // List is Java-defined
  }
  */

  // コンパイラはこのようにパッチを当てる
  class BoxFactory[T]:
    // `Box<T>` は Scala で定義してるので、`Box[T | Null] | Null` となる
    // 前述した冗長な nullable の削減は、Java クラスにしか適用されないため、
    // nullable であることを明示する必要がある
    def makeBox(): Box[T | Null] | Null = ???

    // 1. `List<T>` は、Java クラスなので nullable にはならない
    //    => `java.util.List[T]` 
    // 2. `Box<List<T>>` は、Box が Scala クラスなので nullable を明示する
    //    => `Box[List[T] | Null]`
    // 3. `List<Box<List<T>>>` は、内部で Scala クラスの Box があるので、nullable を明示する
    //    => `List[Box[List[T] | Null]] | Null`
    def makeCrazyBoxes(): java.util.List[Box[java.util.List[T] | Null]] | Null = ???

// 単純なリテラルの定数フィールドは、non-null であることが自明なので nullable にはならない
def no5_5(): Unit =

  // このような Java クラスをロードすると、
  /*
  class Constants {
    final String NAME = "name";
    final int AGE = 0;
    final char CHAR = 'a';

    final String NAME_GENERATED = getNewName();

    String getNewName() { ... }
  }
  */

  // コンパイラはこのようにパッチを当てる
  class Constants:

    // リテラルの定数フィールドは nullable にはならない
    val NAME: String = "name"
    val AGE: Int = 0
    val CHAR: Char = 'a'

    // それ以外は nullable になる
    val NAME_GENERATED: String | Null = getNewName()

    def getNewName(): String | Null = ???

// @NotNull が付与されているメンバは nullable にならない
// 
// @NotNull 以外にもここで定義されているアノテーションも対象となる
// https://github.com/lampepfl/dotty/blob/75f7cb34d1af23cb169b661be7cfc14f5c143108/compiler/src/dotty/tools/dotc/core/Definitions.scala#L937-L955
def no5_6(): Unit =

  class Box[T] { def get(): T = ??? }

  // このような Java クラスをロードすると、
  /*
  class C {
    @NotNull String name;
    @NotNull List<String> getNames(String prefix); // List is Java-defined
    @NotNull Box<String> getBoxedName(); // Box is Scala-defined
  }
  */

  class C:

    // @NotNull が付与されているので nullable にはならない
    val name: String = ???

    // 結果型は @NotNull が付与されているので nullable にはならない
    // パラメータは nullable
    def getNames(prefix: String | Null): java.util.List[String] = ???

    // 結果型は @NotNull が付与されているので nullable にはならない（`Box[String] | Null` とはならない）
    // ただし、Box は Scala クラスなので `Box[String | Nul]` となる
    def getBoxedName(): Box[String | Null] = ???

//---
// Override check

// Java クラスを Scala クラスでオーバーライドする場合、利便性のために Null 型のルールが緩和される
// Java の メソッド `String f(String x)` は、Scala ではこのようにオーバーライドすることができる
def no6(): Unit =
  class ScalaClass1 extends JavaClass:
    override def f(x: String | Null): String | Null = ???

  // コンパイルが通ることを期待しているが、現状これはコンパイルエラーになる...
  // error overriding method f in class JavaClass of type (x$0: String | UncheckedNull): String | UncheckedNull; 
  // class ScalaClass2 extends JavaClass:
  //   override def f(x: String): String | Null = ???

  // 結果型は nullable ではないけど、実際には null が返される可能性があることに注意
  class ScalaClass3 extends JavaClass:
    override def f(x: String | Null): String = ???

  // コンパイルが通ることを期待しているが、現状これはコンパイルエラーになる...
  // error overriding method f in class JavaClass of type (x$0: String | UncheckedNull): String | UncheckedNull;
  // class ScalaClass4 extends JavaClass:
  //   override def f(x: String): String = ???
