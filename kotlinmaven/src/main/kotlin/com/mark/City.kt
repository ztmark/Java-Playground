package com.mark

/**
 * Author: Mark
 * Date  : 2017/9/22
 */
enum class City(val n: String) {

    HANGZHOU("HangZhou"), BEIJING("Beijing"), SHANGHAI("Shanghai");

    fun toLowCase() = n.toLowerCase()

}