package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> AnimatedSwipeToDeleteContainer(
    item: T,
    isRemoved: Boolean = false,
    onSwipeToDelete: (T) -> Unit,
    animationDuration: Int = 500,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onSwipeToDelete(item)
            }
            false
        }
    )

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut(),
        modifier = modifier,
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                SwipeLeftDeleteBackground(swipeDismissState = state)
            },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
        ) {
            content(item)
        }
    }
}
