package com.robjonesdev.todoprogger.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.robjonesdev.todoprogger.domain.models.TodoTask

@Database(entities = [TodoTask::class], version = 1)
@TypeConverters(TodoTypeConverters::class)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

@Suppress("KotlinNoActualForExpect")
expect object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase> {
    override fun initialize(): TodoDatabase
}
