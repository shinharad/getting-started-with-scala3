package com.github.shinharad.gettingStartedWithScala3
package openClass

trait Encryptable[T]:
  def encrypt(x: T): T = ???

//---
// open class を継承してるのでOK

class EncryptedWriter1[T: Encryptable] extends WriterOpen[T]:
  override def send(x: T) =
    super.send(summon[Encryptable[T]].encrypt(x))

//---
// open ではない class を継承してる場合は警告メッセージ
// （コメントアウトを外すと警告メッセージが表示される）

// class EncryptedWriter2[T: Encryptable] extends Writer[T]:
//   override def send(x: T) =
//     super.send(summon[Encryptable[T]].encrypt(x))

//---
// `scala.language.adhocExtensions` を import すると
// アドホックに継承できる（テストダブルや一時的なパッチ適用など、ご利用は計画的に）

import scala.language.adhocExtensions

class EncryptedWriter3[T: Encryptable] extends Writer[T]:
  override def send(x: T) =
    super.send(summon[Encryptable[T]].encrypt(x))
