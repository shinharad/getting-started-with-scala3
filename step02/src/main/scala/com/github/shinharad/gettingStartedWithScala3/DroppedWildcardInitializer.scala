package com.github.shinharad.gettingStartedWithScala3
package droppedWildcardInitializer

// Scala2 ではこう書いていたが、
// var x: String = _

// Scala3 ではこう書く
import scala.compiletime.uninitialized
var x: String = uninitialized
