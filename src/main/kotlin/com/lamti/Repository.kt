package com.lamti

import com.lamti.plugins.Note

class Repository {

    private val dataList = MutableList(10) {
        Note(
            id = it + 1,
            title = "Note ${it + 1}",
            text = "Note ${it + 1} text",
            completed = (it + 1).rem(2) == 0
        )
    }

    fun getNotes(userID: String): Any = if (userID == USER_1) dataList else "No user found!"

    fun addNote(userID: String, note: Note): Any = if (userID == USER_1) {
        val newNote = note.copy(id = dataList.size + 1)
        dataList.add(newNote)
        println("New post added: $newNote")
        newNote
    } else
        "No user found!"

    fun updateNote(userID: String, note: Note): Any = if (userID == USER_1) {
        dataList.firstOrNull { it.id == note.id }?.let {
            val index = dataList.indexOf(it)
            dataList[index] = note
            println("Post updated: $note")
        }
        note
    } else
        "No user found!"

    fun deleteNote(userID: String, note: Note): Boolean = if (userID == USER_1) {
        dataList.remove(note)
        println("Note deleted: $note")
        true
    } else
        false

    companion object {

        private const val USER_1 = "user1"
    }
}