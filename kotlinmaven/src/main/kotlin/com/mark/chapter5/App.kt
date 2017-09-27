package com.mark.chapter5

import com.mark.Person
import java.io.File

/**
 * Author: Mark
 * Date  : 2017/9/26
 */


fun main(args: Array<String>) {
//    val people = listOf(Person("Mark"), Person("Jim"), Person("Tom"))
//    println(people.joinToString(" ") { it.name })
//    val p = createPerson("Mark", 23)
//    println(p)
//    println(p.sayWhat("What"))
    val file = File("/Users/Mark/.m2/repository")
    println(file.isInHiddenDir())
}

fun createPersonFactory() = ::Person
val createPerson = ::Person

fun Person.sayWhat(msg: String): String {
    return msg
}

fun File.isInHiddenDir() = generateSequence(this) { it.parentFile }.any { it.isHidden }

fun alphabet(): String = with(StringBuilder()) {
    for (c in 'A'..'Z') {
        append(c)
    }
    return toString()
}