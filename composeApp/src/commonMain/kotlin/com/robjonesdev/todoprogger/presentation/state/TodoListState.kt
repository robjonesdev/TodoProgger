package com.robjonesdev.todoprogger.presentation.state

import com.robjonesdev.todoprogger.domain.models.TodoTask

data class TodoListState(
    val items: List<TodoTask> = emptyList(),
    val showConfirmDeletionDialog: Boolean = false,
    val selectedDeletionCandidate: TodoTask? = null,
    val taskToSchedule: TodoTask? = null,
    val expandedItemIDs: Set<Int> = emptySet(),
)
