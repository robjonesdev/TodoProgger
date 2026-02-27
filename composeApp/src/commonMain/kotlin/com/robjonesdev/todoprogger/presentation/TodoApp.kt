package com.robjonesdev.todoprogger.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robjonesdev.todoprogger.data.getDatabase
import com.robjonesdev.todoprogger.data.getDatabaseBuilder
import com.robjonesdev.todoprogger.domain.reminders.getReminderScheduler
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.actions.TodoDetailScreenAction
import com.robjonesdev.todoprogger.presentation.composables.ReminderPickerDialog
import com.robjonesdev.todoprogger.presentation.screens.TodoDetailScreen
import com.robjonesdev.todoprogger.presentation.screens.TodoListScreen
import com.robjonesdev.todoprogger.presentation.screens.SettingsScreen
import com.robjonesdev.todoprogger.presentation.theme.TodoProggerTheme
import com.robjonesdev.todoprogger.presentation.viewmodels.TodoListViewModel
import com.robjonesdev.todoprogger.presentation.viewmodels.TodoDetailViewModel
import com.robjonesdev.todoprogger.presentation.viewmodels.SettingsViewModel

@Composable
fun TodoApp(context: Any? = null) {
    val database = remember { getDatabase(getDatabaseBuilder(context)) }
    val dao = remember { database.todoDao() }
    val todoListViewModel = remember { TodoListViewModel(dao) }
    val todoListState by todoListViewModel.uiState.collectAsState()
    
    val settingsViewModel = remember { SettingsViewModel() }
    val selectedTheme by settingsViewModel.selectedTheme.collectAsState()
    
    val reminderScheduler = remember { getReminderScheduler(context) }

    val navController = rememberNavController()


    val onSettingsTapped = remember(navController) { { navController.navigate(TodoScreen.Settings.route); Unit } }
    val onBackTapped = remember(navController) { { navController.popBackStack(); Unit } }
    val onThemeSelected = remember(settingsViewModel) { { theme: com.robjonesdev.todoprogger.presentation.theme.AppTheme -> settingsViewModel.setTheme(theme); Unit } }
    
    val onTodoListAction = remember(todoListViewModel, navController) {
        { action: TodoListScreenAction ->
            when (action) {
                is TodoListScreenAction.OnItemTapped -> {
                    navController.navigate("detail/${action.task.id}")
                    Unit
                }
                else -> todoListViewModel.onAction(action)
            }
        }
    }

    TodoProggerTheme(appTheme = selectedTheme) {
        Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = TodoScreen.List.route
            ) {
                composable(TodoScreen.List.route) {
                    TodoListScreen(
                        state = todoListState,
                        onAction = onTodoListAction,
                        onSettingsTapped = onSettingsTapped
                    )
                }
                composable(
                    route = TodoScreen.Detail.route,
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getInt("taskId")
                    val initialTask = remember(taskId) { todoListState.items.find { it.id == taskId } }

                    if (initialTask != null) {
                        val detailViewModel = remember(taskId) { TodoDetailViewModel(initialTask) }
                        val detailState by detailViewModel.uiState.collectAsState()

                        TodoDetailScreen(
                            state = detailState,
                            onAction = { action ->
                                when (action) {
                                    is TodoDetailScreenAction.OnBackTapped -> onBackTapped()
                                    is TodoDetailScreenAction.OnSaveTapped -> {
                                        todoListViewModel.onAction(TodoListScreenAction.OnUpdateTask(detailViewModel.getUpdatedTask()))
                                        onBackTapped()
                                    }
                                    else -> detailViewModel.onAction(action)
                                }
                            }
                        )
                    }
                }
                composable(TodoScreen.Settings.route) {
                    SettingsScreen(
                        selectedTheme = selectedTheme,
                        onThemeSelected = onThemeSelected,
                        onBackTapped = onBackTapped
                    )
                }
            }
        }

        todoListState.taskToSchedule?.let { task ->
            ReminderPickerDialog(
                onDismiss = { 
                    todoListViewModel.onAction(TodoListScreenAction.OnDismissReminderPicker) 
                },
                onConfirm = { dateTime ->
                    reminderScheduler.schedule(task, dateTime)
                    todoListViewModel.onAction(TodoListScreenAction.OnDismissReminderPicker)
                }
            )
        }
    }
}
