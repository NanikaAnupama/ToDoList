package com.example.todolist

import android.content.DialogInterface

interface DialogCloseListner{
    fun handleDialogClose(dialog: DialogInterface)
}