package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.composables.TodoList
import com.robjonesdev.todoprogger.presentation.state.TodoListState
import kotlinx.coroutines.withTimeoutOrNull
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.*

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
                            contentDescription = stringResource(Res.string.add_new_todo_cd),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onSettingsTapped) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(Res.string.settings_cd),
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
                        onClick = { /* Handled in custom pointerInput below */ },
                        text = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 12.dp)
                                    // Custom gesture logic: Fallback to tap if long press isn't reached
                                    .pointerInput(category) {
                                        awaitEachGesture {
                                            val down = awaitFirstDown()
                                            val longPressTimeout = 500L
                                            
                                            // Wait for either the timeout (long press) or the finger to lift
                                            val up = withTimeoutOrNull(longPressTimeout) {
                                                waitForUpOrCancellation()
                                            }

                                            if (up == null) {
                                                // Timeout reached: It's a Long Press
                                                onAction(TodoListScreenAction.OnCategoryDeleteAttempt(category))
                                            } else {
                                                // Finger lifted before timeout: It's a Tap
                                                onAction(TodoListScreenAction.OnCategorySelected(category))
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(category.name)
                            }
                        }
                    )
                }
                Tab(
                    selected = false,
                    onClick = { onAction(TodoListScreenAction.OnAddCategoryTapped) },
                    text = {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(stringResource(Res.string.new_group))
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
            title = { Text(stringResource(Res.string.new_group)) },
            text = {
                TextField(
                    value = state.newCategoryName,
                    onValueChange = { onAction(TodoListScreenAction.OnNewCategoryNameChanged(it)) },
                    placeholder = { Text(stringResource(Res.string.group_name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onAction(TodoListScreenAction.OnConfirmAddCategory) }
                ) {
                    Text(stringResource(Res.string.action_add))
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoListScreenAction.OnDismissAddCategory) }) {
                    Text(stringResource(Res.string.action_cancel))
                }
            }
        )
    }

    if(state.selectedCategoryDeletionCandidate != null) {
        AlertDialog(
            onDismissRequest = { onAction(TodoListScreenAction.OnDismissDeleteCategory) },
            title = { Text(stringResource(Res.string.delete_category)) },
            text = {
                Text(text = stringResource(Res.string.delete_category_description, state.selectedCategoryDeletionCandidate.name))
            },
            confirmButton = {
                TextButton(
                    onClick = { onAction(TodoListScreenAction.OnConfirmDeleteCategory) }
                ) {
                    Text(stringResource(Res.string.action_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoListScreenAction.OnDismissDeleteCategory) }) {
                    Text(stringResource(Res.string.action_cancel))
                }
            }
        )
    }
}
