package com.skanilantech.notepadai.data

import androidx.lifecycle.LiveData
import com.skanilantech.notepadai.database.NoteDao
import com.skanilantech.notepadai.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
}