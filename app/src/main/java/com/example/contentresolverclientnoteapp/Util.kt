package com.example.contentresolverclientnoteapp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri


val AUTHORITY = "com.example.contentproviderandconentresolver.provider"
const val NOTES_TABLE = "notes"

// which table we want to expose or what what ever we want to expose
val CONTENT_URI = Uri.parse("content://$AUTHORITY/$NOTES_TABLE")


// content resolver act as client to get data from content provider that act as server.
// because it act as client it can have request body we will create here
fun getAllNotes(context: Context): List<Note> {
    val list = mutableListOf<Note>()
    val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
    cursor?.let {
        while (it.moveToNext()) {
            val id = it.getInt(it.getColumnIndexOrThrow("id"))
            val title = it.getString(it.getColumnIndexOrThrow("title"))
            val desc = it.getString(it.getColumnIndexOrThrow("desc"))
            list.add(Note(id = id, title = title, desc = desc))
        }
    }

    return list
}

// content values are used to create request body that will be used by content resolver to give content provider
fun ContentResolver.insertNot(title:String,desc:String){
    val values = ContentValues()
    values.put("title",title)
    values.put("desc",desc)
    insert(CONTENT_URI,values)
}

fun ContentResolver.update(id:Int,title: String,desc:String){
    val values = ContentValues().apply {
        put("title",title)
        put("desc",desc)
    }

    val updateUri = Uri.parse("content://$AUTHORITY/$NOTES_TABLE/$id")
    update(updateUri,values,null,null)
}


fun ContentResolver.delete(id:Int){
    val deleteUri = Uri.parse("content://$AUTHORITY/$NOTES_TABLE/$id")
    delete(deleteUri,null)
}