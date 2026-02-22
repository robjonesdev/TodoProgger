package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robjonesdev.todoprogger.data.TodoDao
import com.robjonesdev.todoprogger.domain.models.TodoTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoListViewModel(private val todoDao: TodoDao) : ViewModel() {

    val todoTasks: StateFlow<List<TodoTask>> = todoDao.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNewTodo() {
        viewModelScope.launch {
            val newTodo = TodoTask()
            todoDao.insertTask(newTodo)
        }
    }

    fun updateTask(updatedTask: TodoTask) {
        viewModelScope.launch {
            todoDao.updateTask(updatedTask)
        }
    }

    fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            todoDao.deleteTask(task.id)
        }
    }
}
