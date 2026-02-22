package com.robjonesdev.todoprogger.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.robjonesdev.todoprogger.domain.models.TodoTask

@Database(entities = [TodoTask::class], version = 1)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

// Room uses this to generate the actual implementation in KMP, we do not need to generate
// actuals for this method
@Suppress("KotlinNoActualForExpect")
expect object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase> {
    override fun initialize(): TodoDatabase
}
