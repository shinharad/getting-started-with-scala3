# Referenceその他の機能たち {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Imports](#imports)
- [Export Clauses](#export-clauses)
- [Open Classes](#open-classes)
- [Changes in Overload Resolution](#changes-in-overload-resolution)
- [Explicit Nulls](#explicit-nulls)

<!-- /code_chunk_output -->

## 概要

Step1では、序盤で見ておきたいものを抽出しましたが、ここでは Reference でまだ取り上げていない、Imports、Export Clauses、Open Classes、Changes in Overload Resolution、Explicit Nulls について見ていきたいと思います。

## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) からこちらを参照します。

- [Imports](https://dotty.epfl.ch/docs/reference/changed-features/imports.html)
- [Export Clauses](https://dotty.epfl.ch/docs/reference/other-new-features/export.html)
- [Open Classes](https://dotty.epfl.ch/docs/reference/other-new-features/open-classes.html)
- [Changes in Overload Resolution](https://dotty.epfl.ch/docs/reference/changed-features/overload-resolution.html)
- [Explicit Nulls](https://dotty.epfl.ch/docs/reference/other-new-features/explicit-nulls.html)


## Imports

https://dotty.epfl.ch/docs/reference/changed-features/imports.html

- Scala3 の `import` 文のワイルドカードは、`_` から `*` に変わった
  - 例) `import scala.annotation.*`
- `import` のリネームは `=>` から `as` に変わった
  - 例) `import scala.annotation as ann`
- Scala3.0 の時点ではまだ `_` や `=>` はサポートしているが、将来のバージョンで廃止される予定

:memo: [Imports.scala](/step05/import-export/src/main/scala/com/github/shinharad/gettingStartedWithScala3/Imports.scala)

## Export Clauses

https://dotty.epfl.ch/docs/reference/other-new-features/export.html

- `export` 句を使うと、構成するメンバのエイリアスを定義できる
- 例えば、`export [メンバ名].[メソッド名]` とすると、そのメソッドのエイリアスが `[メソッド名]` として自動的に作成されるイメージ
- `export` 句は、クラス設計のコンポジションを表現する時に便利
  - 継承よりコンポジションを優先することは推奨されている
    - コンポジションはコンポーネントをブラックボックスとして扱うのに対し、継承はオーバーライドによってコンポーネントの内部動作に影響を与える
    - 継承による密接な結合よりもコンポジションによる緩やかな結合の方が柔軟性がある
  - `extends` による継承よりも `export` 句の方が、メンバをリネームできたりと柔軟性がある
  - `export` 句で作成されるエリアスは、`final` で定義されるので、そのエイリアスを `override` することはできない
- `export` 句で作成されるエイリアスと既存のメンバとの定義が重複する場合はコンパイルエラーになる
- `export` 句は、パッケージオブジェクトからトップレベル定義への移行によってできたギャップを埋めるためのものでもある
  - パッケージオブジェクトがあるクラスを継承している場合、そのパッケージオブジェクトをトップレベル定義へ移行する際に、継承の代わりに `export` 句を付けてトップレベルに定義する
  
:memo: [ExportClauses.scala](/step05/import-export/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ExportClauses.scala) / [ExportClausesMigration.scala](/step05/import-export/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ExportClausesMigration.scala)

## Open Classes

https://dotty.epfl.ch/docs/reference/other-new-features/open-classes.html

- `class` に `open` を付けると、その `class` は別のファイルで定義した `class` から継承可能なことを明示できる
- `open` ではない `class` を継承すると警告メッセージを表示する
- Scala3.0 ではまだ使用できない機能なので、有効にするにはコンパイラオプションに `-source future` を設定する必要がある
- 一応 `trait` や `abstract class` にも `open` を付けることができるが、冗長だし意味が無い
- `open` は、`final` または `sealed` と一緒に定義できない
- `open` ではないクラスをアドホックに継承する方法が用意されている（テストダブルや既存クラスの一時的なパッチ適用など、限られた目的のために）
  - `import scala.language.adhocExtensions` を付ける
  - コンパイラオプションに `-language:adhocExtensions` を付ける
- `open` と `final`、「何も付けない」の使い分け
  - クラスの継承が可能であることを明示したい => `open`
  - 正しさや安全性を保証するために、クラスの継承を明示的に禁止したい => `final`
  - アドホックに拡張することを許容する（ただし自己責任で） => 何も付けない

:memo: [OpenClassWriter.scala](/step05/open-class/src/main/scala/com/github/shinharad/gettingStartedWithScala3/OpenClassWriter.scala) / [OpenClassEncryptedWriter.scala](/step05/open-class/src/main/scala/com/github/shinharad/gettingStartedWithScala3/OpenClassEncryptedWriter.scala)

## Changes in Overload Resolution

*** TO BE FILLED IN ***


## Explicit Nulls

https://dotty.epfl.ch/docs/reference/other-new-features/explicit-nulls.html

- Explicit Nulls は、Scala の型システムを変更するオプトイン機能で、参照型（`AnyRef` を継承した型）に `null` を設定できないようにする。つまりこれはコンパイルエラーになる
  ```scala
  val x: String = null // error: found `Null`, but required `String`
  ```
  代わりに Union Types を使用して nullable であることを明示する必要がある
  ```scala
  val x: String | Null = null // ok
  ``` 
- Explicit Nulls は、コンパイラオプション `-Yexplicit-nulls` を指定すると有効になる
- Explicit Nulls を有効にすると、型階層が変更され、`Null` は `Any` のサブタイプとなる（`AnyRef` のサブタイプではなくなる）
  - [元の型階層](https://docs.scala-lang.org/resources/images/tour/unified-types-diagram.svg)
  - [変更後の型階層](https://dotty.epfl.ch/images/explicit-nulls/explicit-nulls-type-hierarchy.png)
- Java との相互運用性
  - Java クラスの参照型のメンバーは、暗黙的に nullable としてロードされる
  - `@NotNull` が付与されている場合は nullable とはならない
  - Scala クラスが Java クラスをオーバーライドする場合、この機能を使うと Null 型のルールが緩和され、nullable でも non-null でも許容される
- Flow Typing
  - nullable な変数が if 式などで non-null であると判定した場合、そのスコープ内では変数を non-null として扱える
  - Scala3 の Flow Typing は、nullability に限定される
  - 参考) [Flow-sensitive typing](https://en.wikipedia.org/wiki/Flow-sensitive_typing)
- UnsafeNulls
  - 多くの nullable な値を扱うのは時として困難になるので、language feature として `unsafeNulls` が提供されている
  - `unsafeNulls` のスコープの中では、すべての `T | Null` は `T` として使用することができる
  - `unsafeNulls` を適用する方法は2つ
    1. `scala.language.unsafeNulls` をインポートする
    2. コンパイラオプション `-language:unsafeNulls` を設定する  
   （グローバルで適用されるので、主に移行目的で使用することを想定している）
  - `unsafeNulls` のモチベーションは、Explicit Nulls のためのより良い移行経路を提供すること
    - コンパイラオプションに `-language:unsafeNulls` を追加することで、この機能を試すことができる
    - 将来的に完全な Explicit Nulls 機能へ移行するためには、`-language:unsafeNulls` をやめて、必要なときだけ `import scala.language.unsafeNulls` を使うようにする

:memo: [ExplicitNulls1.scala](/step05/explicit-nulls/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ExplicitNulls1.scala) / [ExplicitNulls2.scala](/step05/explicit-nulls/src/main/scala/com/github/shinharad/gettingStartedWithScala3/ExplicitNulls2.scala)
