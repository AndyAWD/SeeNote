package tw.com.andyawd.seenote.settingtitle

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
import tw.com.andyawd.seenote.databinding.FragmentSettingTitleBinding

class SettingTitleFragment : Fragment() {

    private lateinit var binding: FragmentSettingTitleBinding
    private lateinit var viewModel: SettingTitleViewModel
    private lateinit var viewModelFactory: SettingTitleViewModelFactory
    private val args: SettingTitleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_title,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SettingTitleViewModelFactory(dataSource)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(SettingTitleViewModel::class.java)]

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
                binding.fstAcsbTextSize.progress =
                    it.textSize?.settingPage ?: BaseConstants.TEXT_SIZE
            }
        }
    }

    private fun initListener(binding: FragmentSettingTitleBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackSettingPage()
        }

        binding.fstAcsbTextSize.setOnSeekBarChangeListener(object :
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

    private fun initClickListener(binding: FragmentSettingTitleBinding) {
        binding.fstMtToolBar.setNavigationOnClickListener {
            goBackSettingPage()
        }

        binding.fstMbTitleTextColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fstMbTitleBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_BACKGROUND_COLOR)
        }
    }

    private fun goBackSettingPage() {
        val action =
            SettingTitleFragmentDirections.actionSettingTitleFragmentToSettingPageFragment(
                args.page,
                args.noteId
            )
        findNavController().navigate(action)
    }

    private fun goSelectColor(type: String) {
        val action =
            SettingTitleFragmentDirections.actionSettingTitleFragmentToSelectColorFragment(
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