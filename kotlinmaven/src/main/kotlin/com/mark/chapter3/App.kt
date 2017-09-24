package com.mark.chapter3

/**
 * Author: Mark
 * Date  : 2017/9/24
 */


fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4)
    println(joinToString(list, prefix = "(", separator = ", ", suffix = ")"))
    val set = setOf(1, 2, 3, 4, 5)
    val setStr = joinToString(set)
    println(setStr)
    println(set.joinToString())
    println(setStr.lastChar())
    println(StringBuilder("Hello?").lastChar)
    val map = mapOf(1 to "one", 2 to "two", 4 to "four")
    println(map.toString())
}

fun <T> joinToString(collection: Collection<T>, prefix: String = "", separator: String = ", ", suffix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, elem) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(elem)
    }
    result.append(suffix)
    return result.toString()
}


fun String.lastChar(): Char = this[this.length - 1]

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) = this.setCharAt(length - 1, value)