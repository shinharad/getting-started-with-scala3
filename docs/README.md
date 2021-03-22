# Overview

Scala3 のドキュメントの散策は、このような流れで進めたいと思います。

### Step1

最初に Scala3 を書くための環境構築をします。次に、Scala3 の公式ドキュメントを俯瞰し、最後にインデントベースのシンタックスと新しい制御構造について触れます。

### Step2

前半は Scala3 で追加された「地味に嬉しい系の機能追加」について、後半は Scala3 で廃止または非推奨になった機能について取り上げていきます。

### Step3

Enums や Algebraic Data Types (ADTs) で新しいシンタックスが追加されたことで、今までの冗長な記述が簡潔に書けるようになったことや、新しく追加された Intersection Types、Union Types、Opaque Type Aliases で型の表現がより豊かになったことで、設計の幅が広がることを確認します。

### Step4

Step4 では、主に Scala2 と Scala3 の互換性・非互換性について取り上げます。ソースコードレベルやコンパイルタイム、ランタイム、メタプログラミング、モジュール間でどのような互換性・非互換性があるかを確認し、非互換性に対してどのようなツールを使用して Scala3 へ移行するのかを確認します。

### Step5

**:construction: たぶん分割します :construction:**

Step1 では、序盤で見ておきたいものを抽出しましたが、まだ取り上げていないものがいくつか残っています。この Step では、今まで取り上げていない残りの部分について見ていきたいと思います。具体的には、Reference からは Imports、Export Clauses、Open Classes、Explicit Nulls。Migration Guide からは、Porting an sbt Project、Porting the scalacOptions。その他、TASTy についても触れたいと思います。（仮）

### Step6

（考え中）
