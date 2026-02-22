package com.robjonesdev.todoprogger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform