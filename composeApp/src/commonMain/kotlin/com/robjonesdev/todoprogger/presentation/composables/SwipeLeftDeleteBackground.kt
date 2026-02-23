package com.robjonesdev.todoprogger.presentation.composables

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A background composable for the [SwipeToDismissBox].
 * Uses drawBehind to avoid recomposition during swipe gestures.
 */
@Composable
fun SwipeLeftDeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val isDeleting = (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart)
                drawRect(color = if (isDeleting) Color.Red else Color.Transparent)
            }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}
