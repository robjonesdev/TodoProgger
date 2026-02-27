package com.robjonesdev.todoprogger.presentation.state

import com.robjonesdev.todoprogger.domain.models.TodoTask

data class TodoListState(
    val items: List<TodoTask>,
    val showConfirmDeletionDialog: Boolean,
    val selectedDeletionCandidate: TodoTask?,
    val expandedItemIDs: Set<Int>,
)