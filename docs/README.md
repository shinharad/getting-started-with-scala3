# Overview

Scala 3 のドキュメントの散策は、このような流れで進めたいと思います。

## Part 1

### Step1

最初に Scala 3 を書くための環境構築をします。次に、Scala 3 の公式ドキュメントを俯瞰し、最後にインデントベースのシンタックスと新しい制御構造について触れます。

### Step2

前半は Scala 3 で追加された「地味に嬉しい系の機能追加」について、後半は Scala 3 で廃止または非推奨になった機能について取り上げていきます。

### Step3

Enums や Algebraic Data Types (ADTs) で新しいシンタックスが追加されたことで、今までの冗長な記述が簡潔に書けるようになったことや、新しく追加された Intersection Types、Union Types、Opaque Type Aliases で型の表現がより豊かになったことで、設計の幅が広がることを確認します。

### Step4

Scala 3 Migration guide より、Scala 2 と Scala 3 の互換性・非互換性について、Source Level、Classpath Level、Runtime、Metaprogramming の観点で確認した後、Scala 3 へ移行するための前提条件や sbt のマイグレーション方法のチュートリアルを学びます。その後、移行をサポートするいくつかの便利なツール群を確認し、それぞれの非互換性に対してどのような移行方法があるかを整理します。

### Step5

Step1 では、序盤で見ておきたいものを抽出しましたが、ここまででまだ取り上げていない Scala 3 の新機能がいくつか残っています。具体的には、Imports、Export Clauses、Open Classes、Explicit Nulls ですが、このStepではまずそれらを見ていきたいと思います。そして最後に TASTy についても簡単に触れたいと思います。

## Part 2

### Step6

まずは、中盤で確認する Scala 3 の機能について、改めて Reference を俯瞰して決めたいと思います。その後、Scala 2 の implicit の再定義である、Contextual Abstractions に触れたいと思います。（仮）

### Step7

:construction: 考え中 :construction: