package com.jbrunoo.codinghealth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbrunoo.codinghealth.local.TodoDao
import com.jbrunoo.codinghealth.local.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// db는 context가 필요하므로 viewmodel에서 초기화 하지 않고 dao를 받아서 처리.
class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    private var _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems = _todoItems.asStateFlow()

    init {
        viewModelScope.launch {
            todoDao.getAll().collect { todos ->
                _todoItems.value = todos
            }
        }
    }

    fun addTodo(todo: TodoItem) {
        viewModelScope.launch {
            todoDao.insert(todo)
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            todoDao.update(todo)
        }
    }

    fun deleteCheckedItems() {
        viewModelScope.launch {
            todoDao.deleteTodos()
        }
    }
}