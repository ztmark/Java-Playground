package com.mark.chapter4

/**
 * Author: Mark
 * Date  : 2017/9/25
 */


fun main(args: Array<String>) {
    val button = Button()
    button.showOff()
    button.click()
    button.setFocus(true)
    val user = User.newDefaultUser()
    println(user.username)
}

interface Clickable {
    fun click()
    fun showOff() = println("I am clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if(b) "got" else "lost"} focus.")

    fun showOff() = println("I am focusable")
}

class Button: Clickable, Focusable {
    override fun click() = println("I was clicked.")

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

class User(val username: String) {

    init {
        println("in init block")
    }

    companion object {
        fun newDefaultUser(): User = User("Mark")

    }
}

class DelegateCollection<T>(innerList: Collection<T> = ArrayList<T>()): Collection<T> by innerList {

}