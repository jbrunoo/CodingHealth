package com.jbrunoo.codinghealth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbrunoo.codinghealth.data.local.TodoDao
import com.jbrunoo.codinghealth.data.local.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// db는 context 필요, dao를 받아서 처리. 차후 repo, useCase
@HiltViewModel
class TodoViewModel @Inject constructor(private val todoDao: TodoDao) : ViewModel() {
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