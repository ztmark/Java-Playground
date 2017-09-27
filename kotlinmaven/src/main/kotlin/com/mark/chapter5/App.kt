package com.mark.chapter5

import com.mark.Person

/**
 * Author: Mark
 * Date  : 2017/9/26
 */


fun main(args: Array<String>) {
    val people = listOf(Person("Mark"), Person("Jim"), Person("Tom"))
    println(people.joinToString(" ") { it.name })
    val p = createPerson("Mark", 23)
    println(p)
    println(p.sayWhat("What"))
}

fun createPersonFactory() = ::Person
val createPerson = ::Person

fun Person.sayWhat(msg: String): String {
    return msg
}