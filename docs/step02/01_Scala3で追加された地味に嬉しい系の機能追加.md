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

Scala3 で新しく追加された機能の中から、「今まで冗長だった記述が短くなる！地味に嬉しい！」という機能を集めてみました。本リポジトリの対応するサンプルコードと併せて見ていきましょう。


## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Main Methods](https://dotty.epfl.ch/docs/reference/changed-features/main-functions.html)
- [Match Expressions](https://dotty.epfl.ch/docs/reference/changed-features/match-syntax.html)
- [Trait Parameters](https://dotty.epfl.ch/docs/reference/other-new-features/trait-parameters.html)
- [Universal Apply Methods](https://dotty.epfl.ch/docs/reference/other-new-features/creator-applications.html)
- [Parameter Untupling](https://dotty.epfl.ch/docs/reference/other-new-features/parameter-untupling.html)
- [Vararg Splices](https://dotty.epfl.ch/docs/reference/changed-features/vararg-splices.html)
- [Rules for Operators](https://dotty.epfl.ch/docs/reference/changed-features/operators.html)
- [Extension Methods](https://dotty.epfl.ch/docs/reference/contextual/extension-methods.html) (一部のみ)

## Main Methods

https://dotty.epfl.ch/docs/reference/changed-features/main-functions.html

- メソッドに `@main` アノテーションを付けると、そのメソッドは実行可能なプログラムとなる
- `@main` 付きのメソッドは、
  - トップレベルでも `object` の中でも書くことができる
  - コンパイラは内部的に、`def main(args: Array[String]): Unit` を持つクラスに変換する
  - 任意の数のパラメータを持つことができる
-  Scala2 系みたいに `extends App` で書くこともできるが、 Scala3 的には `@main` を推奨している

## Match Expressions

https://dotty.epfl.ch/docs/reference/changed-features/match-syntax.html

`match` 式の構文的な優先順位が変更された。それにより、
1. `match` 式を連鎖できるようになった
2. `match` 式をピリオドの後に書くことができるようになった
3. `match` 式の被検査体（scrutinee）は、`InfixExpr` でなければならない。そのため、 `x: T match { ... }` はサポートされなくなったので、`(x: T) match { ... }` と書く

## Trait Parameters

https://dotty.epfl.ch/docs/reference/other-new-features/trait-parameters.html

- class のパラメータと同じように trait にもパラメータを持たせられるようになった
- trait への引数は、trait が初期化される直前に評価される
- 同一 trait の extends など、曖昧な定義をするとコンパイルエラー

## Universal Apply Methods

https://dotty.epfl.ch/docs/reference/other-new-features/creator-applications.html

- 元々 `case class` はコンパニオンオブジェクトに `apply` メソッドが暗黙的に追加されるので、`new` を書く必要はないが、Scala3 ではこのスキームをすべての具象クラスに一般化した
- `class` に `inline def apply` を書くと、コンパニオンオブジェクトに `apply` メソッドが暗黙的に追加される
- `new` を省略することで実装の詳細が隠され、コードがより読みやすくなる

```
[個人メモ]
new の省略といえば、これまでもコンパニオンオブジェクトに apply を書けば case class じゃなくても省略することはできました。
それに加え、ファクトリはコンパニオンオブジェクトで明示的に書きたいとも思うのですが、
それを class に inline def apply を明示的に書くことによるメリットがどれくらいあるか、考えたいなと。
```

## Parameter Untupling

https://dotty.epfl.ch/docs/reference/other-new-features/parameter-untupling.html

- タプルのリストを `map` する際、これまでは、パターンパッチで分解（`case (x, y) =>`） しなければならなかったが、それが不要になった（`(x, y) =>`)

## Vararg Splices

https://dotty.epfl.ch/docs/reference/changed-features/vararg-splices.html

- vararg splices の構文が変更になった
- 今までは、`: _*` と書いていたけど、 `*` のみで良くなった

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
- この構文を動作させるために、ルールが変更され、A leading infix operator の前にセミコロンを推論しないようになった


## Extension Methods

https://dotty.epfl.ch/docs/reference/contextual/extension-methods.html

現段階では、Operators まで確認することにします。
Generic Extensions 以降はまだ紹介してない機能が含まれるので追々見ていこうかなと。

- これまで `implicit class` で実現していた拡張メソッドに専用の構文が追加された
- Extension Methods は、infix `.` で呼び出すことができる
