package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robjonesdev.todoprogger.data.getDatabase
import com.robjonesdev.todoprogger.data.getDatabaseBuilder
import com.robjonesdev.todoprogger.presentation.TodoScreen
import com.robjonesdev.todoprogger.presentation.composables.TodoList
import com.robjonesdev.todoprogger.presentation.viewmodels.TodoListViewModel
import com.robjonesdev.todoprogger.presentation.theme.TodoProggerTheme
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.todo_list_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(context: Any? = null) {
    val database = remember { getDatabase(getDatabaseBuilder(context)) }
    val dao = remember { database.todoDao() }
    val todoListViewModel = remember { TodoListViewModel(dao) }
    val todoTaskList by todoListViewModel.todoTasks.collectAsState()

    val navController = rememberNavController()

    TodoProggerTheme {
        NavHost(navController = navController, startDestination = TodoScreen.List.route) {
            composable(TodoScreen.List.route) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(Res.string.todo_list_title),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            actions = {
                                IconButton(onClick = { todoListViewModel.addNewTodo() }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add New Todo",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                titleContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                ) { innerPadding ->
                    TodoList(
                        modifier = Modifier.padding(innerPadding),
                        items = todoTaskList,
                        onItemTapped = { task ->
                            navController.navigate("detail/${task.id}")
                        },
                        onDelete = { todoListViewModel.deleteTask(task = it) }
                    )
                }
            }
            composable(TodoScreen.Detail.route) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
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
