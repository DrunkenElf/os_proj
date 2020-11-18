package com.example.os_proj

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.os_proj.database.Task
import com.example.os_proj.database.TaskList
import tellh.com.recyclertreeview_lib.LayoutItemType
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder


class ChildBinder : TreeViewBinder<ChildBinder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun bindView(holder: ViewHolder, i: Int, treeNode: TreeNode<*>) {
        val child: TasklistChild = treeNode.content as TasklistChild

        if (!child.task.isDone) holder.tv.setTextColor(Color.GRAY)
        else holder.tv.setTextColor(Color.BLACK)
        holder.tv.text = child.task.name
    }

    override fun getLayoutId() = R.layout.main_tasklist_child


    inner class ViewHolder(rootview: View) : TreeViewBinder.ViewHolder(rootview) {
        val tv = rootview.findViewById<TextView>(R.id.title)
    }
}

class HeadBinder : TreeViewBinder<HeadBinder.ViewHolder>() {
    override fun provideViewHolder(itemView: View) = ViewHolder(itemView)

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val head: TasklistHead = node.content as TasklistHead
        holder.addId(head.taskList.id)
        holder.tv.text = head.taskList.name
    }

    override fun getLayoutId(): Int {
        return R.layout.main_tasklist_parent
    }

    inner class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val tv = rootView.findViewById<TextView>(R.id.title)
        val btn = rootView.findViewById<Button>(R.id.edit)
    }
    fun ViewHolder.addId(id: Int){
        btn.apply {
            setOnClickListener {
                Log.d("listID: ", id.toString())
                Navigation.findNavController(this).navigate(
                    MainFragmentDirections.actionMainFragmentToEditFragment(id.toString())
                )
            }
        }
    }
}

class TasklistHead(val taskList: TaskList) : LayoutItemType {

    override fun getLayoutId(): Int {
        return R.layout.main_tasklist_parent
    }
}

class TasklistChild(val task: Task) :
    LayoutItemType {

    override fun getLayoutId(): Int {
        return R.layout.main_tasklist_child
    }
}
