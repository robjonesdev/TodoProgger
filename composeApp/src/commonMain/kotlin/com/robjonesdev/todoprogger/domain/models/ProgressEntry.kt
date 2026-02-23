package com.robjonesdev.todoprogger.domain.models

import kotlinx.serialization.Serializable

/**
 * A sub-entry in a [TodoTask] that represents a step or partial accomplishment.
 */
@Serializable
data class ProgressEntry(
    val id: Int,
    val description: String,
    val isCompleted: Boolean = false,
)
