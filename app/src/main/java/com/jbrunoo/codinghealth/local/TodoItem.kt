package com.jbrunoo.codinghealth.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val body: String = "",
    val complete: Boolean = false,
    @ColumnInfo(name = "delete", defaultValue = "0") val delete: Boolean = false
)