# Scala 3 Migration guide を俯瞰してみる {ignore=true}


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [Compatibility Reference](#compatibility-reference)
- [Tooling](#tooling)
- [Tutorials](#tutorials)
- [Incompatibilities](#incompatibilities)
- [Compiler Options](#compiler-options)
- [Resources](#resources)

<!-- /code_chunk_output -->

## 概要

Scala 3 のマイグレーションや Scala 2 との互換性、非互換性を確認するために、[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) の内容も俯瞰してみましょう。こちらも情報量が多いので、見るものを選別します。

まず、Migration guide はこのようなカテゴリに分かれています。

```plantuml
@startmindmap
* Migration guide
** Compatibility Reference
** Tooling
** Tutorials
** Incompatibilities
** Compiler Options
** Resources
@endmindmap
```

## Compatibility Reference

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility/introduction.html

```plantuml
@startmindmap
*[#fff] Compatibility Reference
**[#fff] Introduction
**[#38c0c4] Source Level
**[#38c0c4] Classpath Level
**[#38c0c4] Runtime
**[#38c0c4] Metaprogramming
@endmindmap
```

## Tooling

https://scalacenter.github.io/scala-3-migration-guide/docs/tooling/migration-tools.html

```plantuml
@startmindmap
*[#fff] General
**[#38c0c4] Tour of the Migration Tools
**[#38c0c4] Scala 3 Migration Mode
**[#38c0c4] Scala 3 Migrate Plugin
**[#38c0c4] Scala 3 Syntax Rewriting
@endmindmap
```

## Tutorials

https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/prerequisites.html

```plantuml
@startmindmap
*[#fff] Tutorials
**[#38c0c4] Project Prerequisites
**[#38c0c4] sbt Migration Tutorial
**[#fff] Cross-Building a Macro Library
**[#fff] Mixing Scala 2.13 and Scala 3 Macros
@endmindmap
```

## Incompatibilities

https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/incompatibility-table.html

```plantuml
@startmindmap
*[#fff] Incompatibilities
**[#38c0c4] Incompatibility Table
**[#38c0c4] Syntactic Changes
**[#38c0c4] Dropped Features
**[#fff] Contextual Abstractions
**[#38c0c4] Other Changed Features
**[#fff] Type Checker
**[#fff] Type Inference
@endmindmap
```

## Compiler Options

https://scalacenter.github.io/scala-3-migration-guide/docs/compiler-options/compiler-options-table.html

```plantuml
@startmindmap
*[#fff] Compiler Options
**[#38c0c4] Compiler Options Table
**[#38c0c4] New Scala 3 compiler options
@endmindmap
```

## Resources

https://scalacenter.github.io/scala-3-migration-guide/docs/macros/macro-libraries.html

```plantuml
@startmindmap
*[#fff] Resources
**[#38c0c4] Scala Macro Libraries
**[#fff] Moving from Scala 2 to Scala 3
@endmindmap
```