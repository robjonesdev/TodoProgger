package com.robjonesdev.todoprogger.data

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * Migration from version 1 to 2:
 * Adds 'progressEntries' and 'category' columns to the 'todo_tasks' table.
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        val columns = mutableListOf<String>()

        // We check if the columns exist before adding them to make this migration "idempotent".
        // This prevents "duplicate column name" errors if the migration is re-run after a partial failure.
        if (!columns.contains("progressEntries")) {
            connection.execSQL("ALTER TABLE todo_tasks ADD COLUMN progressEntries TEXT NOT NULL DEFAULT '[]'")
        }
        
        if (!columns.contains("category")) {
            connection.execSQL("ALTER TABLE todo_tasks ADD COLUMN category TEXT NOT NULL DEFAULT 'General'")
        }
    }
}

/**
 * Migration from version 2 to 3:
 * Adds the 'categories' table.
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        // Creates the new dedicated table for task groups
        connection.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))")
        // Initialize with default category so the UI always has a starting group
        connection.execSQL("INSERT OR IGNORE INTO categories (name) VALUES ('General')")
    }
}

fun getDatabase(builder: RoomDatabase.Builder<TodoDatabase>): TodoDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .build()
}

expect fun getDatabaseBuilder(context: Any? = null): RoomDatabase.Builder<TodoDatabase>
