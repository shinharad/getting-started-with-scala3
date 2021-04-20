# AN OVERVIEW OF TASTY {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [ドキュメント参照先](#ドキュメント参照先)
- [AN OVERVIEW OF TASTY](#an-overview-of-tasty-1)
  - [What is TASTy?](#what-is-tasty)
    - [The issue with .class files](#the-issue-with-class-files)
    - [TASTy to the Rescue](#tasty-to-the-rescue)
      - [Key points](#key-points)
  - [Tasty benefits](#tasty-benefits)

<!-- /code_chunk_output -->

## 概要

Scala3 で sbt プロジェクトをコンパイルすると、_target/scala-3.0.0-RC3/classes_ 配下には、_.class_ ファイルの他に _.tasty_ ファイルが作成されていることが確認できます。この _.tasty_ ファイルには、Scala3 の型やメソッドのシグネチャなどの情報が、TASTy と呼ばれる format で格納されています。

ここでは、この TASTy が何を解決するためのものなのかを見ていきたいと思います。

## ドキュメント参照先

[Scala3 Documentation](https://docs.scala-lang.org/scala3) からこちらを参照します。

- [AN OVERVIEW OF TASTY](https://docs.scala-lang.org/scala3/guides/tasty-overview.html)


## AN OVERVIEW OF TASTY

https://docs.scala-lang.org/scala3/guides/tasty-overview.html

### What is TASTy?

- TASTy は、Typed Abstract Syntax Trees の頭文字をとったもの
- Scala3 の high-level interchange format
- _.tasty_ ファイルは、scalac コンパイラによって生成され、プログラムの構文構造、型、位置、さらにはドキュメントなど、ソースコードに関するすべての情報が含まれている
- _.tasty_ ファイルは、JVM 上で実行するために生成される _.class_ ファイルよりもはるかに多くの情報を含んでいる
- Scala3 のコンパイルプロセスはこのようになっていて、_.scala_ から _.tasty_ を生成したうえで、_.class_ が生成される
  ```
           +-------------+    +-------------+    +-------------+
  $ scalac | Hello.scala | -> | Hello.tasty | -> | Hello.class |
           +-------------+    +-------------+    +-------------+
                  ^                  ^                  ^
                  |                  |                  |
            Your source        TASTy file         Class file
                code           for scalac        for the JVM
                                (contains         (incomplete
                                complete         information)
                              information)
  ```

#### The issue with .class files

_.class_ ファイルは、型消去（type erasure）などの問題から、実際にはコードの不完全な表現となっている。

例えば `List` の例を挙げると、

```scala
val xs: List[Int] = List(1, 2, 3)
```

このコードがコンパイルされると、JVM の互換性の要件を満たすために、_.class_ ファイルの内容は次のようなコードに置き換わっている。

```
public scala.collection.immutable.List<java.lang.Object> xs();
```

このように、Scala のコードを JVM で動作させるために、型消去が行われ、`xs: List[Int]` は、 `xs: List[java.lang.Object]` として表現されるので、`Int` 型の情報は失われることになる。

そのため、`List[Int]` の要素にアクセスする場合、_.class_ ファイルでは、 `Int` 型が消去されてしまうので、このようにキャストしてアクセスすることになる。

```scala
// 元のコード
val x = xs(0)

// classファイルではこのように置き換わる
int x = (Int) xs.get(0)               // Java-ish
val x = xs.get(0).asInstanceOf[Int]   // more Scala-like
```

型消去は、互換性のために行われていることではあるが、例えば、既にコンパイルされているライブラリに対して Scala プログラムをコンパイルしようとすると問題が発生する。

この問題を解決するためには、_.class_ ファイルよりも多くの情報が必要となる。

また、ここでは、型消去の話題だけを取り上げているが、JVM が認識していない他のすべての Scala の構造にも同様の問題がある。これには、Union Types、Intersection Types、Trait parameters など、その他多くの Scala3 の機能が含まれている。

#### TASTy to the Rescue

- TASTy format は、型チェック後の完全な abstract syntax tree (AST) を保存する
- Scala3 では、この TASTy format を Scala 2.13 の "Pickle" format の代わりに使用する
  - "Pickle" format は、_.class_ ファイル内のオリジナルの型に関する情報を持たないか、public API のみを持つフォーマット
- AST 全体を保存することには、多くの利点がある
  - 分割コンパイル
  - 異なる JVM バージョンでの再コンパイル
  - プログラムの静的解析 など

##### Key points

_.class_ ファイルの問題点を整理すると、

1. Scala の型は、_.class_ ファイルでは完全には表現できない
2. コンパイル時に得られる情報と実行時に得られる情報には違いがあることを理解する
    - コンパイル時は、`xs` が `List[Int]` であることは知っている
    - コンパイラが対象のコードを _.class_ ファイルに書き込むときは、`xs` は `List[Object]` として書き込むので、`xs` にアクセスするすべての場所にキャスト情報が追加される
    - その結果、実行時には、`xs` が `List[Int]` であることを JVM は知らないことになる

Scala3 と TASTy では、コンパイル時に関するもう一つの重要な注意点がある。

- 他の Scala3 ライブラリを使用する Scala3 コードを書いた場合、scalac はそれらの _.class_ ファイルを読む必要はなくて、代わりに _.tasty_ ファイルを読み込むことができる
- これは、Scala 2.13 と Scala3 を別々にコンパイルして互換性を持たせるために重要

### Tasty benefits

コードの完全な表現を持つことには多くの利点がある。

- コンパイラはこれを利用して、分離コンパイル（separate compilation）をサポートしている
- Scala の Language Server Protocol (LSP) では、
  - ハイパーリンク、コマンド補完、ドキュメンテーションをサポートするために使用される
  - 参照検索や rename などのグローバルな操作にも使用される
- TASTy は、新世代の [reflection-based macros](https://dotty.epfl.ch/docs/reference/metaprogramming/macros.html) の優れた基盤となる
- Optimizer や Analyzer は、より詳細なコード解析や高度なコード生成に利用できる
