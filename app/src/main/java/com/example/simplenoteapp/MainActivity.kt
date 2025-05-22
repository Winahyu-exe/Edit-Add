package com.example.simplenoteapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simplenoteapp.data.NoteDatabase
import com.example.simplenoteapp.data.NoteRepository
import com.example.simplenoteapp.ui.NoteListFragment
import com.example.simplenoteapp.viewmodel.NoteViewModel
import com.example.simplenoteapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteDao = NoteDatabase.getDatabase(this).noteDao()
        val repository = NoteRepository(noteDao)
        val factory = NoteViewModelFactory(repository)

        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        // Cek apakah data berjalan (opsional untuk debug)
        noteViewModel.allNotes.observe(this) { notes ->
            Log.d("MainActivity", "Data notes: $notes")
        }

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, NoteListFragment())
            .commit()
    }
}
