package com.example.simplenoteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplenoteapp.R
import com.example.simplenoteapp.data.Note
import com.example.simplenoteapp.data.NoteDatabase
import com.example.simplenoteapp.data.NoteRepository
import com.example.simplenoteapp.databinding.FragmentAddEditNoteBinding
import com.example.simplenoteapp.viewmodel.NoteViewModel
import com.example.simplenoteapp.viewmodel.NoteViewModelFactory

class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var existingNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]


        val id = arguments?.getInt("id") ?: 0
        val title = arguments?.getString("title") ?: ""
        val description = arguments?.getString("description") ?: ""
        existingNote = if (id != 0) {
            Note(id = id, title = title, description = description)
        } else {
            null
        }

        if (existingNote != null) {
            binding.etTitle.setText(existingNote!!.title)
            binding.etDescription.setText(existingNote!!.description)
        }


        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val desc = binding.etDescription.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (existingNote == null) {
                val newNote = Note(title = title, description = desc)
                viewModel.insert(newNote)
                Toast.makeText(requireContext(), "Note ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                val updatedNote = existingNote!!.copy(title = title, description = desc)
                viewModel.update(updatedNote)
                Toast.makeText(requireContext(), "Note diperbarui", Toast.LENGTH_SHORT).show()
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NoteListFragment())
                .commit()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int, title: String, description: String): AddEditNoteFragment {
            val fragment = AddEditNoteFragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putString("title", title)
            args.putString("description", description)
            fragment.arguments = args
            return fragment
        }
    }

}
