package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robjonesdev.todoprogger.data.TodoDao
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.models.Category
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoListState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoListViewModel(private val todoDao: TodoDao) : ViewModel() {

    companion object {
        const val DONE_CATEGORY_NAME = "Done"
    }

    private val _uiState = MutableStateFlow(TodoListState())
    
    val uiState: StateFlow<TodoListState> = combine(
        todoDao.getAllTasks(),
        todoDao.getAllCategories(),
        _uiState
    ) { tasks, categories, currentState ->
        val dbCategories = categories.ifEmpty { listOf(Category("General")) }

        val allTabs = dbCategories + Category(DONE_CATEGORY_NAME)
        
        val filteredItems = if (currentState.selectedCategory.name == DONE_CATEGORY_NAME) {
           // "Done" tab shows all completed tasks regardless of their group
            tasks.filter { it.isCompleted }
        } else {
            // Other tabs show only active tasks belonging to that group
            tasks.filter { it.category == currentState.selectedCategory.name && !it.isCompleted }
        }
        
        currentState.copy(
            items = filteredItems,
            categories = allTabs
        )
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
                    selectedTodoDeletionCandidate = action.task
                ) }
            }
            is TodoListScreenAction.OnRejectDeletion -> {
                _uiState.update { it.copy(
                    showConfirmDeletionDialog = false,
                    selectedTodoDeletionCandidate = null
                ) }
            }
            is TodoListScreenAction.OnConfirmDeletion -> {
                val taskToDelete = _uiState.value.selectedTodoDeletionCandidate
                if (taskToDelete != null) {
                    deleteTask(taskToDelete)
                }
                _uiState.update { it.copy(
                    showConfirmDeletionDialog = false,
                    selectedTodoDeletionCandidate = null
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
            is TodoListScreenAction.OnScheduleReminder -> {
                _uiState.update { it.copy(taskToSchedule = action.task) }
            }
            is TodoListScreenAction.OnDismissReminderPicker -> {
                _uiState.update { it.copy(taskToSchedule = null) }
            }
            is TodoListScreenAction.OnCategorySelected -> {
                _uiState.update { it.copy(selectedCategory = action.category) }
            }
            is TodoListScreenAction.OnAddCategoryTapped -> {
                _uiState.update { it.copy(showAddCategoryDialog = true) }
            }
            is TodoListScreenAction.OnNewCategoryNameChanged -> {
                _uiState.update { it.copy(newCategoryName = action.name) }
            }
            is TodoListScreenAction.OnConfirmAddCategory -> {
                viewModelScope.launch {
                    val newName = _uiState.value.newCategoryName
                    if (newName.isNotBlank() && newName != DONE_CATEGORY_NAME) {
                        todoDao.insertCategory(Category(newName))
                        _uiState.update { it.copy(
                            selectedCategory = Category(newName),
                            showAddCategoryDialog = false,
                            newCategoryName = ""
                        ) }
                    }
                }
            }
            is TodoListScreenAction.OnDismissAddCategory -> {
                _uiState.update { it.copy(showAddCategoryDialog = false, newCategoryName = "") }
            }
            is TodoListScreenAction.OnCategoryDeleteAttempt -> {
                if (action.category.name != DONE_CATEGORY_NAME && action.category.name != "General") {
                    _uiState.update { it.copy(selectedCategoryDeletionCandidate = action.category) }
                }
            }
            is TodoListScreenAction.OnConfirmDeleteCategory -> {
                viewModelScope.launch {
                    _uiState.value.selectedCategoryDeletionCandidate?.let {
                        todoDao.deleteCategory(it.name)
                    }
                    _uiState.update { it.copy(
                        selectedCategoryDeletionCandidate = null,
                        selectedCategory = Category("General")
                    ) }
                }
            }
            is TodoListScreenAction.OnDismissDeleteCategory -> {
                _uiState.update { it.copy(selectedCategoryDeletionCandidate = null) }
            }
            else -> { /* Navigation handled in UI layer */ }
        }
    }

    private fun addNewTodo() {
        viewModelScope.launch {
            val targetCategory = if (_uiState.value.selectedCategory.name == DONE_CATEGORY_NAME) {
                "General"
            } else {
                _uiState.value.selectedCategory.name
            }
            val newTodo = TodoTask(category = targetCategory)
            todoDao.insertTask(newTodo)
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
