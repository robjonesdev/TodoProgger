package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robjonesdev.todoprogger.data.TodoDao
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoListState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoListViewModel(private val todoDao: TodoDao) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListState(
        items = emptyList(),
        showConfirmDeletionDialog = false,
        selectedDeletionCandidate = null,
        expandedItemIDs = emptySet()
    ))
    
    val uiState: StateFlow<TodoListState> = combine(
        todoDao.getAllTasks(),
        _uiState
    ) { tasks, currentState ->
        currentState.copy(items = tasks)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )

    fun onAction(action: TodoListScreenAction) {
        when (action) {
            is TodoListScreenAction.OnAddNewTodo -> addNewTodo()
            is TodoListScreenAction.OnToggleCompleted -> toggleTaskCompletion(action.task)
            is TodoListScreenAction.OnUpdateTask -> updateTask(action.task)
            is TodoListScreenAction.OnSwipeToDelete -> {
                _uiState.update { it.copy(
                    showConfirmDeletionDialog = true,
                    selectedDeletionCandidate = action.task
                ) }
            }
            is TodoListScreenAction.OnRejectDeletion -> {
                _uiState.update { it.copy(
                    showConfirmDeletionDialog = false,
                    selectedDeletionCandidate = null
                ) }
            }
            is TodoListScreenAction.OnConfirmDeletion -> {
                val taskToDelete = _uiState.value.selectedDeletionCandidate
                if (taskToDelete != null) {
                    deleteTask(taskToDelete)
                }
                _uiState.update { it.copy(
                    showConfirmDeletionDialog = false,
                    selectedDeletionCandidate = null
                ) }
            }
            is TodoListScreenAction.OnToggleExpanded -> {
                _uiState.update { currentState ->
                    val newExpandedSet = if (action.taskId in currentState.expandedItemIDs) {
                        currentState.expandedItemIDs - action.taskId
                    } else {
                        currentState.expandedItemIDs + action.taskId
                    }
                    currentState.copy(expandedItemIDs = newExpandedSet)
                }
            }
            else -> { /* Handle others */ }
        }
    }

    private fun addNewTodo() {
        viewModelScope.launch {
            todoDao.insertTask(TodoTask())
        }
    }

    private fun toggleTaskCompletion(task: TodoTask) {
        viewModelScope.launch {
            todoDao.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    private fun updateTask(task: TodoTask) {
        viewModelScope.launch {
            todoDao.updateTask(task)
        }
    }

    private fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            todoDao.deleteTask(task.id)
        }
    }
}
