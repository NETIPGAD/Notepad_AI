package com.skanilantech.notepadai.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skanilantech.notepadai.databinding.ItemNoteBinding
import com.skanilantech.notepadai.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private val onClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.tvTitle.text = note.title.ifBlank { "Untitled" }
            binding.tvContent.text = note.content.ifBlank { "No content" }
            binding.tvTimestamp.text = formatTimestamp(note.timestamp)
            binding.root.setOnClickListener { onClick(note) }
        }
    }

    private fun formatTimestamp(time: Long): String {
        val diff = System.currentTimeMillis() - time
        return when {
            diff < 60_000 -> "Just now"
            diff < 3_600_000 -> "${diff / 60_000}m ago"
            diff < 86_400_000 -> "${diff / 3_600_000}h ago"
            else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(time))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}