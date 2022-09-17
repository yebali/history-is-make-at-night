package com.yebali.dev.place.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

/*
 * reference : https://www.baeldung.com/kotlin-logging
 */

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> = javaClass.enclosingClass?.takeIf {
    it.kotlin.companionObject?.java == javaClass
} ?: javaClass

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>): Logger = getLogger(
        getClassForLogging(
            thisRef.javaClass
        )
    )
}

open class Logging {
    protected val logger by LoggerDelegate()
}

/**
 * find ***$func$1*** in com.example.package.FileKt$func$1
 */
val dollarSignRegex = """\$.*$""".toRegex()

private fun <T : Any> getClassName(clazz: Class<T>): String = clazz.name.replace(dollarSignRegex, "")
