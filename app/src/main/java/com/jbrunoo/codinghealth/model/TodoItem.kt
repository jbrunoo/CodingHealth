package com.jbrunoo.codinghealth.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
// vm의 todoItemList가 mutableStateList라서 checkItem 함수 실행해도 recomposition x
// 1. 변경 항목 복사 - list에서 항목 삭제 - 변경 항목 다시 추가
// 2. boolean -> mutableState<Boolean>
// 2-1. data class -> class로 수정 후 위임 프로퍼티 활용 ..