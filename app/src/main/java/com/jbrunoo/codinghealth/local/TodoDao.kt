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
    fun getAll(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(user: TodoItem)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}