package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddNoteBinding
    private lateinit var db:NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // here we initialize our database class
        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            // below we take input from the user
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0 , title , content)
            db.insertNote(note)
            // finish will end the activity
            finish()
            Toast.makeText(this , "Note Saved" , Toast.LENGTH_SHORT).show()
        }
    }
}