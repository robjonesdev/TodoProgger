package com.robjonesdev.todoprogger.domain.models

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * A sub-entry in a [TodoTask] that represents a step or partial accomplishment.
 */
@Serializable
data class ProgressEntry @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val description: String,
    val isCompleted: Boolean = false,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)
