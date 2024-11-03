import scala.math.pow

def naive(x : Int) : Int =
  if(x > 0) then
    naive(x - 1) + naive(x - 1)
  else if (x == 0) then
    1
  else
    throw Exception("Invalid input: x < 0")

def naive_optimized_with_let(x : Int) : Int =
  if(x > 0) then
    2 * naive(x - 1)
  else if (x == 0) then
    1
  else
    throw Exception("Invalid input: x < 0")

def doubling_with_exp(x : Int) : Int =
  if(x >= 0) then
      1 << x
  else
    throw Exception("Invalid input: x < 0")
