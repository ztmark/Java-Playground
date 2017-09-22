package com.mark

/**
 * Author: Mark
 * Date  : 2017/5/21
 */



val persons = listOf(Person("Mark"), Person("Jim", 23))

val old = persons.maxBy { it.age ?: 0 }

fun main(args: Array<String>) {
    println("Hello $old")
    val r1 = Rectangle(height, width)
    val r2 = Rectangle(height, width + 1)
    println(r1.isSquare)
    println(r2.isSquare)
    println(City.BEIJING.name)
    println(place(City.SHANGHAI))
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

fun eval(expr: Expr): Int {
    if (expr is Num) {
        return expr.num
    }
    if (expr is Sum) {
        return eval(expr.left) + eval(expr.right)
    }
    throw IllegalArgumentException("Unknown Expression")
}