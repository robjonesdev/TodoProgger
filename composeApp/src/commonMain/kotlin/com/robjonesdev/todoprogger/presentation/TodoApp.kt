package com.robjonesdev.todoprogger.presentation

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

    TodoProggerTheme {
        NavHost(
            navController = navController,
            startDestination = TodoScreen.List.route
        ) {
            composable(TodoScreen.List.route) {
                TodoListScreen(
                    todoTaskList = todoTaskList,
                    onAddNewTodo = { todoListViewModel.addNewTodo() },
                    onDeleteTask = { todoListViewModel.deleteTask(it) },
                    onUpdateTask = { todoListViewModel.updateTask(it) },
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
