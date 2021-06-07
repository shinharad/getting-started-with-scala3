# Migration Guide を俯瞰してみる {ignore=true}

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [Compatibility Reference](#compatibility-reference)
- [Tour of the Migration Tools](#tour-of-the-migration-tools)
- [Scala 3 Migration Mode](#scala-3-migration-mode)
- [Migration Tutorial](#migration-tutorial)
- [Scala 3 Syntax Rewriting](#scala-3-syntax-rewriting)
- [Incompatibility Table](#incompatibility-table)
- [Compiler Options](#compiler-options)
- [Compiler Plugins](#compiler-plugins)
- [Resources](#resources)

<!-- /code_chunk_output -->

## 概要

Scala 3 へのマイグレーション方法や Scala 2 との互換性・非互換性を確認するために、[Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html) の内容も俯瞰してみましょう。こちらも情報量が多いので、見るものを選別します。

まず、Migration Guide はこのようなカテゴリに分かれています。

```plantuml
@startmindmap
* Migration guide
** Compatibility Reference
** Tour of the Migration Tools
** Scala 3 Migration Mode
** Migration Tutorial
** Scala 3 Syntax Rewriting
** Incompatibility Table
** Compiler Options
** Compiler Plugins
@endmindmap
```

この内、カテゴリごとにこの Part で見ておきたいページに色付けしてみましょう。

## Compatibility Reference

https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html

```plantuml
@startmindmap
*[#fff] Compatibility Reference
**[#38c0c4] Source Level
**[#38c0c4] Classpath Level
**[#38c0c4] Runtime
**[#38c0c4] Metaprogramming
@endmindmap
```

## Tour of the Migration Tools

https://docs.scala-lang.org/scala3/guides/migration/tooling-tour.html


```plantuml
@startmindmap
*[#38c0c4] Tour of the Migration Tools
@endmindmap
```

## Scala 3 Migration Mode

https://docs.scala-lang.org/scala3/guides/migration/tooling-migration-mode.html


```plantuml
@startmindmap
*[#38c0c4] Scala 3 Migration Mode
@endmindmap
```

## Migration Tutorial

https://docs.scala-lang.org/scala3/guides/migration/tutorial-intro.html


```plantuml
@startmindmap
*[#fff] Migration Tutorial
**[#38c0c4] Project Prerequisites
**[#38c0c4] Porting an sbt Project
**[#fff] Cross-Building a Macro Library
**[#fff] Mixing Scala 2.13 and Scala 3 Macros
@endmindmap
```

## Scala 3 Syntax Rewriting

https://docs.scala-lang.org/scala3/guides/migration/tooling-syntax-rewriting.html

```plantuml
@startmindmap
*[#38c0c4] Scala 3 Syntax Rewriting
@endmindmap
```

## Incompatibility Table

https://docs.scala-lang.org/scala3/guides/migration/incompatibility-table.html

```plantuml
@startmindmap
*[#fff] Incompatibility Table
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

https://docs.scala-lang.org/scala3/guides/migration/options-intro.html


```plantuml
@startmindmap
*[#fff] Compiler Options
**[#38c0c4] Compiler Options Lookup Table
**[#38c0c4] New Compiler Options
**[#38c0c4] Scaladoc settings compatibility between Scala2 and Scala3
@endmindmap
```

## Compiler Plugins

https://docs.scala-lang.org/scala3/guides/migration/plugin-intro.html

```plantuml
@startmindmap
*[#fff] Compiler Plugins
**[#fff] External Resources
**[#fff] Kind Projector Migration
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