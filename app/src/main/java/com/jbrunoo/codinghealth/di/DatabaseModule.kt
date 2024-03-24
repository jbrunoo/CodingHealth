package com.jbrunoo.codinghealth.di

import android.content.Context
import com.jbrunoo.codinghealth.data.local.TodoDao
import com.jbrunoo.codinghealth.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase =
        TodoDatabase.getDatabase(context = context)

    @Singleton
    @Provides
    fun getTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()
}