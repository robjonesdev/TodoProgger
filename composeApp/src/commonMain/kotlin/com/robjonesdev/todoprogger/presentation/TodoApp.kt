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
import com.robjonesdev.todoprogger.presentation.screens.TodoDetailScreen
import com.robjonesdev.todoprogger.presentation.screens.TodoListScreen
import com.robjonesdev.todoprogger.presentation.theme.TodoProggerTheme
import com.robjonesdev.todoprogger.presentation.viewmodels.TodoListViewModel

@Composable
fun TodoApp(context: Any? = null) {
    val database = remember { getDatabase(getDatabaseBuilder(context)) }
    val dao = remember { database.todoDao() }
    val todoListViewModel = remember { TodoListViewModel(dao) }
    val todoTaskList by todoListViewModel.todoTasks.collectAsState()

    val navController = rememberNavController()

    val onAddNewTodo = remember(todoListViewModel) { { todoListViewModel.addNewTodo() } }
    val onDeleteTask = remember(todoListViewModel) { { task: com.robjonesdev.todoprogger.domain.models.TodoTask -> todoListViewModel.deleteTask(task) } }
    val onUpdateTask = remember(todoListViewModel) { { task: com.robjonesdev.todoprogger.domain.models.TodoTask -> todoListViewModel.updateTask(task) } }

    TodoProggerTheme {
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
                        onDeleteTask = onDeleteTask,
                        onUpdateTask = onUpdateTask,
                        onItemTapped = { task ->
                            navController.navigate("detail/${task.id}")
                        }
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
                            onBackTapped = { navController.popBackStack() },
                            onSaveTapped = { updatedTask ->
                                todoListViewModel.updateTask(updatedTask)
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
