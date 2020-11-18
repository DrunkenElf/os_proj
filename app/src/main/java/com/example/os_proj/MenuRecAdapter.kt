package com.example.os_proj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.os_proj.database.Task
import com.example.os_proj.viewModel.MainViewModel


class MenuRecAdapter(
    val context: Context, val tasks: List<Task>,
    val model: MainViewModel,
) :
    RecyclerView.Adapter<MenuRecAdapter.MenuViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(this.context).inflate(R.layout.task_row_edit_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindItems()
        holder.btnDel.setOnClickListener {
            model.delete(tasks[position])
        }
        holder.btnUpd.setOnClickListener {
            model.update(tasks[position].copy(
                name = holder.editName.text.toString(),
                description = holder.editDesc.text.toString(),
            ))
        }

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editName = itemView.findViewById<EditText>(R.id.edit_name)
        val editDesc = itemView.findViewById<EditText>(R.id.edit_desc)
        val btnUpd = itemView.findViewById<Button>(R.id.btn_update)
        val btnDel = itemView.findViewById<Button>(R.id.btn_delete)


        fun bindItems() {
            editName.setText(  tasks[adapterPosition].name)
            editDesc.setText(  tasks[adapterPosition].description)

        }

    }
}