package com.imran.noteme.db.repository

import com.imran.noteme.db.NoteDatabase
import com.imran.noteme.db.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(note: Note) {
        db.noteDao.insertNote(note)
    }

    suspend fun getAllNote(): List<Note> {
        return db.noteDao.getAllNote()
    }

    suspend fun getTaskByStatus(status: String): List<Note> {
        return db.noteDao.getTaskByStatus(status)
    }

}