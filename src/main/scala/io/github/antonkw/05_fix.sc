def factorialWithKernel(kernel: Int => Int): Int => Int =
  n =>
    if (n == 0) {
      println("returning 1!")
      1
    } else {
      val kernelResult = kernel(n - 1)
      val finalResult  = n * kernelResult
      println(s"n[$n] * kernel($n - 1)[$kernelResult] = $finalResult")
      finalResult
    }

val `factorialWithKernel(identity)` = factorialWithKernel(identity)

val `factorialWithKernel(42)` : Int => Int = factorialWithKernel(_ => 42)

//our function takes kernel of Int => Int type and returns Int => Int function
//so, the signature is (Int => Int) => (Int => Int)
//and we want to still return function to calculate factorial, Int => Int

def fix01[A](f: (A => A) => (A => A)): A => A = ??? //just types

def fix02[A](f: (A => A) => (A => A)): A => A = {
  val calculated = ??? //we need to calculate something, and return, easy step
  calculated
}

def fix03[A](f: (A => A) => (A => A)): A => A = {
  val calculated =
    f(
      ???
    ) //no guarantee, but gut feeling says that we should return result of our recursive function (which is passed as an argument)
  calculated
}

//  def fix04[A](f: (A => A) => (A => A)): A => A = {
//    val calculated = f(calculated) //as previously, we just passing what we calculated at previous step
//    calculated
//  }

//  def fix05[A](f: (A => A) => (A => A)): A => A = {
//    val calculated: A => A = f(calculated) //recursive value needs type!
//    calculated
//  }
//forward reference to value calculated defined on line 59 extends over definition of value calculated


def fix06[A](f: (A => A) => (A => A)): A => A = {
  lazy val calculated: A => A = f(calculated) //recursive value needs type!
  calculated
}

//fix06(factorialWithKernel)

//Exception in thread "main" java.lang.StackOverflowError
//and that exception requires more attention


//problem here is that there is no exit from evaluation
//I specifically don't want to refer to internals of factorial
//we have parameter of F passed as call-by-value parameter
//what does it mean?
//right! we need to calculate it first, do we have a chance to calculate it?
//that's a problem with kernel argument, we want to pass a function. instead, we're saying to compiler "we need to calculate a value and pass it"
//we're ending with f(calculated) = f(f(f(f()))) because of attempt to calculate "calculated" value before passing the to the f
// What we actually unconsciously imply is that we're passing kernel which will be evaluated later, so we will to iteratively reach point of return
//kernel: => (Int => Int)
def fix07[A](f: (=> (A => A)) => (A => A)) = {
  lazy val calculated: A => A = f(calculated) //recursive value needs type!
  calculated
}

def factorialWithByNameKernel(kernel: => (Int => Int)): Int => Int =
  n =>
    if (n == 0) {
      println("returning 1!")
      1
    } else {
      val kernelResult = kernel(n - 1)
      val finalResult  = n * kernelResult
      println(s"n[$n] * kernel($n - 1)[$kernelResult] = $finalResult")
      finalResult
    }

//so, we ended up with solid signatures

val fixedFactorial: Int => Int = fix07[Int](factorialWithByNameKernel)

println(s"Let's apply 11 to fixedFactorial")
println(s"Result for 11 applied to fixedFactorial: ${fixedFactorial apply 11}")
println("")

/*

Let's apply 11 to fixedFactorial
returning 1!
n[1] * kernel(1 - 1)[1] = 1
n[2] * kernel(2 - 1)[1] = 2
n[3] * kernel(3 - 1)[2] = 6
n[4] * kernel(4 - 1)[6] = 24
n[5] * kernel(5 - 1)[24] = 120
n[6] * kernel(6 - 1)[120] = 720
n[7] * kernel(7 - 1)[720] = 5040
n[8] * kernel(8 - 1)[5040] = 40320
n[9] * kernel(9 - 1)[40320] = 362880
n[10] * kernel(10 - 1)[362880] = 3628800
n[11] * kernel(11 - 1)[3628800] = 39916800
Result for 11 applied to fixedFactorial: 39916800
 */

def fix08[A](f: (=> A) => A): A = {
  lazy val calculated: A = f(calculated) //recursive value needs type!
  calculated
}


