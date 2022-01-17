package tw.com.andyawd.seenote.writenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.NoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentWriteNoteBinding

class WriteNoteFragment : Fragment() {

    private lateinit var writeNoteViewModel: WriteNoteViewModel
    private lateinit var binding: FragmentWriteNoteBinding

    private val args: WriteNoteFragmentArgs by navArgs()

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
            WriteNoteViewModelFactory(dataSource, application, args.notePageKey)
        writeNoteViewModel =
            ViewModelProvider(this, viewModelFactory)[WriteNoteViewModel::class.java]

        binding.lifecycleOwner = this
        binding.writeNoteViewModel = writeNoteViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fwnMtBar.setNavigationOnClickListener {
            goBackNotePage()
        }

        writeNoteViewModel.note.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                binding.fwnAcetNoteTitle.setText(note.title)
                binding.fwnAcetNoteContent.setText(note.content)
            }
        })

        binding.fwnAcetNoteTitle.addTextChangedListener {
            updateItem()
        }

        binding.fwnAcetNoteContent.addTextChangedListener {
            updateItem()
        }

        binding.fwnMtBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tmITop -> {
                    binding.fwnAcetNoteContent.setSelection(0)
                    true
                }
                R.id.tmIBottom -> {
                    binding.fwnAcetNoteContent.setSelection(
                        binding.fwnAcetNoteContent.text?.length ?: 0
                    )
                    true
                }
                R.id.tmIDelete -> {
                    writeNoteViewModel.deleteNote()
                    goBackNotePage()
                    true
                }
                R.id.tmIUpload -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun goBackNotePage() {
        val action = WriteNoteFragmentDirections.actionWriteNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    private fun updateItem() {
        writeNoteViewModel.updateNote(
            binding.fwnAcetNoteTitle.text.toString(),
            binding.fwnAcetNoteContent.text.toString()
        )
    }

    companion object {

    }
}