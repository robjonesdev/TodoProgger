package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.robjonesdev.todoprogger.domain.models.ProgressEntry
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.models.Category
import com.robjonesdev.todoprogger.presentation.actions.TodoDetailScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TodoDetailViewModel(
    private val initialTask: TodoTask,
    categories: List<Category>
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoDetailState(
        title = initialTask.title,
        description = initialTask.description,
        progressEntries = initialTask.progressEntries,
        category = initialTask.category,
        availableCategories = categories
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
            is TodoDetailScreenAction.OnCategoryChanged -> {
                _uiState.update { it.copy(category = action.category) }
            }
            is TodoDetailScreenAction.OnAddEntryTapped -> {
                _uiState.update { it.copy(showAddEntryDialog = true, selectedEntryToEdit = null, newEntryText = "") }
            }
            is TodoDetailScreenAction.OnEditEntryTapped -> {
                _uiState.update { it.copy(
                    showAddEntryDialog = true, 
                    selectedEntryToEdit = action.entry,
                    newEntryText = action.entry.description
                ) }
            }
            is TodoDetailScreenAction.OnDeleteEntryAttempt -> {
                _uiState.update { it.copy(selectedEntryToDelete = action.entry) }
            }
            is TodoDetailScreenAction.OnConfirmDeleteEntry -> {
                val entryToDelete = _uiState.value.selectedEntryToDelete
                if (entryToDelete != null) {
                    val updatedEntries = _uiState.value.progressEntries.filter { it.id != entryToDelete.id }
                    _uiState.update { it.copy(progressEntries = updatedEntries, selectedEntryToDelete = null) }
                }
            }
            is TodoDetailScreenAction.OnDismissDeleteEntry -> {
                _uiState.update { it.copy(selectedEntryToDelete = null) }
            }
            is TodoDetailScreenAction.OnNewEntryTextChanged -> {
                _uiState.update { it.copy(newEntryText = action.text) }
            }
            is TodoDetailScreenAction.OnConfirmAddEntry -> {
                val currentText = _uiState.value.newEntryText
                if (currentText.isNotBlank()) {
                    val editingEntry = _uiState.value.selectedEntryToEdit
                    if (editingEntry != null) {
                        val updatedEntries = _uiState.value.progressEntries.map { 
                            if (it.id == editingEntry.id) it.copy(description = currentText) else it 
                        }
                        _uiState.update { it.copy(progressEntries = updatedEntries, showAddEntryDialog = false, newEntryText = "") }
                    } else {
                        val newId = (_uiState.value.progressEntries.maxOfOrNull { it.id } ?: 0) + 1
                        val newEntry = ProgressEntry(id = newId, description = currentText)
                        _uiState.update { it.copy(
                            progressEntries = it.progressEntries + newEntry,
                            showAddEntryDialog = false,
                            newEntryText = ""
                        ) }
                    }
                }
            }
            is TodoDetailScreenAction.OnDismissAddEntry -> {
                _uiState.update { it.copy(showAddEntryDialog = false, newEntryText = "", selectedEntryToEdit = null) }
            }
            else -> { /* Navigation handled in UI */ }
        }
    }

    fun getUpdatedTask(): TodoTask {
        return initialTask.copy(
            title = _uiState.value.title,
            description = _uiState.value.description,
            progressEntries = _uiState.value.progressEntries,
            category = _uiState.value.category
        )
    }
}
