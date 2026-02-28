package com.robjonesdev.todoprogger.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.models.Category

@Database(entities = [TodoTask::class, Category::class], version = 3)
@TypeConverters(TodoTypeConverters::class)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

expect object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase> {
    override fun initialize(): TodoDatabase
}
