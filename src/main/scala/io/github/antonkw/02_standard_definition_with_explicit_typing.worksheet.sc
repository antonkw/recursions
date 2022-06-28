//Standard definition of factorial with explicit typing:
def factorial: Int => Int =
  (x: Int) => if (x == 0) 1 else x * factorial(x - 1)