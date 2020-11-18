package com.example.os_proj.database

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "list_id") val listId: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
): Parcelable

@Parcelize
@Keep
@Entity(tableName = "task_lists")
data class TaskList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
) : Parcelable

data class TaskListWithTasks(
    @Embedded
    val taskList: TaskList,
    @Relation(
        parentColumn = "id",
        entityColumn = "list_id",
    )
    val tasks: List<Task>
)