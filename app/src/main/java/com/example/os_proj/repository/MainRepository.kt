package com.example.os_proj.repository

import android.content.Context
import android.util.Log
import com.example.os_proj.database.TaskDao
import com.example.os_proj.database.TaskListDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(
    val context: Context,
    val taskListDao: TaskListDao,
    val taskDao: TaskDao,
){

    fun fetchTaskLists() = taskListDao.getTaskListWithTasks().also {
        it.forEach{ Log.d("getTasks:", it.toString()) }
    }

    fun getTaskList(tasklistId: Int)
    = taskListDao.getTaskListWithTasks(tasklistId)

}

/*
* CoroutineScope(Dispatchers.IO).launch {
            taskListDao.
        }
        * */