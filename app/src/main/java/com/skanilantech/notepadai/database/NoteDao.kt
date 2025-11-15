package com.skanilantech.notepadai.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.skanilantech.notepadai.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)
}