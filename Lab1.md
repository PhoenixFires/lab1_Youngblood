**Catherine Youngblood**

Lab 01 - CSCI 3155

*Group Members: Catherine Dewerd; Mark Ariniello*

###Problem 1
####(A)

***Line 4***: `pi` is bound at **Line 3**. The use of `pi` on **Line 4** occurs within the scope of a function that explicitly defines it, overwriting the original definition of `pi` for the duration of the function.

***Line 7***: `pi` is bound at **Line 1**. The function `area` does not explicitly define `pi`, so it uses the pre-existing definition created at **Line 1**.

####(B)

***Line 3, 6, and 10***: `x` is bound at **Line 2**, where the function `f()` was initially created, making the `val x` that is given a value when `f()` is called.

***Line 13***: `x` is bound at **Line 1**, where the value is created to be passed into the function (outside of the scope of a function)

###Problem 2
The body of `g` is well-typed and has a return type of `((Int, Int), Int)`. 

**Line 2:**
```scala
(a,b): (Int, (Int,Int)) //because
	a:Int //(defined as 1)
	b:(Int, Int) //because
		x:Int //(defined in Line 1)
		3:Int
```
		
**Line 3:**
```scala
(b,1): ((Int, Int), Int) //because
	b:(Int, Int) //because
		x:Int //(defined in Line 1)
		3:Int 
	1:Int
```
```scala
(b, a+2): ((Int, Int), Int) //because
	b:(Int, Int) //because
		x:Int //(defined in Line 1)
		3:Int 
	a+2: Int
```

```scala
def g(x:Int):((Int, Int), Int)= {/*function code*/}
```
