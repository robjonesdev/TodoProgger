package com.robjonesdev.todoprogger.presentation.state

import com.robjonesdev.todoprogger.domain.models.ProgressEntry

data class TodoDetailState(
    val title: String = "",
    val description: String = "",
    val progressEntries: List<ProgressEntry> = emptyList(),
    val showAddEntryDialog: Boolean = false,
    val newEntryText: String = "",
)
