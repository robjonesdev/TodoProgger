package com.robjonesdev.todoprogger.presentation.state

import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.models.Category

data class TodoListState(
    val items: List<TodoTask> = emptyList(),
    val showConfirmDeletionDialog: Boolean = false,
    val selectedDeletionCandidate: TodoTask? = null,
    val taskToSchedule: TodoTask? = null,
    val expandedItemIDs: Set<Int> = emptySet(),
    val categories: List<Category> = listOf(Category("General")),
    val selectedCategory: Category = Category("General"),
    val showAddCategoryDialog: Boolean = false,
    val newCategoryName: String = ""
)
