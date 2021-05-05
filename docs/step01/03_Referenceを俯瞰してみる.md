# Referenceを俯瞰してみる {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [New Types](#new-types)
- [Enums](#enums)
- [Contextual Abstractions](#contextual-abstractions)
- [Metaprogramming](#metaprogramming)
- [Other New Features](#other-new-features)
- [Other Changed Features](#other-changed-features)
- [Dropped Features](#dropped-features)

<!-- /code_chunk_output -->

## 概要

Scala 3 の新しい仕様を確認していくのは、やはり [Reference](https://dotty.epfl.ch/docs/reference/overview.html) が良さそうです。
ただ、結構なページ数があるのと、難易度がバラバラなので、上から順番に見ていくのはしんどいかもしれません。
そこで、まずはこの Part で見ておきたいものを抽出し、それらを順番に見ていきたいと思います。

まず、Reference はこのようなカテゴリに分かれています。

```plantuml
@startmindmap
* Reference
** New Types
** Enums
** Contextual Abstractions
** Metaprogramming
** Other New Features
** Other Changed Features
** Dropped Features
@endmindmap
```

それぞれのカテゴリには、様々な言語仕様についてのページがありますが、
カテゴリごとにこの Part で見ておきたいページに色付けしてみましょう。


## New Types

https://dotty.epfl.ch/docs/New%20Types/

```plantuml
@startmindmap
*[#fff] New Types
**[#38c0c4] Intersection Types
**[#38c0c4] Union Types
**[#fff] Type Lambdas
**[#fff] Match Types
**[#fff] Dependent Function Types
**[#fff] Polymorphic Function Types
@endmindmap
```

## Enums

https://dotty.epfl.ch/docs/Enums/

```plantuml
@startmindmap
*[#fff] Enums
**[#38c0c4] Enumerations
**[#38c0c4] Algebraic Data Types
**[#fff] Translation of Enums and ADTs
@endmindmap
```

## Contextual Abstractions

https://dotty.epfl.ch/docs/Contextual%20Abstractions/

Scala 2 では様々な役割を持っていた `implicit` が、Scala 3 では機能毎に書き方が分かれました。
implicit parameter や型クラスの新しい書き方などは今後見るとして、最初は `Extension Methods` を見ていきましょう。

```plantuml
@startmindmap
*[#fff] Contextual Abstractions
**[#fff] Overview
**[#fff] Given Instances
**[#fff] Using Clauses
**[#fff] Context Bounds
**[#fff] Importing Givens
**[#38c0c4] Extension Methods
**[#fff] Implementing Type classes
**[#fff] Type Class Derivation
**[#fff] Multiversal Equality
**[#fff] Context Functions
**[#fff] Implicit Conversions
**[#fff] By-Name Context Parameters
**[#fff] Relationship with Scala 2 Implicits
@endmindmap
```

## Metaprogramming

https://dotty.epfl.ch/docs/Metaprogramming/

Metaprogramming は、必要に応じて見れば良いので、今回は除外します。

```plantuml
@startmindmap
*[#999] Metaprogramming
**[#999] Inline
**[#999] Macros
**[#999] Runtime Multi-Stage Programming
**[#999] Reflection
**[#999] TASTy Inspection
@endmindmap
```

## Other New Features

https://dotty.epfl.ch/docs/Other%20New%20Features/

```plantuml
@startmindmap
*[#fff] Other New Features
**[#38c0c4] Trait Parameters
**[#fff] Transparent Traits
**[#38c0c4] Universal Apply Methods
**[#38c0c4] Export Clauses
**[#38c0c4] Opaque Type Aliases
**[#38c0c4] Open Classes
**[#38c0c4] Parameter Untupling
**[#fff] Kind Polymorphism
**[#fff] The Matchable Trait
**[#fff] The @threadUnsafe annotation
**[#fff] The @targetName annotation
**[#38c0c4] New Control Syntax
**[#38c0c4] Optional Braces
**[#38c0c4] Explicit Nulls
**[#fff] Safe Initialization
**[#fff] TypeTest
@endmindmap
```

## Other Changed Features

https://dotty.epfl.ch/docs/Other%20Changed%20Features/

```plantuml
@startmindmap
*[#fff] Other Changed Features
**[#fff] Numeric Literals
**[#fff] Programmatic Structural Types
**[#38c0c4] Rules for Operators
**[#fff] Wildcard Arguments in Types
**[#38c0c4] Imports
**[#fff] Changes in Type Checking
**[#fff] Changes in Type Inference
**[#fff] Changes in Implicit Resolution
**[#fff] Implicit Conversions
**[#38c0c4] Changes in Overload Resolution
**[#38c0c4] Match Expressions
**[#38c0c4] Vararg Splices
**[#38c0c4] Pattern Bindings
**[#fff] Option-less pattern matching
**[#fff] Automatic Eta Expansion
**[#fff] Changes in Compiler Plugins
**[#fff] Lazy Vals initialization
**[#38c0c4] Main Methods
@endmindmap
```

## Dropped Features

https://dotty.epfl.ch/docs/Dropped%20Features/

```plantuml
@startmindmap
*[#fff] Dropped Features
**[#38c0c4] Dropped: Delayedinit
**[#38c0c4] Dropped: Scala 2 Macros
**[#fff] Dropped: Existential Types
**[#fff] Dropped: General Type Projection
**[#38c0c4] Dropped: Do-While
**[#38c0c4] Dropped: Procedure Syntax
**[#38c0c4] Dropped: Package Objects
**[#38c0c4] Dropped: Early Initializers
**[#38c0c4] Dropped: Class Shadowing
**[#38c0c4] Dropped: Limit 22
**[#38c0c4] Dropped: XML Literals
**[#38c0c4] Dropped: Symbol Literals
**[#38c0c4] Dropped: Auto-Application
**[#38c0c4] Dropped: Weak Conformance
**[#38c0c4] Deprecated: Nonlocal Returns
**[#38c0c4] Dropped: private[this] and protected[this]
**[#38c0c4] Dropped: wildcard initializer
@endmindmap
```
