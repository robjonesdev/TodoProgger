package com.robjonesdev.todoprogger.domain.utils

import platform.Foundation.NSLog

actual object Logger {
    actual fun log(level: LogLevel, tag: String, message: String) {
        val levelPrefix = when (level) {
            LogLevel.VERBOSE -> "[VERBOSE]"
            LogLevel.DEBUG -> "[DEBUG]"
            LogLevel.INFO -> "[INFO]"
            LogLevel.WARN -> "[WARN]"
            LogLevel.ERROR -> "[ERROR]"
        }
        NSLog("%s [%s] %s", levelPrefix, tag, message)
    }
}
