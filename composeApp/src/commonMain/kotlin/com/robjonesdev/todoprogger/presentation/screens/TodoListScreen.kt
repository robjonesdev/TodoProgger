package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.presentation.composables.TodoList
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.todo_list_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoTaskList: List<TodoTask>,
    onAddNewTodo: () -> Unit,
    onDeleteTask: (TodoTask) -> Unit,
    onItemTapped: (TodoTask) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
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
                    IconButton(onClick = onAddNewTodo) {
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
            onItemTapped = onItemTapped,
            onDelete = onDeleteTask
        )
    }
}
