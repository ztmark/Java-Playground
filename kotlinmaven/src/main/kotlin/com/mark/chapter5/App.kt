package com.mark.chapter5

import com.mark.Person

/**
 * Author: Mark
 * Date  : 2017/9/26
 */


fun main(args: Array<String>) {
    val people = listOf(Person("Mark"), Person("Jim"), Person("Tom"))
    println(people.joinToString(" ") { it.name })
}
