package com.mark.chapter7

/**
 * Author: Mark
 * Date  : 2017/9/28
 */


fun main(args: Array<String>) {
    var point1 = Point(1, 2)
    var point2 = Point(3, 4)
    println(point1 + point2)
    point1 += point2
    println(point1)
    println(-point1)
}

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }
}

operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}
