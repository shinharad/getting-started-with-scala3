# [WIP] getting-started-with-scala3

**:construction: このリポジトリは作成中です :construction:**

## 概要

このリポジトリは、Scala 3 の公式ドキュメントを散策しながら Scala 3 への理解を少しずつ深めていくことを目的としています。

基本的に、自分が誰かに Scala 3 の良さを伝えたり、議論の材料として利用するために作成しています。

## 今後の更新について

Scala 3 が正式リリースされたこともあり、[Documentation for Scala 3](https://docs.scala-lang.org/scala3) の整備が進んでいます。一応、このリポジトリは細々と更新は続けますが、今後はそちらを見るのが良さそうです。

以前は Scala 3 のドキュメントが、下記のような状況だったため、それらをガイドする位置づけとしてこのリポジトリを作成していました。

- [Documentation for Scala 3](https://docs.scala-lang.org/scala3) は整備されていないページが多かった
- [Documentation for Scala 3](https://docs.scala-lang.org/scala3) と [Scala 3 の公式サイト](https://dotty.epfl.ch)、[Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide) は、相互にリンクが張られていたものの、それぞれが独立して更新されていたため、内容の重複が多かった

しかし、今はこのように整備が進んでいるため、[Documentation for Scala 3](https://docs.scala-lang.org/scala3) がドキュメントの入り口として良さそうです。

- [Documentation for Scala 3](https://docs.scala-lang.org/scala3) の整備が進んでいる
- [Scala 3 Migration guide](https://scalacenter.github.io/scala-3-migration-guide) が [Documentation for Scala 3](https://docs.scala-lang.org/scala3) に統合された

このリポジトリとしては、Scala 3 の各機能について、日本語で要約していることと、サンプルコードを手元の環境ですぐに試すことができるという点では、まだまだ利用価値がありそうなので、細々と更新を続けます。

## 対象者

- Scala 2 には触れたことがあるけど、Scala 3 はこれからという方

## Scalaのバージョン

`3.0.1` を対象としています。

## 環境

このリポジトリは、以下の環境で作成しています。

- JDK
  - [Eclipse Adoptium (AdoptOpenJDK) 8](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
- [sbt](https://www.scala-sbt.org/download.html)
- [Visual Studio Code](https://azure.microsoft.com/ja-jp/products/visual-studio-code/)
  - [Scala (Metals)](https://marketplace.visualstudio.com/items?itemName=scalameta.metals)
  - [Markdown Preview Enhanced](https://marketplace.visualstudio.com/items?itemName=shd101wyy.markdown-preview-enhanced)
- [Graphviz](https://www.graphviz.org/) : PlantUML の描画用に

ドキュメントは、Markdown 内に PlantUML を書いてるので、[Markdown Preview Enhanced](https://shd101wyy.github.io/markdown-preview-enhanced/#/) で閲覧することをおすすめします。（Markdown から HTML の変換は追々やります）

## 公式ドキュメントの散策

[こちらへ](docs)
