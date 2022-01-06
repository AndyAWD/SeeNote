package tw.com.andyawd.seenote.notepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.NoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentNotePageBinding


class NotePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentNotePageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_page,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = NotePageViewModelFactory(dataSource, application)
        val notePageViewModel =
            ViewModelProvider(this, viewModelFactory)[NotePageViewModel::class.java]

        binding.notePageViewModel = notePageViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = NotePageAdapter()
        binding.fnpRvNoteList.adapter = adapter

        notePageViewModel.note.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.fnpMbWriteNote.setOnClickListener {
            findNavController().navigate(R.id.action_notePageFragment_to_writeNoteFragment)
        }

        return binding.root
    }

    companion object {

    }
}