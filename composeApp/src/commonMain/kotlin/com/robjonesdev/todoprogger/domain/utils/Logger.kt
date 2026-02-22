package com.robjonesdev.todoprogger.domain.utils

expect object Logger {
    fun log(level: LogLevel, tag: String, message: String)
}

fun Logger.v(tag: String, message: String) = log(LogLevel.VERBOSE, tag, message)
fun Logger.d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)
fun Logger.i(tag: String, message: String) = log(LogLevel.INFO, tag, message)
fun Logger.w(tag: String, message: String) = log(LogLevel.WARN, tag, message)
fun Logger.e(tag: String, message: String) = log(LogLevel.ERROR, tag, message)
