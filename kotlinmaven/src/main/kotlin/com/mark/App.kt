package com.mark

import java.util.*

/**
 * Author: Mark
 * Date  : 2017/5/21
 */



val persons = listOf(Person("Mark"), Person("Jim", 23))

val old = persons.maxBy { it.age ?: 0 }

fun main(args: Array<String>) {
//    println("Hello $old")
//    val r1 = Rectangle(height, width)
//    val r2 = Rectangle(height, width + 1)
//    println(r1.isSquare)
//    println(r2.isSquare)
//    println(City.BEIJING.name)
//    println(place(City.SHANGHAI))
//
//    println(eval(Sum(Num(1), Sum(Num(2), Num(3)))))

//    iterateMap()

    println(isLetter('3'))
    println(isLetter('d'))
    println(isLetter('*'))
    println(isNotDigit('*'))
    println(isNotDigit('d'))
    println(isNotDigit('4'))

}

private fun isLetter(c: Char) = c in 'A'..'Z' || c in 'a'..'z'

private fun isNotDigit(c: Char) = c !in '0'..'9'

private fun iterateMap() {
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {
        val toBinaryString = Integer.toBinaryString(c.toInt())
        binaryReps[c] = toBinaryString
    }
    for ((c, b) in binaryReps) {
        println("$c = $b")
    }
}

fun place(city: City) = when (city) {
    City.BEIJING -> "North"
    City.HANGZHOU, City.SHANGHAI -> "South"
}

fun max(a: Int, b: Int) = if (a > b) a else b

var height: Int = 23
var width: Int = 23

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() = height == width

}

interface Expr

class Num(val num: Int) : Expr

class Sum(val left: Expr, val right: Expr) : Expr

fun eval(expr: Expr): Int =
    /*if (expr is Num) expr.num
    else if (expr is Sum) {
        eval(expr.left) + eval(expr.right)
    } else {
        throw IllegalArgumentException("Unknown Expression")
    }*/
    when(expr) {
        is Num -> expr.num
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("Unknown Expression")
    }
