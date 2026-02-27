package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.robjonesdev.todoprogger.domain.models.ProgressEntry
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.presentation.actions.TodoDetailScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TodoDetailViewModel(private val initialTask: TodoTask) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoDetailState(
        title = initialTask.title,
        description = initialTask.description,
        progressEntries = initialTask.progressEntries
    ))
    val uiState: StateFlow<TodoDetailState> = _uiState

    fun onAction(action: TodoDetailScreenAction) {
        when (action) {
            is TodoDetailScreenAction.OnTitleChanged -> {
                _uiState.update { it.copy(title = action.title) }
            }
            is TodoDetailScreenAction.OnDescriptionChanged -> {
                _uiState.update { it.copy(description = action.description) }
            }
            is TodoDetailScreenAction.OnAddEntryTapped -> {
                _uiState.update { it.copy(showAddEntryDialog = true) }
            }
            is TodoDetailScreenAction.OnNewEntryTextChanged -> {
                _uiState.update { it.copy(newEntryText = action.text) }
            }
            is TodoDetailScreenAction.OnConfirmAddEntry -> {
                val currentText = _uiState.value.newEntryText
                if (currentText.isNotBlank()) {
                    val newId = (_uiState.value.progressEntries.maxOfOrNull { it.id } ?: 0) + 1
                    val newEntry = ProgressEntry(id = newId, description = currentText)
                    _uiState.update { it.copy(
                        progressEntries = it.progressEntries + newEntry,
                        showAddEntryDialog = false,
                        newEntryText = ""
                    ) }
                }
            }
            is TodoDetailScreenAction.OnDismissAddEntry -> {
                _uiState.update { it.copy(showAddEntryDialog = false, newEntryText = "") }
            }
            else -> { /* Navigation handled in UI */ }
        }
    }

    fun getUpdatedTask(): TodoTask {
        return initialTask.copy(
            title = _uiState.value.title,
            description = _uiState.value.description,
            progressEntries = _uiState.value.progressEntries
        )
    }
}
