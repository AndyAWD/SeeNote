package tw.com.andyawd.seenote.settingnotepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.databinding.FragmentSettingNotePageBinding

class SettingNotePageFragment : Fragment() {

    private lateinit var binding: FragmentSettingNotePageBinding
    private lateinit var viewModel: SettingNotePageViewModel
    private lateinit var viewModelFactory: SettingNotePageViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_note_page,
            container,
            false
        )
        viewModelFactory = SettingNotePageViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(SettingNotePageViewModel::class.java)]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener(binding)
    }

    private fun initClickListener(binding: FragmentSettingNotePageBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackSettingNote()
        }
        binding.fsnpMtToolBar.setNavigationOnClickListener {
            goBackSettingNote()
        }
        binding.fsnpMbTitleTextColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_TITLE_TEXT_COLOR)
        }
        binding.fsnpMbTitleBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_TITLE_BACKGROUND_COLOR)
        }
        binding.fsnpMbContentTextColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_CONTENT_TEXT_COLOR)
        }
        binding.fsnpMbContentBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_CONTENT_BACKGROUND_COLOR)
        }
        binding.fsnpMbDateTextColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_CREATE_DATE_TEXT_COLOR)
        }
        binding.fsnpMbDateBackgroundColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_CREATE_DATE_BACKGROUND_COLOR)
        }
        binding.fsnpMbHorizontalLineColor.setOnClickListener {
            selectColor(BaseConstants.PAGE_HORIZONTAL_LINE_COLOR)
        }
    }

    private fun goBackSettingNote() {
        val action =
            SettingNotePageFragmentDirections.actionSettingNotePageFragmentToSettingNoteFragment()
        findNavController().navigate(action)
    }

    private fun selectColor(setting: String) {
        val action =
            SettingNotePageFragmentDirections.actionSettingNotePageFragmentToSelectColorFragment(
                setting
            )
        findNavController().navigate(action)
    }

    companion object {

    }
}