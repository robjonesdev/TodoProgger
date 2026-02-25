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
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.reminders.getReminderScheduler
import com.robjonesdev.todoprogger.presentation.composables.ReminderPickerDialog
import com.robjonesdev.todoprogger.presentation.screens.TodoDetailScreen
import com.robjonesdev.todoprogger.presentation.screens.TodoListScreen
import com.robjonesdev.todoprogger.presentation.screens.SettingsScreen
import com.robjonesdev.todoprogger.presentation.theme.TodoProggerTheme
import com.robjonesdev.todoprogger.presentation.viewmodels.TodoListViewModel
import com.robjonesdev.todoprogger.presentation.viewmodels.SettingsViewModel

@Composable
fun TodoApp(context: Any? = null) {
    val database = remember { getDatabase(getDatabaseBuilder(context)) }
    val dao = remember { database.todoDao() }
    val todoListViewModel = remember { TodoListViewModel(dao) }
    val todoTaskList by todoListViewModel.todoTasks.collectAsState()
    
    val settingsViewModel = remember { SettingsViewModel() }
    val selectedTheme by settingsViewModel.selectedTheme.collectAsState()
    
    val reminderScheduler = remember { getReminderScheduler(context) }
    var taskToSchedule by remember { mutableStateOf<TodoTask?>(null) }

    val navController = rememberNavController()

    // Optimization: Remember lambdas to prevent unnecessary recompositions of child screens
    val onAddNewTodo = remember(todoListViewModel) { { todoListViewModel.addNewTodo() } }
    val onDeleteTask = remember(todoListViewModel) { { task: TodoTask -> todoListViewModel.deleteTask(task) } }
    val onUpdateTask = remember(todoListViewModel) { { task: TodoTask -> todoListViewModel.updateTask(task) } }
    val onScheduleReminder = remember { { task: TodoTask -> taskToSchedule = task } }
    
    // Navigation Lambdas, keys passed here ensure that the composable still recomposes
    // when the keys change
    val onSettingsTapped = remember(navController) { 
        { 
            navController.navigate(TodoScreen.Settings.route) 
            Unit
        } 
    }

    val onItemTapped = remember(navController) { 
        { task: TodoTask -> 
            navController.navigate("detail/${task.id}")
            Unit
        } 
    }

    val onBackTapped = remember(navController) { 
        { 
            navController.popBackStack()
            Unit
        } 
    }

    val onSaveTask = remember(todoListViewModel, navController) { 
        { task: TodoTask -> 
            todoListViewModel.updateTask(task)
            navController.popBackStack()
            Unit
        } 
    }

    val onThemeSelected = remember(settingsViewModel) { 
        { theme: com.robjonesdev.todoprogger.presentation.theme.AppTheme -> 
            settingsViewModel.setTheme(theme) 
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
                        todoTaskList = todoTaskList,
                        onAddNewTodo = onAddNewTodo,
                        onSettingsTapped = onSettingsTapped,
                        onDeleteTask = onDeleteTask,
                        onUpdateTask = onUpdateTask,
                        onScheduleReminder = onScheduleReminder,
                        onItemTapped = onItemTapped
                    )
                }
                composable(
                    route = TodoScreen.Detail.route,
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getInt("taskId")
                    val task = todoTaskList.find { it.id == taskId }

                    if (task != null) {
                        TodoDetailScreen(
                            todoTask = task,
                            onBackTapped = onBackTapped,
                            onSaveTapped = onSaveTask
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
        
        taskToSchedule?.let { task ->
            ReminderPickerDialog(
                onDismiss = { taskToSchedule = null },
                onConfirm = { dateTime ->
                    reminderScheduler.schedule(task, dateTime)
                    taskToSchedule = null
                }
            )
        }
    }
}
