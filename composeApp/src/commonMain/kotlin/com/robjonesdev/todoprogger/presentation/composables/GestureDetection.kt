package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

/**
 * A reusable [Modifier] extension for detecting and differentiating between common gestures
 * (taps, horizontal swipes, and long presses) within a scrollable or interactive container.
 *
 * This implementation is used to overcome common gesture conflicts in Compose (e.g., within a TabRow)
 * where high-level components might "steal" events or ignore taps that exceed standard timing thresholds.
 *
 * It ensures expected user behavior by:
 * 1. Explicitly classifying movement beyond the system's "touch slop" as a [onSwipe] event,
 *    allowing the parent container to handle scrolling.
 * 2. Classifying any release before the 500ms timeout (that isn't a swipe) as a [onTap].
 * 3. Triggering [onLongPress] only if the finger remains still for the full duration.
 * 4. Preventing "ghost events" by waiting for final pointer release after a long press is detected.
 *
 * @param key A key used to identify the pointer input instance.
 * @param onTap Callback triggered when a quick tap is detected.
 * @param onSwipe Callback triggered when a swipe/pan movement is detected (exceeding touch slop).
 * @param onLongPress Callback triggered when the finger is held for 500ms without significant movement.
 */
fun <T : Any> Modifier.detectGestures(
    key: T,
    onTap: (T) -> Unit = {},
    onSwipe: (T) -> Unit = {},
    onLongPress: (T) -> Unit = {},
): Modifier {
    return this.pointerInput(key) {
        // touchSlop is the system-defined threshold (in pixels) that a finger
        // must move before the gesture is classified as a 'scroll' instead of a 'tap'.
        val slop = viewConfiguration.touchSlop

        awaitEachGesture {
            // Wait for the initial finger down.
            // We use requireUnconsumed = false because the parent Tab component
            // might also be watching these events; we want to see them regardless.
            awaitFirstDown(requireUnconsumed = false)
            var panSum = Offset.Zero

            // Start a timer. If 500ms passes without the finger lifting OR
            // moving beyond the 'slop' threshold, it is a Long Press.
            val result = withTimeoutOrNull(500L) {
                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.first()

                    // Track total movement distance
                    panSum += change.position - change.previousPosition

                    // If the user moves their finger more than the 'slop', they are trying
                    // to scroll the TabRow. We return 'SWIPE' to cancel our internal
                    // Tap/Long-Press logic and let the parent handle the scroll.
                    if (panSum.getDistance() > slop) {
                        return@withTimeoutOrNull "SWIPE"
                    }

                    // If the finger is lifted (pressed == false) before the 500ms
                    // timeout, it is a Tap.
                    if (!change.pressed) {
                        // consume() tells other gesture filters that we have
                        // handled this event.
                        change.consume()
                        return@withTimeoutOrNull "TAP"
                    }
                }
            }

            // Decide which action to take based on the result of the timer/movement
            when (result) {
                "TAP" -> {
                    onTap(key)
                }

                "SWIPE" -> {
                    onSwipe(key)
                }

                null -> {
                    // Timeout was reached (500ms) and the finger didn't move: This is a Long Press.
                    onLongPress(key)

                    // We must wait for the user to finally lift their finger
                    // before finishing this gesture loop. If we don't, the loop restarts
                    // immediately while the finger is still down, which can trigger
                    // accidental taps or ghost events.
                    waitForUpOrCancellation()
                }
            }
        }
    }
}