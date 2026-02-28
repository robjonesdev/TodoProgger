package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.composables.TodoList
import com.robjonesdev.todoprogger.presentation.state.TodoListState
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.todo_list_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onAction: (TodoListScreenAction) -> Unit,
    onSettingsTapped: () -> Unit,
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
                    IconButton(onClick = { onAction(TodoListScreenAction.OnAddNewTodo) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add New Todo Button",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onSettingsTapped) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings Button",
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
        Column(modifier = Modifier.padding(innerPadding)) {
            val selectedIndex = remember(state.categories, state.selectedCategory) {
                state.categories.indexOfFirst { it.name == state.selectedCategory.name }.coerceAtLeast(0)
            }

            SecondaryScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                state.categories.forEach { category ->
                    Tab(
                        selected = state.selectedCategory.name == category.name,
                        onClick = { onAction(TodoListScreenAction.OnCategorySelected(category.name)) },
                        text = { Text(category.name) }
                    )
                }
                Tab(
                    selected = false,
                    onClick = { onAction(TodoListScreenAction.OnAddCategoryTapped) },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.padding(end = 4.dp))
                            Text("New Group")
                        }
                    }
                )
            }

            TodoList(
                modifier = Modifier.weight(1f),
                state = state,
                action = onAction
            )
        }
    }

    if (state.showAddCategoryDialog) {
        AlertDialog(
            onDismissRequest = { onAction(TodoListScreenAction.OnDismissAddCategory) },
            title = { Text("New Group") },
            text = {
                TextField(
                    value = state.newCategoryName,
                    onValueChange = { onAction(TodoListScreenAction.OnNewCategoryNameChanged(it)) },
                    placeholder = { Text("Group Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onAction(TodoListScreenAction.OnConfirmAddCategory) }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoListScreenAction.OnDismissAddCategory) }) {
                    Text("Cancel")
                }
            }
        )
    }
}
