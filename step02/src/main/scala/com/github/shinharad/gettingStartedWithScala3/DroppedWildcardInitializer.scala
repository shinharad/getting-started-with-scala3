package com.github.shinharad.gettingStartedWithScala3
package droppedWildcardInitializer

// Scala 2 ではこう書いていたが、
// var x: String = _

// Scala 3 ではこう書く
import scala.compiletime.uninitialized
var x: String = uninitialized
