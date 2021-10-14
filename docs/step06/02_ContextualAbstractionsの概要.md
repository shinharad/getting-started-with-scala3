# Contextual Abstractionsの概要 {ignore=true}

**:construction: EPFLのドキュメントは、docs.scala-lang.org/scala3 に統合されたためこの内容は古くなっています。 :construction:**

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [Overview](#overview)
  - [Critique of the Status Quo (現状に対する批判)](#critique-of-the-status-quo-現状に対する批判)
  - [The New Design](#the-new-design)
    - [Contextual Abstractions の再設計の4つの基本的な変更点](#contextual-abstractions-の再設計の4つの基本的な変更点)
    - [Contextual Abstractions の発展的な言語機能](#contextual-abstractions-の発展的な言語機能)
    - [なぜ新しい設計にしたのか？](#なぜ新しい設計にしたのか)

<!-- /code_chunk_output -->

## 概要

Contextual Abstractions（コンテキストの抽象化）は、Scala 2 で様々なユースケースで使用されていた `implicit` の再設計のことが記載されています。それなりにボリュームがあるので、この Step と 次の Step で前半と後半に分けて見ていきたいと思います。

まずは、Contextual Abstractions の概要で、Scala 2 の `implicit` には現状どのような課題があって、新しい設計はどうなったか、なぜ新しい設計にしたのかについて確認します。


## ドキュメント参照先

[Reference](https://dotty.epfl.ch/docs/reference/overview.html) の Contextual Abstractions よりこちらを参照します。

- [Overview](https://dotty.epfl.ch/docs/reference/contextual/motivation.html)

## Overview

https://dotty.epfl.ch/docs/reference/contextual/motivation.html

### Critique of the Status Quo (現状に対する批判)

- Scala 2 の `implicit` は、Scala の最も特徴的な機能であり、次のようなユースケースに対してコンテキストを抽象化するための基本的な方法として提供されている
  - 型クラスの実装 (implementing type classes)
  - コンテキストの確立 (establishing context)
  - 依存性の注入 (dependency injection)
  - ケイパビリティの表現 (expressing capabilities)
  - 新しい型の計算 (computing new types)
  - これらの間のリレーションシップの証明 (proving relationships between them)
- `implicit` は優れた機能であると同時に、最も議論を呼んでいる機能でもある。特に批判されているのは、このあたり
  1. それぞれのユースケースの構文が似ていて、混乱させている
      ```scala
      // 型クラスのインスタンスを表現している（conditional implicit value）
      implicit def i1(implicit x: T): C[T] = ...
      // implicit conversion
      implicit def i2(x: T): C[T] = ...
  1. `implicit` の import が不可解な型のエラーを引き起こす
  1. `implicit` という単一の修飾子が、初学者にとって意図ではなくメカニズムを伝えてしまうので、誤読しやすい
      - 型クラスのインスタンスの場合は、unconditional は `implicit object` や `implicit val` だが、conditional は `implicit def` だったり
  1. implicit parameter の構文上の欠点
      ```scala
      def currentMap(implicit ctx: Context): Map[String, Int]
      ```
      - 上記に対して、`currentMap("abc")` と書くと、`String` 型の `"abc"` が implicit parameter の `ctx` の明示的な引数として取られてしまうため、代わりに `currentMap.apply("abc")` と書かなければならない
      - また、implicit parameter には名前が必要だが、多くの場合はその名前が参照されることはないので、ちょっと面倒
  1. `implicit` はツールの課題となっている
      - 利用可能な `implicit` のセットはコンテキストに依存するので、コード補完はコンテキストを考慮しなければならない。これはIDEでは可能だが、Scaladocのように静的なWebページをベースにしたツールでは近似値しか提供できない
      - もうひとつの問題は、深くネストした `implicit` の再帰的な検索が失敗した場合、非常に不明確なエラーメッセージが表示されてしまう
- 歴史的に見てこれらの欠点の多くは、`implicit` のユースケースが徐々に発見されていったという経緯に起因している
  - Scala には元々 implicit conversion しかなかった
  - implicit parameter と型クラスのインスタンスの定義は、2006年以降に登場したので、便利そうだったので同じような構文を採用したが、これらを区別するための努力は行われていなかった
- 既存の Scala プログラマは現状に慣れていて変更の必要性をあまり感じていないが、初学者にとっては大きなハードルとなっている。このハードルを超えるために、根本的に新しい設計を考える必要があった

### The New Design

Contextual Abstractions の新しい設計は、全体的に機能の相互作用を回避し、言語の一貫性と直交性を高めています。

#### Contextual Abstractions の再設計の4つの基本的な変更点

1. Given Instances
    - 暗黙的な修飾子を多数の機能と混ぜ合わせるのではなく、型に対して合成可能な用語を定義する単一の方法を持つ
1. Using Clauses 
    - 暗黙のパラメータのための新しい構文
1. "Given" Imports
    - given のみをインポートするためのインポートセレクタ
1. Implicit Conversions
    - implicit conversion は、標準的な `Conversion` クラスのインスタンスとして表現されるようになった
    - 今までの implicit conversion は、段階的に廃止される

#### Contextual Abstractions の発展的な言語機能

公式ドキュメントでは、再設計された Contextual Abstractions の基本的な機能をもとにして、以下のような発展的な機能についても解説しています。

- Context Bounds
  - 変更されずに引き継がれる
- Extension Methods
  - Scala 2 の `implicit class` を置き換え、型クラスとの統合性を高める
- Implementing Type Classes
  - 新しい構文を使って、一般的な型クラスをどのように実装するかを解説している
- Type Class Derivation
  - ADTs のための型クラスのインスタンスを自動的に導出させる
- Multiversal Equality
  - 型安全性の高い等式をサポートするために、特別な型クラスを導入する
- Context Functions
  - コンテキストパラメータを抽象化する方法を提供する
- By-Name Context Parameters
  - ループを使わずに再帰的な合成値を定義するために不可欠なツール
- Relationship with Scala 2 Implicits
  - 旧スタイルの implicits と新スタイルの givens の関係と、一方から他方への移行方法について解説している

#### なぜ新しい設計にしたのか？

なぜ、既存の `implicit` に手を加えるのではなく、新しい構文とルールを導入する必要があったのか、その理由は以下の通りとされています。

- 現状の `implicit` は、明らかに構文上の問題があり、それを解決するためには異なる構文が必要だった
- 移行の観点で、途中で `implicit` のルールを変えることはできないが、構文の変更であれば容易
  - 新しいルールで新しい構文を導入し、クロスコンパイルを容易にするためにしばらくの間は古い構文をサポートし、将来的には古い構文を非推奨にして段階的に廃止にする
  - 新しい構文を導入せずに古い構文を維持することは、このような方法を提供できないし、進化のための実行可能な方法を提供できない
  - 教育の問題で、既存の文献や教材に修正を加えると、特に初学者を混乱させてしまう
      - 新しい設計と明確に分離しておくことで、`implicit` について言及しているものはすべて古いものと考えることができる
