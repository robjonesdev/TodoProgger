package com.robjonesdev.todoprogger.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<TodoDatabase> {
    require(context is Context) { "Android Room requires a Context" }
    val dbFile = context.getDatabasePath("todo_tasks.db")
    return Room.databaseBuilder<TodoDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}
