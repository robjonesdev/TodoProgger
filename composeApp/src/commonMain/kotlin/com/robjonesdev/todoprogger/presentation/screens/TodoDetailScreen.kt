package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.actions.TodoDetailScreenAction
import com.robjonesdev.todoprogger.presentation.composables.detectGestures
import com.robjonesdev.todoprogger.presentation.state.TodoDetailState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TodoDetailScreen(
    state: TodoDetailState,
    onAction: (TodoDetailScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var showCategoryMenu by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.todo_detail_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(TodoDetailScreenAction.OnBackTapped) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.action_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAction(TodoDetailScreenAction.OnSaveTapped) }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = stringResource(Res.string.action_save))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
                .imePadding()
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
                placeholder = { Text(stringResource(Res.string.task_title_placeholder), style = MaterialTheme.typography.headlineMedium) }
            )


            Box(modifier = Modifier.padding(start = 4.dp)) {
                TextButton(
                    onClick = { showCategoryMenu = true },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.group_label, state.category),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                DropdownMenu(
                    expanded = showCategoryMenu,
                    onDismissRequest = { showCategoryMenu = false }
                ) {
                    state.availableCategories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                onAction(TodoDetailScreenAction.OnCategoryChanged(category.name))
                                showCategoryMenu = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

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
                placeholder = { Text(stringResource(Res.string.task_description_placeholder)) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.progress_entries_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            state.progressEntries.forEach { progressEntry ->
                val dateText = remember(progressEntry.timestamp) {
                    val localDateTime = kotlin.time.Instant.fromEpochMilliseconds(progressEntry.timestamp)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    "${localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${localDateTime.day}, ${localDateTime.year} ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
                }

                ListItem(
                    modifier = Modifier.detectGestures(
                        key = progressEntry,
                        onTap = { onAction(TodoDetailScreenAction.OnEditEntryTapped(progressEntry)) },
                        onSwipe = { /* No-op */ },
                        onLongPress = {  onAction(TodoDetailScreenAction.OnDeleteEntryAttempt(progressEntry)) }
                    ),
                    headlineContent = {
                        Text(
                            text = progressEntry.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingContent = {
                        Text(
                            text = dateText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
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
                        text = stringResource(Res.string.add_progress_entry_hint),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_progress_entry_cd),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (state.showAddEntryDialog) {
        AlertDialog(
            onDismissRequest = { onAction(TodoDetailScreenAction.OnDismissAddEntry) },
            title = { Text(if (state.selectedEntryToEdit != null) "Edit Progress Entry" else stringResource(Res.string.new_progress_entry_title)) },
            text = {
                TextField(
                    value = state.newEntryText,
                    onValueChange = { onAction(TodoDetailScreenAction.OnNewEntryTextChanged(it)) },
                    placeholder = { Text(stringResource(Res.string.new_progress_entry_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnConfirmAddEntry) }) {
                    Text(if (state.selectedEntryToEdit != null) "Update" else stringResource(Res.string.action_add))
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnDismissAddEntry) }) {
                    Text(stringResource(Res.string.action_cancel))
                }
            }
        )
    }

    if (state.selectedEntryToDelete != null) {
        AlertDialog(
            onDismissRequest = { onAction(TodoDetailScreenAction.OnDismissDeleteEntry) },
            title = { Text("Delete Entry?") },
            text = { Text("Are you sure you want to delete this progress update?") },
            confirmButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnConfirmDeleteEntry) }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(TodoDetailScreenAction.OnDismissDeleteEntry) }) {
                    Text("Cancel")
                }
            }
        )
    }
}
