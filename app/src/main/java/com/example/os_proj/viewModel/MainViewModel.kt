package com.example.os_proj.viewModel

import android.app.Application
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.os_proj.TasklistChild
import com.example.os_proj.TasklistHead
import com.example.os_proj.database.Task
import com.example.os_proj.database.TaskList
import com.example.os_proj.database.TaskListWithTasks
import com.example.os_proj.repository.MainRepository
import kotlinx.coroutines.*
import tellh.com.recyclertreeview_lib.LayoutItemType
import tellh.com.recyclertreeview_lib.TreeNode

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    @Assisted private val savedState: SavedStateHandle, application: Application,
) : AndroidViewModel(application) {
    private lateinit var listId: String

    private val _taskLists = MutableLiveData<List<TaskListWithTasks>>()
    val tasksList: LiveData<List<TaskListWithTasks>>
        get() = _taskLists

    fun fetchTaskLists() = CoroutineScope(Dispatchers.IO).launch {
        delay(1000L)
        val res = repository.fetchTaskLists()
        //_taskLists.postValue(res)
        _taskLists.postValue(res)
    }

    private val _nodes = MutableLiveData<List<TreeNode<*>>>()
    val nodes: LiveData<List<TreeNode<*>>> get() = _nodes

    init {
        tasksList.observeForever {
            _nodes.value = emptyList()
            val temp = mutableListOf<TreeNode<*>>()
            it.forEach {
                val head = TreeNode<TasklistHead>(TasklistHead(it.taskList))
                temp.add(head)
                it.tasks.forEach { task ->
                    head.addChild(TreeNode<TasklistChild>(TasklistChild(task)))
                }
            }
            _nodes.value = temp
        }
        fetchTaskLists()
    }

    fun getNodes(id: String?){
        id?.let {
            listId = it
            fetchTaskLists()
        }
        Log.d("getNodes: ", id.toString())

    }


    private fun fetchNodes(){
        tasksList.observeForever {
            _nodes.value = emptyList()
            val temp = mutableListOf<TreeNode<*>>()
            it.forEach {
                val head = TreeNode<TasklistHead>(TasklistHead(it.taskList))
                temp.add(head)
                it.tasks.forEach { task ->
                    head.addChild(TreeNode<TasklistChild>(TasklistChild(task)))
                }
            }
            _nodes.value = temp
        }
    }

    private val _tasklist = MutableLiveData<TaskList>()
    val taskList: LiveData<TaskList> get() =  _tasklist


    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() =  _tasks



    fun openEdit(){
        CoroutineScope(Dispatchers.IO).launch {
            delay(500L)

            val resp = repository.getTaskList(listId.toInt())
            _tasklist.postValue(resp.taskList)
            _tasks.postValue(resp.tasks)
        }
    }

    fun update(name: String, desc: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.taskListDao.insert(taskList.value!!.copy(name = name, description =  desc))
        openEdit()
    }

    fun update(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.taskDao.update(task)
        openEdit()
    }
    fun delete(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.taskDao.delete(task)
        openEdit()
    }

    suspend fun createTemp(): Int{

            val id = repository.taskListDao.insert(TaskList(name = "placeholder", description = "desc"))

            openEdit()
            return id.toInt()

    }
}