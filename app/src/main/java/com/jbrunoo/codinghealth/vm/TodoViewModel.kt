package com.jbrunoo.codinghealth.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jbrunoo.codinghealth.model.TodoItem


class TodoViewModel: ViewModel() {
    var todoItemList = mutableStateListOf<TodoItem>()
        private set

    fun addTodoItem(item: TodoItem) {
        todoItemList.add(item)
    }

    fun removeTodoItem(item: TodoItem) {
        todoItemList.remove(item)
    }

    fun undateTodoItem(item: TodoItem, newBody: String) {
        todoItemList.find { it.id == item.id }?.let {
            it.body = newBody
        }
    }

    fun checkItem(item: TodoItem, checked: Boolean) =
        todoItemList.find { it.id == item.id }?.let {
//            it.checked.value = checked
            it.checked = checked
    }
}