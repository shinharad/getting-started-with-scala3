package com.github.shinharad.gettingStartedWithScala3
package openClass

// open を明示してるので、別のファイルで継承可能
open class WriterOpen[T]:
  def send(x: T) = println(x)
  def sendAll(xs: T*) = xs.foreach(send)

// open を明示していないので、基本的に継承不可
class Writer[T]:
  def send(x: T) = println(x)
  def sendAll(xs: T*) = xs.foreach(send)
