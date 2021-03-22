# Scala3で追加された地味に嬉しい系の機能追加 {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Main Methods](#main-methods)
- [Match Expressions](#match-expressions)
- [Trait Parameters](#trait-parameters)
- [Universal Apply Methods](#universal-apply-methods)
- [Parameter Untupling](#parameter-untupling)
- [Vararg Splices](#vararg-splices)
- [Rules for Operators](#rules-for-operators)
  - [The infix Modifier](#the-infix-modifier)
  - [The @targetName Annotation](#the-targetname-annotation)
  - [Syntax Change](#syntax-change)
- [Extension Methods](#extension-methods)

<!-- /code_chunk_output -->

## 概要

Scala3 で新しく追加された機能の中から「今まで冗長だった記述が短くなる！地味に嬉しい！」という機能を集めてみました。本リポジトリの対応するサンプルコードと併せて見ていきましょう。


## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Main Methods](https://dotty.epfl.ch/docs/reference/changed-features/main-functions.html)
- [Match Expressions](https://dotty.epfl.ch/docs/reference/changed-features/match-syntax.html)
- [Trait Parameters](https://dotty.epfl.ch/docs/reference/other-new-features/trait-parameters.html)
- [Universal Apply Methods](https://dotty.epfl.ch/docs/reference/other-new-features/creator-applications.html)
- [Parameter Untupling](https://dotty.epfl.ch/docs/reference/other-new-features/parameter-untupling.html)
- [Vararg Splices](https://dotty.epfl.ch/docs/reference/changed-features/vararg-splices.html)
- [Rules for Operators](https://dotty.epfl.ch/docs/reference/changed-features/operators.html)
- [Extension Methods](https://dotty.epfl.ch/docs/reference/contextual/extension-methods.html)

## Main Methods

https://dotty.epfl.ch/docs/reference/changed-features/main-functions.html

- メソッドに `@main` アノテーションを付けると、そのメソッドは実行可能なプログラムとなる
- `@main` 付きのメソッドは、
  - トップレベルでも `object` の中でも書くことができる
  - コンパイラは内部的に、`def main(args: Array[String]): Unit` を持つクラスに変換する
  - 任意の数のパラメータを持つことができる
-  Scala2 系みたいに `extends App` で書くこともできるが、 Scala3 的には `@main` を推奨している

:memo: [MainMethods.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/MainMethods.scala)

## Match Expressions

https://dotty.epfl.ch/docs/reference/changed-features/match-syntax.html

`match` 式の構文的な優先順位が変更された。それにより、
1. `match` 式を連鎖できるようになった
2. `match` 式をピリオドの後に書くことができるようになった
3. `match` 式の被検査体（scrutinee）は、`InfixExpr` でなければならない。そのため、 `x: T match { ... }` はサポートされなくなったので、`(x: T) match { ... }` と書く

:memo: [MatchExpressions.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/MatchExpressions.scala)

## Trait Parameters

https://dotty.epfl.ch/docs/reference/other-new-features/trait-parameters.html

- `class` のパラメータと同じように `trait` にもパラメータを持たせられるようになった
- `trait` への引数は、`trait` が初期化される直前に評価される
- 同一 `trait` の `extends` など、曖昧な定義をするとコンパイルエラー

:memo: [TraitParameters.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/TraitParameters.scala) / [TraitParameters2.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/TraitParameters2.scala)

## Universal Apply Methods

https://dotty.epfl.ch/docs/reference/other-new-features/creator-applications.html

- 元々 `case class` はコンパニオンオブジェクトに `apply` メソッドが暗黙的に追加されるので、インスタンス生成時に `new` を書く必要はなかったが、Scala3 ではこのスキームをすべての具象クラスに一般化した
- `class` を定義すると、コンパニオンオブジェクトに対して、コンストラクタに対応した `apply` メソッドが自動的に追加される（constructor proxy と呼ばれている）
  - その結果、`class` のインスタンス生成時でも `new` が不要になった
- Javaのクラスも `new` を書かずにインスタンス生成できるようになった
- `new` を省略することで実装の詳細が隠され、コードがより読みやすくなる

:memo: [UniversalApplyMethods.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/UniversalApplyMethods.scala)

## Parameter Untupling

https://dotty.epfl.ch/docs/reference/other-new-features/parameter-untupling.html

- タプルのリストを `map` する際、これまでは、パターンパッチで分解（`case (x, y) =>`） しなければならなかったが、それが不要になった（`(x, y) =>`)

:memo: [ParameterUntupling.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ParameterUntupling.scala)

## Vararg Splices

https://dotty.epfl.ch/docs/reference/changed-features/vararg-splices.html

- vararg splices の構文が変更になった
- 今までは、`: _*` と書いていたけど、 `*` のみで良くなった

:memo: [VarargSplices.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/VarargSplices.scala)

## Rules for Operators

https://dotty.epfl.ch/docs/reference/changed-features/operators.html

### The infix Modifier

- メソッドや型がどのように適用されるかについて、コードベース間の一貫性を実現するためのもの
- メソッド作成者が、そのメソッドを `infix` 演算子として適用するのか、通常の方法で適用するのかを決定できる 

### The @targetName Annotation

シンボリック演算子に対して、 `@targetName` で任意の名前を付けられる。これにより、
- 他の言語から target name を使って呼び出すことができる。シンボリック名の低レベルエンコーディングを覚える必要がなくなる
- スタックトレースやその他の実行時診断の読みやすさが向上する
- ドキュメンテーションツールとしての役割を果たせる
 
### Syntax Change

- `infix` 演算子を複数行で書く場合に、行末ではなく行頭に書けるようになった（A leading infix operator）
- この構文を動作させるために、ルールが変更され、行頭の `infix` 演算子の前のセミコロンを推論しないようになった

:memo: [RulesForOperators.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/RulesForOperators.scala)

## Extension Methods

https://dotty.epfl.ch/docs/reference/contextual/extension-methods.html

- これまで `implicit class` で実現していた拡張メソッドに専用の構文が追加された
- extension methods は、通常のメソッド呼び出しと同じ様に、infix `.` で呼び出すことができる
- `<` や `+:` など、演算子の定義としても使える
- extension methods を参照可能にするためには4つの方法がある
  1. スコープ内で定義しているか、それを継承、インポートしている
  2. 参照先のスコープ内で型クラスのインスタンスが定義されている
  3. extension method `m` を `r.m` で参照する場合、`r` の暗黙のスコープで定義されている
  4. extension method `m` を `r.m` で参照する場合、型クラスのインスタンスが `r` の暗黙のスコープで定義されている

:memo: [ExtensionMethods.scala](/step02/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ExtensionMethods.scala)
