package com.yebali.dev.place.util

fun String.removeHtmlTags(): String {
    return this.replace("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>".toRegex(), "")
}

fun String.removeWhitespace(): String {
    return this.replace(" ", "")
}
