package com.robjonesdev.todoprogger.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_tasks")
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "New To-Do",
    val description: String = "Tap to edit, Swipe left to delete",
    var isCompleted: Boolean = false,
)