package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.utils.Logger
import com.robjonesdev.todoprogger.domain.utils.e

/**
 * A list of [TodoTask] items with swipe-to-delete functionality.
 *
 * @param items The list of [TodoTask] objects to display.
 * @param onItemTapped Callback triggered when an item is tapped.
 * @param onDelete Callback triggered when an item is swiped to delete.
 * @param modifier The modifier to be applied to the underlying [LazyColumn].
 * @param listState The state of the [LazyColumn].
 */
@Composable
fun TodoList(
    items: List<TodoTask>,
    onItemTapped: (TodoTask) -> Unit,
    onDelete: (TodoTask) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {

    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    var selectedDeletionCandidate by remember { mutableStateOf<TodoTask?>(null) }

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items, key = { it.id }) { item ->
            AnimatedSwipeToDeleteContainer(
                item = item,
                isRemoved = (selectedDeletionCandidate?.id == item.id),
                onSwipeToDelete = {
                    showConfirmDeletionDialog = true
                    selectedDeletionCandidate = item
                },
                modifier = Modifier.animateItem(),
            ) { task ->
                Box(
                    modifier = Modifier
                        .clickable { onItemTapped(task) }
                ) {
                    TodoItem(item = task)
                }
            }
        }
    }

    if(showConfirmDeletionDialog) {
        ConfirmDeletionDialog(
            onRejectDeletion = {
                showConfirmDeletionDialog = false
                selectedDeletionCandidate = null
           },
        ) {
            onDelete(
                selectedDeletionCandidate ?: run {
                    Logger.e("ConfirmDeletionDialog", "No item selected for deletion")
                    return@ConfirmDeletionDialog
                }
            )
            showConfirmDeletionDialog = false
            selectedDeletionCandidate = null
        }
    }
}
