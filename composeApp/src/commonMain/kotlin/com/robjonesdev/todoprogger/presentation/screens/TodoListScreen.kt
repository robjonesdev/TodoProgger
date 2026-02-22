package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.robjonesdev.todoprogger.data.getDatabase
import com.robjonesdev.todoprogger.data.getDatabaseBuilder
import com.robjonesdev.todoprogger.domain.models.TodoTask
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
    var selectedTask by remember { mutableStateOf<TodoTask?>(null) }

    TodoProggerTheme {
        AnimatedContent(targetState = selectedTask) { task ->
            if (task == null) {
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
                        onItemTapped = { selectedTask = it },
                        onDelete = { todoListViewModel.deleteTask(task = it) }
                    )
                }
            } else {
                TodoDetailScreen(
                    todoTask = task,
                    onBackTapped = { selectedTask = null },
                    onSaveTapped = { updatedTask ->
                        todoListViewModel.updateTask(updatedTask)
                        selectedTask = null
                    }
                )
            }
        }
    }
}
