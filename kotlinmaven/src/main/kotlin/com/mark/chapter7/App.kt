package com.mark.chapter7

import java.time.LocalDate

/**
 * Author: Mark
 * Date  : 2017/9/28
 */


fun main(args: Array<String>) {
//    var point1 = Point(1, 2)
//    var point2 = Point(3, 4)
//    println(point1 + point2)
//    point1 += point2
//    println(point1)
//    println(-point1)
//    var point3 = point2.copy()
//    println(point2 == point3)
//    println(point2[0])
//    println(point2[1])

    val now = LocalDate.now()
    val range = now..now.plusDays(10)
    println(now.plusWeeks(1) in range)
    for (d in range) {
        println(d)
    }

}

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Point) {
            return false
        }
        return x == other.x && y == other.y
    }
}

operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}

operator fun Point.get(idx: Int): Int {
    return when (idx) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid index $idx")
    }
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
        object : Iterator<LocalDate> {
            var current = start

            override fun hasNext(): Boolean = current <= endInclusive

            override fun next(): LocalDate = current.apply { current = plusDays(1) }

        }