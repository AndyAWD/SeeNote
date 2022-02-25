package tw.com.andyawd.seenote.writenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentWriteNoteBinding

class WriteNoteFragment : Fragment() {

    private lateinit var viewModel: WriteNoteViewModel
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
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        val viewModelFactory =
            WriteNoteViewModelFactory(
                noteDataSource,
                settingDataSource,
                application,
                args.notePageKey
            )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[WriteNoteViewModel::class.java]

        binding.lifecycleOwner = this
        binding.writeNoteViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initListener(binding)
        initClickListener(binding)
    }

    private fun initObserve() {
        viewModel.isDatabaseDeleted.observe(viewLifecycleOwner) { isDatabaseDeleted ->
            isDatabaseDeleted?.let {
                if (it) {
                    goBackNotePage()
                }
            }
        }

        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                binding.setting = it
            }
        }
    }

    private fun initListener(binding: FragmentWriteNoteBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackNotePage()
        }

        binding.fwnAcetNoteTitle.addTextChangedListener {
            updateTitle()
        }

        binding.fwnAcetNoteContent.addTextChangedListener {
            updateContent()
        }
    }

    private fun initClickListener(binding: FragmentWriteNoteBinding) {
        binding.fwnMtToolbar.setNavigationOnClickListener {
            goBackNotePage()
        }

        binding.fwnMtToolbar.setOnMenuItemClickListener { menuItem ->
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
                    viewModel.deleteNote()
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

    private fun updateTitle() {
        viewModel.updateNoteTitle(binding.fwnAcetNoteTitle.text.toString())
    }

    private fun updateContent() {
        viewModel.updateNoteContent(binding.fwnAcetNoteContent.text.toString())
    }

    companion object {

    }
}