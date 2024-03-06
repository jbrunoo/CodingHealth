package com.jbrunoo.codinghealth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

class TodoItem(
    val id: UUID = UUID.randomUUID(),
    initialBody: String = "",
    initialChecked: Boolean = false
//    var checked: MutableState<Boolean> = mutableStateOf(false)
) {
    var body by mutableStateOf(initialBody)
    var checked by mutableStateOf(initialChecked)
}
// mutableStateList라서 checkedItem 함수 실행해도 recomposition 일어나지 않음
// 1. 변경 항목 복사 - list에서 항목 삭제 - 변경 항목 다시 추가
// 2. boolean -> mutableState<Boolean>
// 2-1. data class -> class로 수정 후 위임 프로퍼티 활용 ..



class TodoViewModel: ViewModel() {
    var uiState = mutableStateListOf<TodoItem>()
        private set

    fun addTodoItem(item: TodoItem) {
        uiState.add(item)
    }

    fun removeTodoItem(item: TodoItem) {
        uiState.remove(item)
    }

    fun undateTodoItem(item: TodoItem, newBody: String) {
        uiState.find { it.id == item.id }?.let {
            it.body = newBody
        }
    }

    fun checkItem(item: TodoItem, checked: Boolean) =
        uiState.find { it.id == item.id }?.let {
//            it.checked.value = checked
            it.checked = checked
    }
}