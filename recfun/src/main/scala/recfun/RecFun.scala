package recfun
import scala.annotation.tailrec

object RecFun extends RecFunInterface {


  /**
   * Exercise 1
   *
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else if (c > r) 0
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   * Write a recursive function which verifies the balancing of parentheses in a string, which we represent as a List[Char] not a String
   */
  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def balance_helper(chars: List[Char], count: Int): Boolean = {

      if (chars.isEmpty) count == 0
      else if (chars.head == '(') balance_helper(chars.tail, count + 1)
      else if (chars.head == ')') balance_helper(chars.tail, count - 1)
      else balance_helper(chars.tail,count)
    }
    balance_helper(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def total_number_of_combinators(money: Int, coins: List[Int]) :Int = {
      if (money == 0) 1
      else if (money < 0 || coins.isEmpty) 0
      else total_number_of_combinators(money - coins.head, coins) + total_number_of_combinators(money, coins.tail)
    }
    total_number_of_combinators(money, coins)
  }


  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

}