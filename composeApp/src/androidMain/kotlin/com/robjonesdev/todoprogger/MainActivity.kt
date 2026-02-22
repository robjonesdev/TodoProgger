package com.robjonesdev.todoprogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.robjonesdev.todoprogger.presentation.screens.TodoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            // Pass 'this' (the Activity Context) to the screen
            TodoListScreen(context = this)
        }
    }
}
