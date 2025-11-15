package com.skanilantech.notepadai.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skanilantech.notepadai.databinding.ActivityDashboardBinding
import com.skanilantech.notepadai.ui.editor.NoteEditorActivity
import com.skanilantech.notepadai.viewmodel.NoteViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeNotes()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, NoteEditorActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter { note ->
            val intent = Intent(this, NoteEditorActivity::class.java).apply {
                putExtra("NOTE_ID", note.id)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = this@DashboardActivity.adapter
        }
    }

    private fun observeNotes() {
        viewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }
    }
}