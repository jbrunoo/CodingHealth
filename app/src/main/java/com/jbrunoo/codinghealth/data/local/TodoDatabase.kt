package com.jbrunoo.codinghealth.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [TodoItem::class],
    version = 3,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = TodoDatabase.MyAutoMigration::class
        ),
        AutoMigration(from = 2, to = 3) // 열 추가는 자동인데 기본 값 설정 시 entity에서 default value 설정 해야 함
    ]
)
abstract class TodoDatabase : RoomDatabase() {
    @RenameColumn(tableName = "todo", fromColumnName = "checked", toColumnName = "complete")
    class MyAutoMigration : AutoMigrationSpec

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