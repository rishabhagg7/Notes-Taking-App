package com.example.notes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.NotesDatabaseHelper
import com.example.notes.R
import com.example.notes.activites.UpdateNoteActivity
import com.example.notes.model.Note

class NoteAdapter: ListAdapter<Note,NoteAdapter.NoteViewHolder>(DiffCallBack) {

    private lateinit var db: NotesDatabaseHelper

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        fun bind(note: Note){
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
            titleTextView.text = note.title
            contentTextView.text = note.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        db = NotesDatabaseHelper(parent.context)
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id",getItem(position).id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteNote(getItem(position).id)
            submitList(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id",getItem(position).id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return areItemsTheSame(oldItem,newItem)
            }

        }
    }
}