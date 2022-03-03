package tw.com.andyawd.seenote.settingcontent

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
import androidx.navigation.fragment.navArgs
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentSettingContentBinding

class SettingContentFragment : Fragment() {

    private lateinit var binding: FragmentSettingContentBinding
    private lateinit var viewModel: SettingContentViewModel
    private lateinit var viewModelFactory: SettingContentViewModelFactory
    private val args: SettingContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_content,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        viewModelFactory = SettingContentViewModelFactory(
            settingDataSource,
            noteDataSource,
            args.page,
            args.noteId
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(SettingContentViewModel::class.java)]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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
        viewModel.changeSettingSize(args.size)
    }

    private fun initObserve() {
        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                binding.fscAcsbTextSize.progress =
                    it.textSize?.settingPage ?: BaseConstants.TEXT_SIZE
            }
        }
    }

    private fun initListener(binding: FragmentSettingContentBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackSettingPage()
        }

        binding.fscAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeSettingSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.updateSetting()
            }
        })
    }

    private fun initClickListener(binding: FragmentSettingContentBinding) {
        binding.fscMtToolBar.setNavigationOnClickListener {
            goBackSettingPage()
        }

        binding.fscMbContentTextColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fscMbContentBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_BACKGROUND_COLOR)
        }
    }

    private fun goBackSettingPage() {
        val action =
            SettingContentFragmentDirections.actionSettingContentFragmentToSettingPageFragment(
                args.page,
                args.noteId
            )
        findNavController().navigate(action)
    }

    private fun goSelectColor(type: String) {
        val action =
            SettingContentFragmentDirections.actionSettingContentFragmentToSelectColorFragment(
                viewModel.setting.value?.textSize?.selectColor ?: BaseConstants.COLOR_SIZE,
                args.page,
                type,
                args.noteId
            )
        findNavController().navigate(action)
    }

    companion object {
    }
}