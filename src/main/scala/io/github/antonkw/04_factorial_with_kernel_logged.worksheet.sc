def factorialWithKernel(kernel: Int => Int): Int => Int =
  n => {
    if (n == 0) {
      println("returning 1!")
      1
    } else {
      val kernelResult = kernel(n - 1)
      val finalResult = n * kernelResult
      println(s"n[$n] * kernel($n - 1)[$kernelResult] = $finalResult")
      finalResult
    }
  }

val `factorialWithKernel(identity)` = factorialWithKernel(identity)

val `factorialWithKernel(42)`: Int => Int =
  factorialWithKernel(_ => 42)

val result0With42Kernel = `factorialWithKernel(42)` apply 0
/*
returning 1!
val result0With42Kernel: Int = 1
 */

println(s"Result for 0 applied to factorialWithKernel(42): $result0With42Kernel")
//Result for 0 applied to factorialWithKernel(42): 1


val result1With42Kernel =
  `factorialWithKernel(42)` apply 1
/*
n[1] * kernel(1 - 1)[42] = 42
val result1With42Kernel: Int = 42
 */

println(s"Result for 1 applied to factorialWithKernel(42): $result1With42Kernel")
//Result for 1 applied to factorialWithKernel(42): 42


val factorialWithDerivedKernel =
  factorialWithKernel(`factorialWithKernel(42)`)

factorialWithDerivedKernel apply 1
/*
returning 1!
n[1] * kernel(1 - 1)[1] = 1
val res2: Int = 1
 */