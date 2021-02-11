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

Scala3 の新しい仕様を確認していくのは、やはり [Reference](https://dotty.epfl.ch/docs/reference/overview.html) が良さそうです。
ただ、結構なページ数があるのと、難易度がバラバラなので、上から順番に見ていくのはしんどいかもしれません。
そこで、まずは序盤で見ておきたいものを抽出し、それらを順番に見ていきたいと思います。

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
カテゴリごとに序盤で見ておきたいページに色付けしてみましょう。


## New Types

https://dotty.epfl.ch/docs/New%20Types/

```plantuml
@startmindmap
* New Types
**[#38c0c4] Intersection Types
**[#38c0c4] Union Types
** Type Lambdas
** Match Types
** Dependent Function Types
** Polymorphic Function Types
@endmindmap
```

## Enums

https://dotty.epfl.ch/docs/Enums/

```plantuml
@startmindmap
* Enums
**[#38c0c4] Enumerations
**[#38c0c4] Algebraic Data Types
**[#38c0c4] Translation of Enums and ADTs
@endmindmap
```

## Contextual Abstractions

https://dotty.epfl.ch/docs/Contextual%20Abstractions/

Scala2 系では様々な役割を持っていた `implicit` が、Scala3 では機能毎に書き方が分かれました。
implicit parameter や型クラスの新しい書き方などは今後見るとして、最初は `Extension Methods` を見ていきましょう。

```plantuml
@startmindmap
* Contextual Abstractions
** Given Instances
** Using Clauses
** Context Bounds
** Importing Givens
**[#38c0c4] Extension Methods
** Implementing Type classes
** Type Class Derivation
** Multiversal Equality
** Context Functions
** Implicit Conversions
** By-Name Context Parameters
** Relationship with Scala 2 Implicits
@endmindmap
```

## Metaprogramming

https://dotty.epfl.ch/docs/Metaprogramming/

Metaprogramming は、必要に応じて見れば良いので、今回は除外します。

```plantuml
@startmindmap
* Metaprogramming
** Inline
** Macros
** Runtime Multi-Stage Programming
** Reflection
** TASTy Inspection
@endmindmap
```

## Other New Features

https://dotty.epfl.ch/docs/Other%20New%20Features/

```plantuml
@startmindmap
* Other New Features
**[#38c0c4] Trait Parameters
** Transparent Traits
**[#38c0c4] Universal Apply Methods
**[#38c0c4] Export Clauses
**[#38c0c4] Opaque Type Aliases
**[#38c0c4] Open Classes
**[#38c0c4] Parameter Untupling
** Kind Polymorphism
** The Matchable Trait
** The @threadUnsafe annotation
** The @targetName annotation
**[#38c0c4] New Control Syntax
**[#38c0c4] Optional Braces
**[#38c0c4] Explicit Nulls
** Safe Initialization
@endmindmap
```

## Other Changed Features

https://dotty.epfl.ch/docs/Other%20Changed%20Features/

```plantuml
@startmindmap
* Other Changed Features
** Numeric Literals
** Programmatic Structural Types
**[#38c0c4] Rules for Operators
** Wildcard Arguments in Types
** Imports
** Changes in Type Checking
** Changes in Type Inference
** Changes in Implicit Resolution
** Implicit Conversions
** Changes in Overload Resolution
**[#38c0c4] Match Expressions
**[#38c0c4] Vararg Splices
** Pattern Bindings
** Option-less pattern matching
** Automatic Eta Expansion
** Changes in Compiler Plugins
** Lazy Vals initialization
**[#38c0c4] Main Methods
@endmindmap
```

## Dropped Features

https://dotty.epfl.ch/docs/Dropped%20Features/

```plantuml
@startmindmap
* Dropped Features
**[#38c0c4] Dropped: Delayedinit
** Dropped: Scala 2 Macros
** Dropped: Existential Types
** Dropped: General Type Projection
**[#38c0c4] Dropped: Do-While
**[#38c0c4] Dropped: Procedure Syntax
**[#38c0c4] Dropped: Package Objects
**[#38c0c4] Dropped: Early Initializers
**[#38c0c4] Dropped: Class Shadowing
**[#38c0c4] Dropped: Limit 22
**[#38c0c4] Dropped: XML Literals
**[#38c0c4] Dropped: Symbol Literals
**[#38c0c4] Dropped: Auto-Application
** Dropped: Weak Conformance
**[#38c0c4] Deprecated: Nonlocal Returns
**[#38c0c4] Dropped: private[this] and protected[this]
**[#38c0c4] Dropped: wildcard initializer
@endmindmap
```
