package com.robjonesdev.todoprogger.domain.models

/**
 * A sub-entry in a [TodoTask] that represents a step or partial accomplishment.
 * [ProgressEntry] instances always have a parent [TodoTask] with which they are associated.
 */
data class ProgressEntry(
    val parent: TodoTask,
    val id: Int,
    val description: String,
    val isCompleted: Boolean,
)
