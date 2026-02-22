package com.robjonesdev.todoprogger.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<TodoDatabase> {
    val dbFilePath = documentDirectory() + "/todo_tasks.db"
    return Room.databaseBuilder<TodoDatabase>(
        name = dbFilePath,
        factory = { TodoDatabaseConstructor.initialize() }
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase>
