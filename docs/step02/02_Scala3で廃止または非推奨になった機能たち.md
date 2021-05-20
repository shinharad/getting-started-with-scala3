# Scala 3で廃止または非推奨になった機能たち {ignore=ture}


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Dropped: Delayedinit](#dropped-delayedinit)
- [Dropped: Scala 2 Macros](#dropped-scala-2-macros)
- [Dropped: Do-While](#dropped-do-while)
- [Dropped: Procedure Syntax](#dropped-procedure-syntax)
- [Dropped: Package Objects](#dropped-package-objects)
- [Dropped: Early Initializers](#dropped-early-initializers)
- [Dropped: Class Shadowing](#dropped-class-shadowing)
- [Dropped: Limit 22](#dropped-limit-22)
- [Dropped: XML Literals](#dropped-xml-literals)
- [Dropped: Symbol Literals](#dropped-symbol-literals)
- [Dropped: Auto-Application](#dropped-auto-application)
- [Dropped: Weak Conformance](#dropped-weak-conformance)
- [Deprecated: Nonlocal Returns](#deprecated-nonlocal-returns)
- [Dropped: private[this] and protected[this]](#dropped-privatethis-and-protectedthis)
- [Dropped: wildcard initializer](#dropped-wildcard-initializer)

<!-- /code_chunk_output -->


## 概要

Scala 3 で廃止または非推奨になった機能を見てみましょう。


## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Dropped: Delayedinit](https://dotty.epfl.ch/docs/reference/dropped-features/delayed-init.html)
- [Dropped: Scala 2 Macros](https://dotty.epfl.ch/docs/reference/dropped-features/macros.html)
- [Dropped: Do-While](https://dotty.epfl.ch/docs/reference/dropped-features/do-while.html)
- [Dropped: Procedure Syntax](https://dotty.epfl.ch/docs/reference/dropped-features/procedure-syntax.html)
- [Dropped: Package Objects](https://dotty.epfl.ch/docs/reference/dropped-features/package-objects.html)
- [Dropped: Early Initializers](https://dotty.epfl.ch/docs/reference/dropped-features/early-initializers.html)
- [Dropped: Class Shadowing](https://dotty.epfl.ch/docs/reference/dropped-features/class-shadowing.html)
- [Dropped: Limit 22](https://dotty.epfl.ch/docs/reference/dropped-features/limit22.html)
- [Dropped: XML Literals](https://dotty.epfl.ch/docs/reference/dropped-features/xml.html)
- [Dropped: Symbol Literals](https://dotty.epfl.ch/docs/reference/dropped-features/symlits.html)
- [Dropped: Auto-Application](https://dotty.epfl.ch/docs/reference/dropped-features/auto-apply.html)
- [Dropped: Weak Conformance](https://dotty.epfl.ch/docs/reference/dropped-features/weak-conformance.html)
- [Deprecated: Nonlocal Returns](https://dotty.epfl.ch/docs/reference/dropped-features/nonlocal-returns.html)
- [Dropped: private[this] and protected[this]](https://dotty.epfl.ch/docs/reference/dropped-features/this-qualifier.html)
- [Dropped: wildcard initializer](https://dotty.epfl.ch/docs/reference/dropped-features/wildcard-init.html)



## Dropped: Delayedinit

https://dotty.epfl.ch/docs/reference/dropped-features/delayed-init.html

- `DelayedInit` は、オブジェクトの初期化を実行時まで遅延させるもので、メイン処理を簡潔に書くための `App` はこの `DelayedInit` を継承している
  - [App.scala](https://github.com/scala/scala/blob/2.13.x/src/library/scala/App.scala)
  - [DelayedInit.scala](https://github.com/scala/scala/blob/2.13.x/src/library/scala/DelayedInit.scala)
- `DelayedInit` の初期化処理 `delayedInit()` は特別扱いされていて、通常はユーザコードから呼び出すのではなく、`DelayedInit` を継承した `class` や `object` に対して、コンパイラが生成したコードとして呼び出される
- ただし `trait` は例外で、`DelayedInit` を継承し `trait` のボディに初期化処理を書いたとしても、コンパイラは `delayedInit()` を呼び出すコードを生成しない。このように予想外の挙動をすることがあったため、この特別な処理は廃止されることになった
- その結果、`App` は部分的に壊れたため、一部の JVM 言語では、初期化処理が正常に動作しなくなった
- `App` は引き続き、メイン処理を簡潔に書くために使用できるが、ベンチマークの計測には使用しない方が良い
- 今後は、`@main` か `def main(args: Array[String]): Unit` のどちらかを使用する

## Dropped: Scala 2 Macros

https://dotty.epfl.ch/docs/reference/dropped-features/macros.html

- Scala 2 までの実験的なマクロシステムは廃止された
- 詳細はここでは触れないが、Scala 3 のマクロとは互換性が無いので注意が必要

## Dropped: Do-While

https://dotty.epfl.ch/docs/reference/dropped-features/do-while.html

- do-whileは比較的使われることが少ないため廃止された
  - `do <body> while <cond>` の代わりに `while ({ <body> ; <cond> }) ()` を使う
- 詳細は、本リポジトリ内の対応するコードを参照

:memo: [DroppedDoWhile.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedDoWhile.scala)

## Dropped: Procedure Syntax

https://dotty.epfl.ch/docs/reference/dropped-features/procedure-syntax.html

- Procedure syntax: `def f() { ... }` は廃止された
- その代わりに、以下を使う
  - `def f() = { ... }`
  - `def f(): Unit = { ... }`

:memo: [DroppedProcedureSyntax.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedProcedureSyntax.scala)

## Dropped: Package Objects

https://dotty.epfl.ch/docs/reference/dropped-features/package-objects.html

- Scala 3 では、あらゆる種類の定義をトップレベルで記述できるようになったため、Package objects は不要となった
- Scala 3.0 ではまだ利用可能だが、今後は非推奨となり削除される予定

:memo: [DroppedPackageObjects.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedPackageObjects.scala)

## Dropped: Early Initializers

https://dotty.epfl.ch/docs/reference/dropped-features/early-initializers.html

- Early Initializers は廃止された
  - `class C extends { ... } with SuperClass ...`
- 今までは、 `trait` にはパラメータを設定できないという課題を解決するための書き方だったが、ほとんど使われていなかった
- Scala 3 で追加された `Trait Parameters` によって完全に不要となった

## Dropped: Class Shadowing

https://dotty.epfl.ch/docs/reference/dropped-features/class-shadowing.html

- Scala 2 では、親クラスで定義されているクラスと同じ名前のクラスを子クラスで定義することができた
  - オーバーライドしているように見えるが、実際には Scala 2 のクラスはオーバーライドできない
- Scala 3 では、内部操作を一貫させ物事をきれいに保つために、このような場合は別名を付ける必要がある。同じ名前はコンパイルエラー

:memo: [DroppedClassShadowing.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedClassShadowing.scala)

## Dropped: Limit 22

https://dotty.epfl.ch/docs/reference/dropped-features/limit22.html

- Function のパラメータと、Tuple のフィールドの最大数の制限値 22 が廃止された
- Function は、任意のパラメータを持てるようになり、`Function22` を超える場合は、新しい `scala.runtime.FunctionXXL` に置き換わる
- Tuple は、任意の数のフィールドを持てるようになり、`Tuple22` を超える場合は、新しい `scala.runtime.TupleXXL` に置き換わる
- どちらも配列を使って実装されている
  - [FunctionXXL.scala](https://github.com/lampepfl/dotty/blob/master/library/src/scala/runtime/FunctionXXL.scala)
  - [TupleXXL.scala](https://github.com/lampepfl/dotty/blob/master/library/src/scala/runtime/TupleXXL.scala)

:memo: [DroppedLimit22.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedLimit22.scala)

## Dropped: XML Literals

https://dotty.epfl.ch/docs/reference/dropped-features/xml.html

- XML リテラルはまだサポートされているが、近い将来には削除され、[XML string interpolation](https://github.com/lampepfl/xml-interpolator) に置き換えられる予定
  - `xml""" ... """`
- `import dotty.xml.interpolator.*` を使用するということだが、詳細はまだ不明

## Dropped: Symbol Literals

https://dotty.epfl.ch/docs/reference/dropped-features/symlits.html

- `scala.Symbol` は将来的に非推奨となり削除される予定
- `scala.Symbol` はまだ存在するので一応使用することはできるが、代わりにプレーンな文字列 `"xyz"` の使用が推奨されている

:memo: [DroppedSymbolLiterals.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedSymbolLiterals.scala)

## Dropped: Auto-Application

https://dotty.epfl.ch/docs/reference/dropped-features/auto-apply.html

- nullary method（例: `def next()`）の呼び出し方で、以下のように変更された
  - Scala 2 は、 `def next()` を `next` で呼び出すと、暗黙的に `()` が挿入されていた
  - Scala 3 は、 `def next()` は `next()` で呼び出す必要がある。`next` はコンパイルエラー
- このルールは、nullary method をオーバーライドしたメソッドにも適用される
  ```scala
  class A:
    def next(): Int

  class B extends A:
    def next: Int // overriding error: incompatible type
  ```
- ただし、Java の場合はこのルールから除外されている。その理由は、
  - 例えば、`xs.toString().length()` を `xs.toString.length` と書くのは、uniform access principle に準拠しているため、Scalaのイディオムとなっている
  - uniform access principle は、平たく言うと、副作用の無いメソッドをまるでフィールドにアクセスしてるかのように `()` を省略できるようにすべきというもの
  - Scala では、副作用がある場合は `()` を付け、副作用が無いものは `()` を省略して書くことが推奨されているが、Java で定義されたメソッドは、このような区別はできない
  - そこで Scala では、これまでもパラメータの無い参照を許可することで、クライアント側の問題を解決していた
  - Scala 2 がすべてのメソッドの参照に対してこの自由を認めているのに対し、Scala 3 では、Scala 3 で定義されていない外部メソッドの参照に限定している
  - 後方互換性を考慮して、Scala 3 では、Scala 2 で定義された nullary method に対して、オーバーライドしたメソッドも含めて `()` を自動的に挿入している

:memo: [DroppedAutoApplication.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedAutoApplication.scala)

## Dropped: Weak Conformance

https://dotty.epfl.ch/docs/reference/dropped-features/weak-conformance.html

- Scala では、型の集合の least upper bound を計算するときに、weak conformance の関係を使うことがある
- 例えば、`List(1.0, math.sqrt(3.0), 0, -3.3)` は、`(Double, Double, Int, Double)` という型の集合なので、`Int` が `Double` に変換され、`List[Double]` という型を持つようになる
- Scala 2 では、この weak conformance は、すべての `numeric` 型（`Char` も含む）に適用され、式がリテラルであるかどうかに関係なく適用されていた
  - 例えば、`Char` を含む `(Int, Char, Double)` が `List[Doube]` に型付けされていて、これは意図したものではなかった
- そこで Scala 3 では、weak conformance を `Int` リテラルに限定した
  - つまり、`(Double, Double, Int, Double)` は `List[Double]` になるのは変わらないが、 `(Int, Char, Double)` は、least upper bound である `AnyVal` に変換されるので、`List[AnyVal]` に型付けされる

:memo: [DroppedWeakConformance.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedWeakConformance.scala)

## Deprecated: Nonlocal Returns

https://dotty.epfl.ch/docs/reference/dropped-features/nonlocal-returns.html

- ネストした匿名関数からのretrun（Nonlocal Returns）は非推奨となった
- Nonlocal Returns は、`scala.runtime.NonLocalReturnException` を `throw` して `catch` することで実装されているが、以下のような問題がある
  - プログラマが意図したものであることはほとんどない
  - `Exception` を `throw` したり `catch` したりすることで隠れたパフォーマンスコストが問題になることがある
  - Exception Handler がすべての `Exception` を `catch` していると、`NonLocalReturnException` を傍受できてしまう
- 代替機能は、`scala.util.control.NonLocalReturns` で提供されている

:memo: [DeprecatedNonlocalReturns.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DeprecatedNonlocalReturns.scala)

## Dropped: private[this] and protected[this]

https://dotty.epfl.ch/docs/reference/dropped-features/this-qualifier.html

- Scala 3 では、`private[this]` および `protected[this]` アクセス修飾子は非推奨となり、段階的に廃止される
- 以前は、このような目的で付けられていた
  - `private[this]` を付けたフィールドへのアクセスは、JVMレベルでのフィールドへの直接アクセスになるため、若干高速になる（Java のフィールドと等価になる）
    - 細かいパフォーマンスチューニングをする際に意識的に付けられていた
  - getters と setters の生成を避ける
  - `private[this]` でオブジェクト非公開定義にすることで、変位指定チェックから除外する
    - (Scala 2 では `protected[this]` も除外しているが、これは不健全であることが判明したため削除された）
- Scala 3 では、
  - コンパイラは、`private` メンバが `this` を介してのみアクセスするようになった
  - このようなメンバは、`private[this]` が宣言されていたかのように扱われる
  - `protected[this]` は代替機能が実装されることなく削除される

## Dropped: wildcard initializer

https://dotty.epfl.ch/docs/reference/dropped-features/wildcard-init.html

- 初期化されていないフィールドを示すために使用されていた `_` が削除された
- 代わりに `scala.compiletime.uninitialized` を使う
  - 例) `var x: String = uninitialized`

:memo: [DroppedWildcardInitializer.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/DroppedWildcardInitializer.scala)
