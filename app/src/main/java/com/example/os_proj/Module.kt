package com.example.os_proj

import android.content.Context
import com.example.os_proj.database.AppDatabase
import com.example.os_proj.database.TaskDao
import com.example.os_proj.database.TaskListDao
import com.example.os_proj.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object DbModule {

    @Provides
    fun provideTaskDao(@ApplicationContext context: Context): TaskDao = AppDatabase(context).taskDao()
    @Provides
    fun provideTaskListDao(@ApplicationContext context: Context): TaskListDao = AppDatabase(context).tasklistDao()

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase = AppDatabase(context)

}


@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(@ApplicationContext context: Context, taskListDao: TaskListDao,
        taskDao: TaskDao) =
        MainRepository(context, taskListDao, taskDao)


}
