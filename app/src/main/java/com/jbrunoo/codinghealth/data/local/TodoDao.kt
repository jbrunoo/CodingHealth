package com.jbrunoo.codinghealth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo WHERE id = :id") // :id가 함수 매개변수를 가리킴
    fun getTodoById(id: Long): Flow<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoItem)

    //update는 기본키를 알려주어야 동작
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: TodoItem)

    @Query("DELETE FROM todo WHERE `delete` = 1")
    suspend fun deleteTodos()

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}