package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    private lateinit var db:NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        // below we setup the adapter
        notesAdapter = NotesAdapter(db.getAllNotes() ,this  )
        // use to set the layout
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        // connect adapter to the recycler view
        binding.notesRecyclerView.adapter=  notesAdapter

        binding.addButton.setOnClickListener {
            val intent  = Intent(this , AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
    // used to automatically refresh the data
    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}