# Scala 3 Migration guide を俯瞰してみる {ignore=true}


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [概要](#概要)
- [General](#general)
- [Tooling](#tooling)
- [Tutorials](#tutorials)
- [Incompatibilities](#incompatibilities)
- [Macros](#macros)
- [External Resources](#external-resources)

<!-- /code_chunk_output -->

## 概要

Scala3 のマイグレーションや Scala2 系との互換性を確認するために、[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide/) の内容も俯瞰してみましょう。こちらも情報量が多いので、見るものを選別します。

まず、Migration guide はこのようなカテゴリに分かれています。

```plantuml
@startmindmap
* Migration guide
** General
** Tooling
** Tutorials
** Incompatibilities
** Macros
** External Resources
@endmindmap
```

## General

https://scalacenter.github.io/scala-3-migration-guide/docs/compatibility.html

```plantuml
@startmindmap
* General
**[#38c0c4] Compatibility Reference
**[#38c0c4] Incompatibility Table
** Metaprogramming
@endmindmap
```

## Tooling

```plantuml
@startmindmap
* General
**[#38c0c4] Tour of the Migration Tools
**[#38c0c4] Scala 3 Migration Mode
**[#38c0c4] Scala 3 Migrate Plugin
**[#38c0c4] Scala 3 Syntax Rewriting
@endmindmap
```

## Tutorials

https://scalacenter.github.io/scala-3-migration-guide/docs/tutorials/sbt-migration.html

```plantuml
@startmindmap
* Tutorials
**[#38c0c4] Porting an sbt Project
**[#38c0c4] Porting the scalacOptions
@endmindmap
```

## Incompatibilities

https://scalacenter.github.io/scala-3-migration-guide/docs/incompatibilities/table.html

```plantuml
@startmindmap
* Incompatibilities
**[#38c0c4] Syntactic Changes
**[#38c0c4] Dropped Features
** Contextual Abstractions
**[#38c0c4] Other Changed Features
@endmindmap
```

## Macros

https://scalacenter.github.io/scala-3-migration-guide/docs/macros/macro-libraries.html

必要に応じて見れば良いので、今回は除外します。

```plantuml
@startmindmap
* Macros
** Scala Macro Libraries
** Porting a Macro Library
@endmindmap
```

## External Resources

https://scalacenter.github.io/scala-3-migration-guide/docs/external-resources/moving-from-scala-2-to-scala-3.html

リンク先の [Moving Forward from Scala 2 to Scala 3](https://github.com/lunatech-labs/lunatech-scala-2-to-scala3-course) が更新されてないので除外します。

```plantuml
@startmindmap
* External Resources
** Moving from Scala 2 to Scala 3
@endmindmap
```