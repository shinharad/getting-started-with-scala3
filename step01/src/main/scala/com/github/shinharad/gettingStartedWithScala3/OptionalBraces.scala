package com.github.shinharad.gettingStartedWithScala3
package optionalBraces

// Indentation Rules

// def no1_ng =
//   val x = 10
//   if (x < 0) {
//     println(1)
//     println(2)

//   println("done")  // error: indented too far to the left

def no1_ok(): Unit =
  val x = 10
  if (x < 0) {
    println(1)
    println(2)
  }
  println("done")

// Optional Braces

// def no2_ng =
//   val x = 10
//   if x < 0 then
//       -x
//     else   // error: `else` does not align correctly
//       x

def no2_ok: Int =
  val x = 10
  if x < 0 then
     -x
  else
     x

// Optional Braces Around Template Bodies

trait A:
  def f: Int

class C(x: Int) extends A:
  def f = x

object O:
  def f = 3

enum Color:
  case Red, Green, Blue

def no3 =
  new A:
    def f = 3

package p:
  def a = 1

package q:
  def b = 2

// Indentation and Braces

def no4(): Unit =
  def f(x: Int, y: Int => Int): Int = ???
  val x = 10

  // インデントは中括弧 {...} だけでなく、括弧 [....] や括弧 (....) も自由に混在させることができる
  { 
    val z = f(x: Int, y =>
      x * (
        y + 1
      ) +
      (x +
      x)
    )
  }

// Special Treatment of Case Clauses

def no5(): Unit =
  val x = 1

  x match
    case 1 => print("I")
    case 2 => print("II")
    case 3 => print("III")
    case 4 => print("IV")
    case 5 => print("V")

  println(".")

// The End Marker

package p1.p2:

  abstract class C():

    def this(x: Int) =
      this()
      if x > 0 then
        val a :: b =
          x :: Nil
        end val
        var y =
          x
        end y
        while y > 0 do
          println(y)
          y -= 1
        end while
        try
          x match
            case 0 => println("0")
            case _ =>
          end match
        finally
          println("done")
        end try
      end if
    end this

    def f: String
  end C

  object C:
    given C =
      new C:
        def f = "!"
        end f
      end new
    end given
  end C

  extension (x: C)
    def ff: String = x.f ++ x.f
  end extension

end p2

// Example

enum IndentWidth:
  case Run(ch: Char, n: Int)
  case Conc(l: IndentWidth, r: Run)

  def <= (that: IndentWidth): Boolean = this match
    case Run(ch1, n1) =>
      that match
        case Run(ch2, n2) => n1 <= n2 && (ch1 == ch2 || n1 == 0)
        case Conc(l, r)   => this <= l
    case Conc(l1, r1) =>
      that match
        case Conc(l2, r2) => l1 == l2 && r1 <= r2
        case _            => false

  def < (that: IndentWidth): Boolean =
    this <= that && !(that <= this)

  override def toString: String =
    this match
    case Run(ch, n) =>
      val kind = ch match
        case ' '  => "space"
        case '\t' => "tab"
        case _    => s"'$ch'-character"
      val suffix = if n == 1 then "" else "s"
      s"$n $kind$suffix"
    case Conc(l, r) =>
      s"$l, $r"

object IndentWidth:
  private inline val MaxCached = 40

  private val spaces = IArray.tabulate(MaxCached + 1)(new Run(' ', _))
  private val tabs = IArray.tabulate(MaxCached + 1)(new Run('\t', _))

  def Run(ch: Char, n: Int): Run =
    if n <= MaxCached && ch == ' ' then
      spaces(n)
    else if n <= MaxCached && ch == '\t' then
      tabs(n)
    else
      new Run(ch, n)
  end Run

  val Zero = Run(' ', 0)
end IndentWidth
