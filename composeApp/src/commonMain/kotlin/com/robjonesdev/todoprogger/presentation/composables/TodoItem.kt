package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.domain.models.TodoTask

/**
 * A composable that represents a single to-do task item in a list.
 * Displays the title, and an expandable description with action tabs.
 *
 * @param item The [TodoTask] data model to display.
 * @param onToggleComplete Callback triggered when the complete icon is tapped.
 * @param onClick Callback triggered when the item is tapped.
 * @param modifier The modifier to be applied to the entire item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(
    item: TodoTask,
    onToggleComplete: (TodoTask) -> Unit = {},
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null,
                    color = if (item.isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more"
                    )
                }
            }
            
            AnimatedVisibility(visible = expanded) {
                Column {
                    if (item.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SuggestionChip(
                            onClick = { onToggleComplete(item) },
                            label = { Text(if (item.isCompleted) "Completed" else "Complete") },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Mark as complete",
                                    modifier = Modifier.size(SuggestionChipDefaults.IconSize)
                                )
                            },
                            colors = if (item.isCompleted) {
                                SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    iconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            } else {
                                SuggestionChipDefaults.suggestionChipColors()
                            }
                        )
                    }
                }
            }
        }
    }
}
