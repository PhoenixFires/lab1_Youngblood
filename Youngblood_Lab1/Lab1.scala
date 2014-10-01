object Lab1 extends jsy.util.JsyApplication {
  import jsy.lab1.ast._
  import jsy.lab1.Parser
  
  /*
   * CSCI 3155: Lab 1
   * Catherine Youngblood
   * 
   * Partner 1: Catherine Dewerd
   * Partner 2: Mark Ariniello 
   * Collaborators: <Any Collaborators>
   */

  /*
   * (A)
   * Replace the 'throw new UnsupportedOperationException' expression with
   * your code in each function.
   * 
   * Do not make other modifications to this template, such as
   * - adding "extends App" or "extends Application" to your Lab object,
   * - adding a "main" method, and
   * - leaving any failing asserts.
   * 
   * Your lab will not be graded if it does not compile.
   * 
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * 'throws new UnsupportedOperationException' as needed to get something
   * that compiles without error.
   */

  /*--------------------------------------------------------*/
  /* Exercises */
  /*--------------------------------------------------------*/
  
  /*--------------------------------------------------------*/
  /*--- # 3.a ---*/
  def abs(n: Double): Double = { if (n < 0) {-1*n} else {n} }
  /* because I got it working for Int... */
  def absInt(n: Int): Int = {if (n>0) n else {~n+1}} //bit shift
  
  /*--- # 3.b ---*/
  def xor(a: Boolean, b: Boolean): Boolean = {
    if (a) { if(b) {false} else {true} }
    else if (b) {true} else {false} 
  }

  /*--------------------------------------------------------*/
  /*--- # 4.a ---*/
  def repeat(s: String, n: Int): String = {
    require(n>=0)
    if (n>0) {s + repeat(s,(n-1))} else {""}
  }
  
  /*--- # 4.b ---*/
  def sqrtStep(c: Double, xn: Double): Double = {xn-(((xn*xn)-c)/(2*xn))}

  def sqrtN(c: Double, x0: Double, n: Int): Double = {
    require(c > 0)
    require(n >= 0)
    if (n>0) {sqrtN(c,sqrtStep(c,x0),(n-1))} else {x0}
  }
  /*def sqrtNwhile(c: Double, x0: Double, n: Int): Double = {
    require(x0 != 0)
    while(n>0) {sqrtStep(c,x0)}
  }  :P  */
  
  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = {
    require(epsilon > 0)
    if (abs((x0*x0)-c)>=epsilon) {sqrtErr(c,sqrtStep(c,x0),epsilon)} else {x0}
  }

  def sqrt(c: Double): Double = {
    require(c >= 0)
    if (c == 0) 0 else sqrtErr(c, 1.0, 0.0001)
  }
  
  /*--------------------------------------------------------*/
  /*--- # 5 ---*/
  /* Search Tree */
  /* 
   * General sources of help:
   * http://tinyurl.com/2f33tz     <- stanford page on binary search trees
   * http://tinyurl.com/nnvsytj    <- help with repOk function
   */
  
  sealed abstract class SearchTree
  case object Empty extends SearchTree
  case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree
  
  def repOk(t: SearchTree): Boolean = {
    def check(t: SearchTree, min: Int, max: Int): Boolean = t match {
      case Empty => true
      case Node(l, d, r) => if ((d >= min) && (d < max) && check(l,min,d) && check(r,d,max)) {true} else {false}          
    }
    check(t, Int.MinValue, Int.MaxValue)
  }
  
  def insert(t: SearchTree, n: Int): SearchTree = t match {
    case Empty => Node(Empty,n,Empty)
    case Node(l,d,r) => if (n == d) {Node(l,d,Node(Empty,n,r))} else if (n < d) {Node(insert(l,n),d,r)} else {Node(l,d,insert(r,n))}
  }
  
  def deleteMin(t: SearchTree): (SearchTree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      case Node(Empty, d, r) => (r, d)
      case Node(l, d, r) => 
        val (l1, m) = deleteMin(l)
        (Node(l1,d,r),m)
    }
  }
 
  def delete(t: SearchTree, n: Int): SearchTree = {
    require(repOk(t)) //must return true
    (t: @unchecked) match {
      case Empty => Empty
      case Node(Empty, d, Empty) => if (d == n) {Empty} else {t}
      case Node(l, d, r) => 
        if (n < d) {Node(delete(l,n),d,r)}
        else if (n > d) {Node(l,d,delete(r,n))}
        else {
          def shift(add: SearchTree, tsmall: SearchTree): SearchTree = tsmall match {
            case Node(Empty,d,r) => Node(add,d,r)
            case Node(l,d,r) => shift(add,l)
          } /*shift() only attaches "add" to the minimum value of tsmall*/
          shift(l,r)
        }
    }
  } /*I couldn't figure out how to do this using the deleteMin function*/
  
  /*--------------------------------------------------------*/
  /*--- # 6 ---*/
  /* JavaScripty */
  
  def eval(e: Expr): Double = e match {
    case N(n) => n  //return number
    case Unary(uop,exp) => (uop: @unchecked) match{
      case Neg => -eval(exp)  //return negative of expression
      case _ => throw new UnsupportedOperationException
    }
    case Binary(bop,exp1,exp2) => (bop: @unchecked) match{
      case Plus => eval(exp1) + eval(exp2)
      case Minus => eval(exp1) - eval(exp2)
      case Times => eval(exp1)*eval(exp2)
      case Div =>  
        if (eval(exp2) == 0) {eval(exp1)*Double.PositiveInfinity} 
        else {eval(exp1)/eval(exp2)}
      case _ => throw new UnsupportedOperationException
    }
    case _ => throw new UnsupportedOperationException
  }
  
 // Interface to run your interpreter from a string.  This is convenient
 // for unit testing.
 def eval(s: String): Double = eval(Parser.parse(s))


 /*--------------------------------------------------------*/
 /* Interface to run your interpreter from the command-line.  You can ignore the code below. */  
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    val expr = Parser.parseFile(file)
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    if (debug) { println("Evaluating ...") }
    val v = eval(expr)
    println(v)
  }
}
