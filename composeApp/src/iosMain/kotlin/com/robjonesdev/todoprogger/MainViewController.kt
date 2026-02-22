package com.robjonesdev.todoprogger

import androidx.compose.ui.window.ComposeUIViewController
import com.robjonesdev.todoprogger.presentation.screens.TodoListScreen

fun MainViewController() = ComposeUIViewController { TodoListScreen() }