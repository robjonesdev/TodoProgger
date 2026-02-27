package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.presentation.actions.TodoListScreenAction
import com.robjonesdev.todoprogger.presentation.state.TodoListState

/**
 * A list of [TodoTask] items with swipe-to-delete functionality.
 *
 * @param state The current [TodoListState] containing the items and UI state.
 * @param action Callback triggered for various user actions on the list.
 * @param modifier The modifier to be applied to the underlying [LazyColumn].
 */
@Composable
fun TodoList(
    state: TodoListState,
    action: (TodoListScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState: LazyListState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(state.items, key = { it.id }) { item ->
            AnimatedSwipeToDeleteContainer(
                item = item,
                isRemoved = (state.selectedDeletionCandidate?.id == item.id),
                onSwipeToDelete = {
                    action(TodoListScreenAction.OnSwipeToDelete(item))
                },
                modifier = Modifier.animateItem(),
            ) { task ->
                TodoItem(
                    item = task,
                    expanded = task.id in state.expandedItemIDs,
                    onToggleExpanded = {
                        action(TodoListScreenAction.OnToggleExpanded(task.id))
                    },
                    onToggleComplete = { 
                        action(TodoListScreenAction.OnToggleCompleted(task))
                    },
                    onScheduleReminder = { 
                        action(TodoListScreenAction.OnScheduleReminder(task))
                    },
                    onClick = { 
                        action(TodoListScreenAction.OnItemTapped(task))
                    }
                )
            }
        }
    }

    if(state.showConfirmDeletionDialog) {
        ConfirmDeletionDialog(
            todoName = state.selectedDeletionCandidate?.title ?: "Unknown",
            onRejectDeletion = {
                action(TodoListScreenAction.OnRejectDeletion)
           },
        ) {
            action(TodoListScreenAction.OnConfirmDeletion)
        }
    }
}
