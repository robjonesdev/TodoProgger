package com.robjonesdev.todoprogger.presentation

enum class TodoScreen(val route: String) {
    List("list"),
    Detail("detail/{taskId}"),
    Settings("settings"),
}
