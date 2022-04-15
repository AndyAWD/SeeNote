package tw.com.andyawd.seenote.tagpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentTagPageBinding

class TagPageFragment : Fragment() {

    private lateinit var binding: FragmentTagPageBinding
    private lateinit var viewModelFactory: TagPageViewModelFactory
    private lateinit var viewModel: TagPageViewModel
    private lateinit var adapter: TagPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tag_page,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        adapter = TagPageAdapter()

        viewModelFactory = TagPageViewModelFactory(application, noteDataSource, settingDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[TagPageViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ftpRvTagList.adapter = adapter
        binding.ftpRvTagList.layoutManager = GridLayoutManager(application, 1)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        initObserve()
        initListener(binding)
        initClickListener(binding)
    }

    private fun initComponent() {


    }

    private fun initObserve() {
        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                adapter.changeSetting(it)
                binding.ftpAcsbTextSize.progress = it.textSize?.notePage ?: BaseConstants.TEXT_SIZE
            }
        }

        viewModel.tag.observe(viewLifecycleOwner) { tag ->
            adapter.addHeaderAndSubmitList(tag)
        }

        viewModel.searchText.observe(viewLifecycleOwner) { text ->
            text?.let {
                if (text.isEmpty()) {
                    binding.ftpAcetSearchText.text?.clear()
                }

                viewModel.queryTag()
            }
        }

        viewModel.tagPageDetail.observe(viewLifecycleOwner) { tag ->
            tag?.let {
                goNotePage(tag)
            }
        }

        viewModel.settingPageDetail.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                goSettingPage()
            }
        }

        viewModel.writeNoteDetail.observe(viewLifecycleOwner) { write ->
            write?.let {
                goWriteNote()
            }
        }
    }

    private fun initListener(binding: FragmentTagPageBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {

        }

        binding.ftpAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeTagPageSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.updateSetting()
            }
        })

        binding.ftpAcetSearchText.addTextChangedListener { text ->
            viewModel.inputSearchText(text.toString())
        }
    }

    private fun initClickListener(binding: FragmentTagPageBinding) {

        adapter.setOnItemClickListener(TagPageListener { tag ->
            viewModel.onTagPageItemClicked(tag)
        })

        binding.ftpMtToolbar.setNavigationOnClickListener {
            viewModel.onSettingPageClicked(BaseConstants.SETTING_PAGE)
        }

        binding.ftpMtvWriteNote.setOnClickListener {
            viewModel.onWriteNoteClicked(BaseConstants.WRITER_NOTE)
        }

        binding.ftpAcivSearchCancel.setOnClickListener {
            viewModel.inputSearchText(BaseConstants.EMPTY_STRING)
        }
    }

    private fun goSettingPage() {
        val action = TagPageFragmentDirections.actionTagPageFragmentToSettingPageFragment(
            isFromTagPage = true,
            isFromWriteNote = false
        )
        findNavController().navigate(action)
        viewModel.onSettingPageNavigated()
    }

    private fun goNotePage(tag: String) {
        val action = TagPageFragmentDirections.actionTagPageFragmentToNotePageFragment(
            tag = tag
        )
        findNavController().navigate(action)
        viewModel.onTagPageNavigated()
    }

    private fun goWriteNote() {
        val action = TagPageFragmentDirections.actionTagPageFragmentToWriteNoteFragment(
            noteId = BaseConstants.CREATE_NOTE,
            isFromTagPage = true,
            isFromNotePage = false
        )
        findNavController().navigate(action)
        viewModel.onWriteNoteNavigated()
    }

    companion object {

    }
}