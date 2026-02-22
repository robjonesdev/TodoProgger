package com.robjonesdev.todoprogger.data

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getDatabase(builder: RoomDatabase.Builder<TodoDatabase>): TodoDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

expect fun getDatabaseBuilder(context: Any? = null): RoomDatabase.Builder<TodoDatabase>