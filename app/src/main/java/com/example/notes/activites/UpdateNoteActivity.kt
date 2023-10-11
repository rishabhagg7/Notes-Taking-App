package com.example.notes.activites

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.NotesDatabaseHelper
import com.example.notes.databinding.ActivityNoteUpdateBinding
import com.example.notes.model.Note

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNodeByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedNote = Note(noteId,newTitle,newContent)
            if(newTitle.isEmpty() && newContent.isEmpty()){
                Toast.makeText(this,"Note is Empty!",Toast.LENGTH_SHORT).show()
            }else{
                db.updateNote(updatedNote)
                finish()
                Toast.makeText(this,"Changes Saved!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}