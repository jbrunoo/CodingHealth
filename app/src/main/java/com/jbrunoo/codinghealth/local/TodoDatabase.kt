package com.jbrunoo.codinghealth.local

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").build()
            }
        }
    }
}