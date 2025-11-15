package com.skanilantech.notepadai.ui.editor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.skanilantech.notepadai.databinding.ActivityNoteEditorBinding
import com.skanilantech.notepadai.model.Note
import com.skanilantech.notepadai.viewmodel.NoteViewModel

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding
    private val viewModel: NoteViewModel by viewModels()
    private var noteId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("NOTE_ID", -1)
        isEditMode = noteId != -1

        if (isEditMode) loadNote()

        binding.btnSave.setOnClickListener { saveNote() }
    }

    private fun loadNote() {
        viewModel.allNotes.observe(this) { notes ->
            val note = notes.find { it.id == noteId } ?: return@observe
            binding.etTitle.setText(note.title)
            binding.etContent.setText(note.content)
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            id = if (isEditMode) noteId else 0,
            title = title.ifBlank { "Untitled" },
            content = content
        )

        if (isEditMode) viewModel.update(note) else viewModel.insert(note)
        finish()
    }
}