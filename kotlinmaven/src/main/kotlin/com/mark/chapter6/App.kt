package com.mark.chapter6

/**
 * Author: Mark
 * Date  : 2017/9/27
 */


fun main(args: Array<String>) {
//    val address = Address("street 23", 232323, "HZ", "China")
//    val company = Company("xxx", address)
//    val person = Person("Mark", company)
//    printShippingLabel(person)
//    val person1 = Person("Jim", null)
//    printShippingLabel(person1)

    var name: String? = "Mark"
    name?.let { sendEmailTo(it) }
    name = null
    name?.let { sendEmailTo(it) }
}

class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)

fun printShippingLabel(person: Person) {
    val address = person.company?.address ?: throw IllegalArgumentException("No address")
    with(address) {
        println(streetAddress)
        println("$zipCode $city $country")
    }
}

fun sendEmailTo(who: String) {
    println("Send email to $who")
}