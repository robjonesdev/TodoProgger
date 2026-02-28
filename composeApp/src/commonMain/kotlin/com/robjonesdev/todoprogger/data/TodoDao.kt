package com.robjonesdev.todoprogger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.robjonesdev.todoprogger.domain.models.TodoTask
import com.robjonesdev.todoprogger.domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_tasks")
    fun getAllTasks(): Flow<List<TodoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoTask)

    @Update
    suspend fun updateTask(task: TodoTask)

    @Query("DELETE FROM todo_tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("DELETE FROM categories WHERE name = :name")
    suspend fun deleteCategory(name: String)
}
