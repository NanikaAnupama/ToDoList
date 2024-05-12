package com.example.todolist

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Adapter.ToDoAdapter
import com.example.todolist.Model.ToDoModel
import com.example.todolist.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections

class MainActivity : AppCompatActivity() {
    lateinit var tasksAdapter: ToDoAdapter
    lateinit var taskList: List<ToDoModel>
    lateinit var db: DatabaseHandler
    lateinit var fab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



        val tasksRecyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)









        db = DatabaseHandler(this)
        db.openDatabase()

        taskList = db.getAllTasks()
        Collections.reverse(taskList)
        tasksAdapter = ToDoAdapter(db,taskList,this)
        tasksRecyclerView.adapter = tasksAdapter

//        tasksAdapter.setTasks(taskList)

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }





    }

    fun handleDialogClose() {
        var taskList: MutableList<ToDoModel> = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}