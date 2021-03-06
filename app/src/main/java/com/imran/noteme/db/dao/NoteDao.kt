package com.imran.noteme.db.dao

import androidx.room.*
import com.imran.noteme.db.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAllNote(): List<Note>

    @Query("SELECT * FROM note WHERE status = :status")
    suspend fun getTaskByStatus(status: String): List<Note>

    @Delete
    suspend fun deleteNote(note: Note)

}