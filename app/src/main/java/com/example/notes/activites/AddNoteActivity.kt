package com.example.notes.activites

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.NotesDatabaseHelper
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.model.Note

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0,title,content)
            if(title.isEmpty() and content.isEmpty()){
                Toast.makeText(this,"Note is Empty!",Toast.LENGTH_SHORT).show()
            }else {
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Saved Successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}