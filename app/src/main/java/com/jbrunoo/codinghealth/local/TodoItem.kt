package com.jbrunoo.codinghealth.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "todo")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val body: String = "",
    val checked: Boolean = false
)