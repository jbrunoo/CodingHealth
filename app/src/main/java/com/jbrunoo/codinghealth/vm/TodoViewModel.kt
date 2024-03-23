package com.jbrunoo.codinghealth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbrunoo.codinghealth.local.TodoDao
import com.jbrunoo.codinghealth.local.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// db는 context가 필요하므로 viewmodel에서 초기화 하지 않고 dao를 받아서 처리.
class TodoViewModel(private val todoDao: TodoDao): ViewModel() {
    private var _todoItemList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItemList = _todoItemList.asStateFlow()

    init {
        viewModelScope.launch {
            todoDao.getAll().collect { todoList ->
                _todoItemList.value = todoList
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
    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }
    fun deleteTodos() {
        viewModelScope.launch {
            todoDao.deleteAll()
        }
    }
}