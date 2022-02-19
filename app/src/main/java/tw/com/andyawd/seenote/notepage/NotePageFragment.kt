package tw.com.andyawd.seenote.notepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentNotePageBinding


class NotePageFragment : Fragment() {

    private lateinit var viewModel: NotePageViewModel
    private lateinit var viewModelFactory: NotePageViewModelFactory
    private lateinit var binding: FragmentNotePageBinding
    private lateinit var adapter: NotePageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_page,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao

        viewModelFactory = NotePageViewModelFactory(noteDataSource, settingDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotePageViewModel::class.java]

        binding.notePageViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = NotePageAdapter()
        binding.fnpRvNoteList.adapter = adapter
        binding.fnpRvNoteList.layoutManager = GridLayoutManager(application, 1)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initListener()
        initClickListener(binding)
    }

    private fun initClickListener(binding: FragmentNotePageBinding) {
        adapter.setOnItemClickListener(NotePageListener { id ->
            AWDLog.d("新的資料庫id: $id")
            viewModel.onItemClicked(id)
        })

        binding.fnpMtToolbar.setNavigationOnClickListener {
            val action = NotePageFragmentDirections.actionNotePageFragmentToSettingNoteFragment()
            findNavController().navigate(action)
        }

        binding.fnpMtToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tnpIWriteNote -> {
                    val action =
                        NotePageFragmentDirections.actionNotePageFragmentToWriteNoteFragment(
                            BaseConstants.CREATE_NOTE
                        )
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }
    }

    private fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback {

        }
    }

    private fun initObserve() {
        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                adapter.addSetting(it)
            }
        }

        viewModel.note.observe(viewLifecycleOwner, Observer {
            //adapter.submitList(it)
            adapter.addHeaderAndSubmitList(it)
        })

        viewModel.notePageDetail.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                findNavController().navigate(
                    NotePageFragmentDirections.actionNotePageFragmentToWriteNoteFragment(
                        noteId
                    )
                )
                viewModel.onNotePageNavigated()
            }
        })
    }

    companion object {

    }
}

