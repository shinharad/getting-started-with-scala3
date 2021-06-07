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

Migration guide より、Scala 2.13 と Scala 3 の互換性について触れた後、Scala 3 へ移行するための前提条件や各種ツール、移行チュートリアルを確認します。そして最後に、Scala 2.13 と Scala 3 の非互換性についても確認します。

### Step5

Step1 では、この Part で見ておきたいものを抽出しましたが、ここまででまだ取り上げていない Scala 3 の新機能がいくつか残っています。具体的には、Imports、Export Clauses、Open Classes、Explicit Nulls ですが、このStepではまずそれらを見ていきたいと思います。そして最後に TASTy についても簡単に触れたいと思います。

## Part 2

### Step6

最初に Scala 3 の機能について改めて Reference を俯瞰し、Part 2 で確認するものを決めたいと思います。その後、Scala 2 の implicit の再設計である Contextual Abstractions の概要と4つの基本的な変更点を確認し、それらを使用した型クラスの実装例を見ていきます。

### Step7

前の Step では、Contextual Abstractions の前半部分を確認しました。この Step では、Contextual Abstractions の後半として、Type Class Derivation や Multiversal Equality などに触れ、最後に Scala 2 の implicit との関連性を確認します。（仮）

### Step8

:construction: 考え中 :construction: