package com.robjonesdev.todoprogger.presentation.state

import com.robjonesdev.todoprogger.domain.models.ProgressEntry
import com.robjonesdev.todoprogger.domain.models.Category

data class TodoDetailState(
    val title: String = "",
    val description: String = "",
    val progressEntries: List<ProgressEntry> = emptyList(),
    val category: String = "General",
    val availableCategories: List<Category> = emptyList(),
    val showAddEntryDialog: Boolean = false,
    val newEntryText: String = "",
)
