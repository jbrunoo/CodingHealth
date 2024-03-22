package com.jbrunoo.codinghealth.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todo: TodoItem)

    @Delete
    suspend fun deleteTodo(user: TodoItem)

    @Query("DELETE FROM todo")
    suspend fun deleteTodos()
}