package com.example.os_proj.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random


@Database(entities = [Task::class, TaskList::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
   // abstract fun userDao(): UserDao

    abstract fun tasklistDao(): TaskListDao

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "database.db")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                   /* val taskListDao = instance?.tasklistDao()
                    CoroutineScope(Dispatchers.IO).launch {
                        val temp = listOf("a", "b", "c")
                        temp.forEach {
                            val id = instance?.tasklistDao()
                                ?.insert(TaskList(name = it, description = "description"))
                            id?.let {
                                repeat(5){
                                    instance?.taskDao()?.insert(
                                        Task(
                                            listId = id.toInt(),
                                            name = "name${Random.nextInt(50)}",
                                            description = "descpsdfsdfs"
                                        )
                                    )
                                }
                            }
                        }
                        *//* subjects.forEach { instance?.subjectDao()?.insert(it) }
                        val titles = context.resources.getStringArray(R.array.subjects)
                        val hrefs = context.resources.getStringArray(R.array.subjects_prefix)
                        titles.zip(hrefs).forEach {
                            Log.d("first   second", it.first +  " "+it.second)
                            instance?.subjectMainDao()?.insert(SubjectMain(it.first, it.second))
                        }*//*
                    }*/
                }
            })
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }
}