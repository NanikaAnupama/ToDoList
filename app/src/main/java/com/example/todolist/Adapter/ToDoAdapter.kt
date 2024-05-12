package com.example.todolist.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.AddNewTask
import com.example.todolist.MainActivity
import com.example.todolist.Model.ToDoModel
import com.example.todolist.R
import com.example.todolist.Utils.DatabaseHandler
import android.widget.CompoundButton

class ToDoAdapter(private var db:DatabaseHandler,private var todoList: List<ToDoModel>, private val activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = todoList[position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)

        holder.task.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                db.updateStatus(item.id, 1)
            } else {
                db.updateStatus(item.id, 0)
            }
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setTasks(todoList: List<ToDoModel>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun getContext(): Context {
        return activity
    }

    fun deleteItem(position: Int) {
//        val item = todoList[position]
//        db.deleteTask(item.id)
//        todoList.removeAt(position)
//        notifyItemRemoved(position)
        val item = todoList[position]
        db.deleteTask(item.id)
        todoList = db.getAllTasks()
        notifyDataSetChanged()
    }


    fun editItem(position: Int) {
        val item = todoList[position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }
        val fragment = AddNewTask().apply {
             arguments = bundle
        }

        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: CheckBox = itemView.findViewById(R.id.todoCheckBox)
    }
}
