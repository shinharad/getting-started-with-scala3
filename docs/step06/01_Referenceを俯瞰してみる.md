# Referenceを俯瞰してみる {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [New Types](#new-types)
- [Enums](#enums)
- [Contextual Abstractions](#contextual-abstractions)
- [Other New Features](#other-new-features)
- [Metaprogramming](#metaprogramming)
- [Other Changed Features](#other-changed-features)
- [Dropped Features](#dropped-features)

<!-- /code_chunk_output -->

## 概要

ここまでで、[Reference](https://dotty.epfl.ch/docs/reference/overview.html) や [Migration guide](https://scalacenter.github.io/scala-3-migration-guide) などから、Part 1 で確認しておきたいものは一通り終えました。ここからは Part 2 ということで、Scala 3 の更に深い部分について触れていきたいと思います。

それでは、改めて [Reference](https://dotty.epfl.ch/docs/reference/overview.html) を俯瞰し、次に見ておきたいページを抽出したいと思います。

なお、ここまでで確認済みのページに関しては除外してあります。

## New Types

https://dotty.epfl.ch/docs/New%20Types/

型クラスの実装で必要となる Type Lambdas を確認します。

```plantuml
@startmindmap
*[#fff] New Types
**[#38c0c4] Type Lambdas
**[#fff] Match Types
**[#fff] Dependent Function Types
**[#fff] Polymorphic Function Types
@endmindmap
```

## Enums

https://dotty.epfl.ch/docs/Enums/

:construction: ここは敢えて今見なくても良いとも思うので、除外するかも :construction:

```plantuml
@startmindmap
*[#fff] Enums
**[#fff] Translation of Enums and ADTs
@endmindmap
```

## Contextual Abstractions

https://dotty.epfl.ch/docs/Contextual%20Abstractions/

Extension Methods は Part 1 で確認したので、それ以外をやりましょう！

```plantuml
@startmindmap
*[#fff] Contextual Abstractions
**[#38c0c4] Overview
**[#38c0c4] Given Instances
**[#38c0c4] Using Clauses
**[#38c0c4] Context Bounds
**[#38c0c4] Importing Givens
**[#38c0c4] Implementing Type classes
**[#38c0c4] Type Class Derivation
**[#38c0c4] Multiversal Equality
**[#38c0c4] Context Functions
**[#38c0c4] Implicit Conversions
**[#38c0c4] By-Name Context Parameters
**[#38c0c4] Relationship with Scala 2 Implicits
@endmindmap
```

## Other New Features

https://dotty.epfl.ch/docs/Other%20New%20Features/

`The @targetName annotation` は Part 1 で軽く触れたので、今回はこれ以上深堀りしなくても良さそう。

:construction: どこまで中盤でやるか考え中 :construction:

```plantuml
@startmindmap
*[#fff] Other New Features
**[#38c0c4] Transparent Traits
**[#fff] Kind Polymorphism
**[#38c0c4] The Matchable Trait
**[#38c0c4] The @threadUnsafe annotation
**[#999] The @targetName annotation
**[#38c0c4] Safe Initialization
**[#38c0c4] TypeTest
@endmindmap
```

## Metaprogramming

https://dotty.epfl.ch/docs/Metaprogramming/

Metaprogramming は、必要に応じて見れば良いので、今回は除外します。

## Other Changed Features

https://dotty.epfl.ch/docs/Other%20Changed%20Features/

```plantuml
@startmindmap
*[#fff] Other Changed Features
**[#38c0c4] Numeric Literals
**[#38c0c4] Programmatic Structural Types
**[#fff] Wildcard Arguments in Types
**[#fff] Changes in Type Checking
**[#fff] Changes in Type Inference
**[#38c0c4] Changes in Implicit Resolution
**[#38c0c4] Implicit Conversions
**[#38c0c4] Option-less pattern matching
**[#38c0c4] Automatic Eta Expansion
**[#38c0c4] Changes in Compiler Plugins
**[#38c0c4] Lazy Vals initialization
@endmindmap
```

## Dropped Features

https://dotty.epfl.ch/docs/Dropped%20Features/

:construction: ここに触れるか考え中 :construction:

```plantuml
@startmindmap
*[#fff] Dropped Features
**[#fff] Dropped: Existential Types
**[#fff] Dropped: General Type Projection
@endmindmap
```
