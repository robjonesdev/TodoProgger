package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.actions.TodoDetailScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoDetailState
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.todo_detail_screen_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    state: TodoDetailState,
    onAction: (TodoDetailScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.todo_detail_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(TodoDetailScreenAction.OnBackTapped) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAction(TodoDetailScreenAction.OnSaveTapped) }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            TextField(
                value = state.title,
                onValueChange = { onAction(TodoDetailScreenAction.OnTitleChanged(it)) },
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Task Title", style = MaterialTheme.typography.headlineMedium) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.description,
                onValueChange = { onAction(TodoDetailScreenAction.OnDescriptionChanged(it)) },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Add more details...") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Progress Entries",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            state.progressEntries.forEach { progressEntry ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = progressEntry.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.FiberManualRecord,
                            contentDescription = null,
                            modifier = Modifier.size(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            ListItem(
                modifier = Modifier.clickable { onAction(TodoDetailScreenAction.OnAddEntryTapped) },
                headlineContent = {
                    Text(
                        text = "Add new progress entry",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add progress entry",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }

    if (state.showAddEntryDialog) {
        AlertDialog(
            onDismissRequest = { onAction(TodoDetailScreenAction.OnDismissAddEntry) },
            title = { Text("New Progress Entry") },
            text = {
                TextField(
                    value = state.newEntryText,
                    onValueChange = { onAction(TodoDetailScreenAction.OnNewEntryTextChanged(it)) },
                    placeholder = { Text("Add an update") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnConfirmAddEntry) }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnDismissAddEntry) }) {
                    Text("Cancel")
                }
            }
        )
    }
}
