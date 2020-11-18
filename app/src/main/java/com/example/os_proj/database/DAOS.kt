package com.example.os_proj.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{
    @Query("SELECT * FROM tasks WHERE list_id = :listId ORDER BY list_id ASC")
    fun getTasks(listId: Int): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}

@Dao
interface TaskListDao{
    /*@Query("SELECT * FROM task_lists  ORDER BY id ASC")
    fun getTaskLists(): LiveData<List<Task>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskList: TaskList): Long


    @Delete
    fun delete(taskList: TaskList)

    @Transaction
    @Query("SELECT * FROM task_lists WHERE id = :id LIMIT 1")
    fun getTaskListWithTasks(id: Int): TaskListWithTasks

    @Transaction
    @Query("SELECT * FROM task_lists")
    fun getTaskListWithTasks(): List<TaskListWithTasks>
}

