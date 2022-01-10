package tw.com.andyawd.seenote.writenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.NoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentWriteNoteBinding

class WriteNoteFragment : Fragment() {

    private lateinit var writeNoteViewModel: WriteNoteViewModel
    private lateinit var binding: FragmentWriteNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_write_note,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory =
            WriteNoteViewModelFactory(dataSource, application, BaseConstants.CREATE_NOTE)
        writeNoteViewModel =
            ViewModelProvider(this, viewModelFactory)[WriteNoteViewModel::class.java]

        binding.lifecycleOwner = this
        binding.writeNoteViewModel = writeNoteViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fwnMtBar.setNavigationOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        if (isEntryValid()) {
            writeNoteViewModel.addNote(
                binding.fwnTietNoteTitle.text.toString(),
                binding.fwnTietNoteContent.text.toString()
            )
        }

        val action = WriteNoteFragmentDirections.actionWriteNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    private fun isEntryValid(): Boolean {
        return writeNoteViewModel.isEntryValid(
            binding.fwnTietNoteTitle.text.toString(),
            binding.fwnTietNoteContent.text.toString()
        )
    }

    companion object {

    }
}