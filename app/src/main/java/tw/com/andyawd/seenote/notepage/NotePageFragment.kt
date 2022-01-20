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
import androidx.recyclerview.widget.GridLayoutManager
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
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
        binding.fnpRvNoteList.layoutManager = GridLayoutManager(application, 1)

        adapter.setOnItemClickListener(NotePageListener { id ->
            AWDLog.d("新的資料庫id: $id")
            notePageViewModel.onItemClicked(id)
        })

        notePageViewModel.note.observe(viewLifecycleOwner, Observer {
            //adapter.submitList(it)
            adapter.addHeaderAndSubmitList(it)
        })

        notePageViewModel.notePageDetail.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                findNavController().navigate(
                    NotePageFragmentDirections.actionNotePageFragmentToWriteNoteFragment(
                        noteId
                    )
                )
                notePageViewModel.onNotePageNavigated()
            }
        })

        binding.fnpMbWriteNote.setOnClickListener {
            findNavController().navigate(
                NotePageFragmentDirections.actionNotePageFragmentToWriteNoteFragment(
                    BaseConstants.CREATE_NOTE
                )
            )
        }

        binding.fnpMbSettingNote.setOnClickListener {
            val action = NotePageFragmentDirections.actionNotePageFragmentToSettingNoteFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    companion object {

    }
}

