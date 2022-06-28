import cats.{ Id, Monad }
import cats.implicits.catsSyntaxEitherId

import scala.annotation.tailrec

val nonTailRecFactorial: BigInt => BigInt =
  new (BigInt => BigInt) { kernel =>
    def apply(x: BigInt): BigInt = if (x == 0) 1 else x * kernel.apply(x - 1)
  }

val `nonTailRecFactorial 10000` = nonTailRecFactorial.apply(10000) //ok
//val `nonTailRecFactorial 20000` = nonTailRecFactorial.apply(20000) //overflow

println(s"non tail for 10000 ${`nonTailRecFactorial 10000`}")

case class FactorialStepState(current: BigInt, accumulator: BigInt) {

  /** Generates state for next iteration (in terms of algorithm that decreases a value on each step)
    * @return
    *   updated state
    */
  def multiplyAndDecrement: FactorialStepState =
    this.copy(current = this.current - 1, accumulator = this.current * this.accumulator)
}
object FactorialStepState {
  val initial: BigInt => FactorialStepState = x => FactorialStepState(x, 1)
}

def factorial(x: Int): BigInt = {
  @tailrec
  def factorialIteration(current: Int, accumulator: BigInt): BigInt =
    if (current == 0) accumulator
    else factorialIteration(current - 1, current * accumulator)

  factorialIteration(x, accumulator = 1)
}

factorial(3)
