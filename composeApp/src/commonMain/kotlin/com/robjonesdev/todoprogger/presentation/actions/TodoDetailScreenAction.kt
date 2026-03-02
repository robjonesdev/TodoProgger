package com.robjonesdev.todoprogger.presentation.actions

import com.robjonesdev.todoprogger.domain.models.ProgressEntry

sealed interface TodoDetailScreenAction {
    data class OnTitleChanged(val title: String) : TodoDetailScreenAction
    data class OnDescriptionChanged(val description: String) : TodoDetailScreenAction
    data class OnCategoryChanged(val category: String) : TodoDetailScreenAction
    data object OnAddEntryTapped : TodoDetailScreenAction
    data class OnEditEntryTapped(val entry: ProgressEntry) : TodoDetailScreenAction
    data class OnDeleteEntryAttempt(val entry: ProgressEntry) : TodoDetailScreenAction
    data object OnConfirmDeleteEntry : TodoDetailScreenAction
    data object OnDismissDeleteEntry : TodoDetailScreenAction
    data class OnNewEntryTextChanged(val text: String) : TodoDetailScreenAction
    data object OnConfirmAddEntry : TodoDetailScreenAction
    data object OnDismissAddEntry : TodoDetailScreenAction
    data object OnSaveTapped : TodoDetailScreenAction
    data object OnBackTapped : TodoDetailScreenAction
}
