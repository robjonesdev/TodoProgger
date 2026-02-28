package com.robjonesdev.todoprogger.presentation.actions

import com.robjonesdev.todoprogger.domain.models.TodoTask

sealed interface TodoListScreenAction {
    data class OnItemTapped(val task: TodoTask) : TodoListScreenAction
    data class OnSwipeToDelete(val task: TodoTask) : TodoListScreenAction
    data object OnRejectDeletion : TodoListScreenAction
    data object OnConfirmDeletion : TodoListScreenAction
    data class OnScheduleReminder(val task: TodoTask) : TodoListScreenAction
    data object OnDismissReminderPicker : TodoListScreenAction
    data class OnToggleExpanded(val taskId: Int) : TodoListScreenAction
    data class OnToggleCompleted(val task: TodoTask) : TodoListScreenAction
    data class OnUpdateTask(val task: TodoTask) : TodoListScreenAction
    data object OnAddNewTodo : TodoListScreenAction
    data class OnCategorySelected(val category: String) : TodoListScreenAction
    data object OnAddCategoryTapped : TodoListScreenAction
    data class OnNewCategoryNameChanged(val name: String) : TodoListScreenAction
    data object OnConfirmAddCategory : TodoListScreenAction
    data object OnDismissAddCategory : TodoListScreenAction
}
