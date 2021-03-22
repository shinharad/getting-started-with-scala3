package com.github.shinharad.gettingStartedWithScala3
package after

import com.github.shinharad.gettingStartedWithScala3.before

//---
// 移行後
// パッケージオブジェクトが継承していた Facade クラスを export 句でトップレベルに定義する

val facade: Facade = new Facade

export facade.foo
