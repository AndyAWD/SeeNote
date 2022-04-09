package tw.com.andyawd.seenote.notepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
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
    private val args: NotePageFragmentArgs by navArgs()
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

        viewModelFactory =
            NotePageViewModelFactory(application, noteDataSource, settingDataSource, args.tag)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotePageViewModel::class.java]

        binding.notePageViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val xmlColor =
            ActivityCompat.getDrawable(
                requireNotNull(this.activity),
                R.drawable.line_horizontal_line_height
            )
        val dividerItemDecoration =
            DividerItemDecoration(requireNotNull(this.activity), DividerItemDecoration.VERTICAL)
        xmlColor?.let { dividerItemDecoration.setDrawable(it) }

        adapter = NotePageAdapter()
        binding.fnpRvNoteList.adapter = adapter
//        binding.fnpRvNoteList.adapter = tagPageAdapter
        binding.fnpRvNoteList.layoutManager = GridLayoutManager(application, 1)
        //binding.fnpRvNoteList.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        initObserve()
        initListener()
        initClickListener(binding)
    }

    private fun initComponent() {

    }

    private fun initObserve() {
        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                adapter.changeSetting(it)
                binding.fnpAcsbTextSize.progress = it.textSize?.notePage ?: BaseConstants.TEXT_SIZE
            }
        }

        viewModel.note.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.notePageDetail.observe(viewLifecycleOwner) { id ->
            id?.let {
                AWDLog.d("args.tag: ${args.tag}")
                goWriteNote(id, args.tag)
            }
        }

        viewModel.searchText.observe(viewLifecycleOwner) { text ->
            text?.let {
                if (text.isEmpty()) {
                    binding.fnpAcetSearchText.text?.clear()
                }

                viewModel.queryNote()
            }
        }
    }

    private fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback {
            goTagNote()
        }

        binding.fnpAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeNotePageSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.updateSetting()
            }
        })

        binding.fnpAcetSearchText.addTextChangedListener { text ->
            viewModel.inputSearchText(text.toString())
        }
    }

    private fun initClickListener(binding: FragmentNotePageBinding) {
        adapter.setOnItemClickListener(NotePageListener { id ->
            viewModel.onItemClicked(id)
        })

        binding.fnpMtToolbar.setNavigationOnClickListener {
            goTagNote()
        }

        binding.fnpMtToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tnpIWriteNote -> {
                    AWDLog.d("args.tag: ${args.tag}")
                    goWriteNote(BaseConstants.CREATE_NOTE, args.tag)
                    true
                }
                else -> false
            }
        }

        binding.fnpAcivSearchCancel.setOnClickListener {
            viewModel.inputSearchText(BaseConstants.EMPTY_STRING)
        }
    }

    private fun goWriteNote(id: Long, tag: String) {
        AWDLog.d("goWriteNote id: $id / tag: $tag")
        val action = NotePageFragmentDirections.actionNotePageFragmentToWriteNoteFragment(
            noteId = id,
            isFromTagPage = false,
            isFromNotePage = true,
            tag = args.tag
        )
        findNavController().navigate(action)
        viewModel.onNotePageNavigated()
    }

    private fun goTagNote() {
        val action = NotePageFragmentDirections.actionNotePageFragmentToTagPageFragment()
        findNavController().navigate(action)
        viewModel.onNotePageNavigated()
    }

    companion object {

    }
}

