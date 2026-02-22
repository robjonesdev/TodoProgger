package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A background composable for the [SwipeToDismissBox].
 * Only displays the delete icon and red background when swiping from end to start.
 */
@Composable
fun SwipeLeftDeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val isDeleting = (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart)

    val backgroundColor = if (isDeleting) Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (isDeleting) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}
