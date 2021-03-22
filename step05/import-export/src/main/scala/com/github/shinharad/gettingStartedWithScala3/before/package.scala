package com.github.shinharad.gettingStartedWithScala3

//---
// 移行前
// パッケージオブジェクトに共通的な Facade クラスを継承している

class Facade:
  def foo(): Int = ???

package object before extends Facade
