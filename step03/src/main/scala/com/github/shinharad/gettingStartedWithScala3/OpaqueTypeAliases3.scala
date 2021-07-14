package com.github.shinharad.gettingStartedWithScala3
package opaqueTypeAliases3

//---
// Domain Modeling Made Functional - Chapter 4 Understanding Types の
// Building a Domain Model by Composing Types を Scala 3で表現すると

object Purchase:

  // スコープの外からは実体が隠されている
  opaque type CheckNumber = Int
  object CheckNumber:
    def apply(n: Int): CheckNumber = n
  
  // もしもスコープの外からアクセスしたい場合は、extension methods で公開する
  extension (a: CheckNumber)
    def hoge: Unit = ???

  opaque type CardNumber = String
  object CardNumber:
    def apply(n: String): CardNumber = n

  opaque type PaymentAmount = Float
  object PaymentAmount:
    def apply(amount: Float): PaymentAmount = amount

  enum CardType:
    case Visa, Mastercard

  enum Currency:
    case EUR, USD

  final case class CreditCardInfo(cardType: CardType, cardNumber: CardNumber)

  enum PaymentMethod:
    case Cash
    case Check(checkNumber: CheckNumber)
    case Card(creditCardInfo: CreditCardInfo)

  final case class Payment(
    amount: PaymentAmount,
    currency: Currency,
    method: PaymentMethod
  )

def no1(): Unit =
  import Purchase.*

  Payment(
    PaymentAmount(10.0f),
    Currency.EUR,
    PaymentMethod.Cash)

  Payment(
    PaymentAmount(350),
    Currency.USD,
    PaymentMethod.Card(CreditCardInfo(CardType.Visa, CardNumber("1111111111111111"))))

  // 外のスコープからは公開されたメソッドにしかアクセスできない
  CheckNumber(1234).hoge

  // 公開されていないメソッドを呼び出すことはできない（実体である Int の toDouble とか）
  // CheckNumber(1234).toDouble
