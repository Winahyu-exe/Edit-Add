package com.example.simplenoteapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplenoteapp.ui.AddEditNoteFragment
import com.example.simplenoteapp.ui.NoteListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)

         supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NoteListFragment())
            .commit()

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, NoteListFragment())
                        .commit()
                    true
                }
                R.id.menu_add_edit -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AddEditNoteFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
