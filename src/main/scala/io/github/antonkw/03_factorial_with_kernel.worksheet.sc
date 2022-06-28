def factorialWithKernel(kernel: Int => Int): Int => Int =
  (x: Int) => if (x == 0) 1 else x * kernel(x - 1)