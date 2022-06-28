//Demonstration of stack-safe factorial implementation.
//FlatMap[F].tailRecM and explicit state class are used.

import cats.implicits.catsSyntaxEitherId
import cats.{ FlatMap, Id }

/** @param number
  *   value that fixes
  * @param factorial
  */
case class Factorials(number: Int, factorial: BigInt, factorials: Map[Int, BigInt]) {

  /** Generates state for next iteration (in terms of algorithm that increases a value on each step)
    * @return
    *   updated state
    */
  def nextFactorial: Factorials = {
    val incremented: Int   = this.number + 1
    val calculated: BigInt = this.factorial * incremented
    println(s"Factorial for $incremented is $calculated")
    this.copy(
      number = incremented,
      factorial = calculated,
      this.factorials + (incremented -> calculated)
    )
  }
}

object Factorials {
  val initial: Factorials = Factorials(0, 1, Map(0 -> 1))
}

def factorialsUntil(x: Int): Map[Int, BigInt] =
  FlatMap[Id].tailRecM[Factorials, Map[Int, BigInt]](Factorials.initial)(state =>
    if (state.number == x) state.factorials.asRight else state.nextFactorial.asLeft
  )

val result = factorialsUntil(10)(10)
println(result) //3628800
