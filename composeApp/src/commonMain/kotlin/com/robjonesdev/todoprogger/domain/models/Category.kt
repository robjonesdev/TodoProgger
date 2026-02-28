package com.robjonesdev.todoprogger.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a group or tab used to organize [TodoTask] items.
 *
 * @property name The unique name of the category, which serves as the primary key.
 */
@Entity(tableName = "categories")
@Serializable
data class Category(
    @PrimaryKey val name: String
)
