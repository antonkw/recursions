//Demonstration of stack-safe factorial implementation.
//FlatMap[F].tailRecM and explicit state class are used.

import cats.implicits.catsSyntaxEitherId
import cats.{ FlatMap, Id }

/** @param current
  *   value that fixes
  * @param accumulator
  */
case class FactorialStepState(current: Int, accumulator: BigInt) {

  /** Generates state for next iteration (in terms of algorithm that decreases a value on each step)
    * @return
    *   updated state
    */
  def multiplyAndDecrement: FactorialStepState =
    this.copy(current = this.current - 1, accumulator = this.current * this.accumulator)
}

object FactorialStepState {
  val initial: Int => FactorialStepState = x => FactorialStepState(x, 1)
}

def factorial0(x: Int): BigInt =
  FlatMap[Id].tailRecM[FactorialStepState, BigInt](FactorialStepState.initial(x))(state =>
    if (state.current == 0) state.accumulator.asRight
    else
      FactorialStepState(
        current = state.current - 1,
        accumulator = state.current * state.accumulator
      ).asLeft
  )

/** @param x
  *   @see <a href="https://typelevel.org/cats/api/cats/FlatMap.html">cats/FlatMap</a>
  * @return
  */
def factorial(x: Int): BigInt =
  FlatMap[Id].tailRecM[FactorialStepState, BigInt](FactorialStepState.initial(x))(state =>
    if (state.current == 0) state.accumulator.asRight else state.multiplyAndDecrement.asLeft
  )

val result = factorial(20000)
println(result)
