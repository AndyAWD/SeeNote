package tw.com.andyawd.seenote.settingnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentSettingNoteBinding

class SettingNoteFragment : Fragment() {

    private lateinit var viewModel: SettingNoteViewModel
    private lateinit var viewModelFactory: SettingNoteViewModelFactory
    private lateinit var binding: FragmentSettingNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_note,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SettingNoteViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingNoteViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initListener(binding)
        initClickListener(binding)
    }

    private fun initListener(binding: FragmentSettingNoteBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.updateSettingSize()
            goBackNotePage()
        }

        binding.fsnMtToolBar.setNavigationOnClickListener {
            viewModel.updateSettingSize()
            goBackNotePage()
        }

        binding.fsnAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeSettingSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun initObserve() {
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fsnAcsbTextSize.progress = size.toInt()
            binding.fsnMbSponsorSeeNote.iconSize = size.toInt()
        }
    }

    private fun initClickListener(binding: FragmentSettingNoteBinding) {
        binding.fsnMbTitleTextColor.setOnClickListener {
            selectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fsnMbTitleBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.TITLE_BACKGROUND_COLOR)
        }

        binding.fsnMbContentTextColor.setOnClickListener {
            selectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fsnMbContentBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.CONTENT_BACKGROUND_COLOR)
        }

        binding.fsnMbCreateDateTextColor.setOnClickListener {
            selectColor(BaseConstants.CREATE_DATE_TEXT_COLOR)
        }

        binding.fsnMbCreateDateBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.CREATE_DATE_BACKGROUND_COLOR)
        }

        binding.fsnMbHorizontalLineColor.setOnClickListener {
            selectColor(BaseConstants.HORIZONTAL_LINE_COLOR)
        }
    }

    private fun selectColor(setting: String) {
        val action =
            SettingNoteFragmentDirections.actionSettingNoteFragmentToSelectColorFragment(setting)
        findNavController().navigate(action)
    }

    private fun goBackNotePage() {
        val action = SettingNoteFragmentDirections.actionSettingNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    companion object {

    }
}