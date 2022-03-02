package tw.com.andyawd.seenote.settingpage

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
import tw.com.andyawd.seenote.databinding.FragmentSettingPageBinding

class SettingPageFragment : Fragment() {

    private lateinit var viewModel: SettingPageViewModel
    private lateinit var viewModelFactory: SettingPageViewModelFactory
    private lateinit var binding: FragmentSettingPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_page,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SettingPageViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingPageViewModel::class.java]


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

    private fun initListener(binding: FragmentSettingPageBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackNotePage()
        }

        binding.fspAcsbTextSize.setOnSeekBarChangeListener(object :
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

    private fun initObserve() {
        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                binding.fspAcsbTextSize.progress =
                    it.textSize?.settingPage ?: BaseConstants.TEXT_SIZE
            }
        }
    }

    private fun initClickListener(binding: FragmentSettingPageBinding) {
        binding.fspMtToolBar.setNavigationOnClickListener {
            goBackNotePage()
        }

        binding.fspMbTitleColor.setOnClickListener {
            goTitleSetting()
        }

        binding.fspMtvTitle.setOnClickListener {
            goTitleSetting()
        }

        binding.fspMbContentColor.setOnClickListener {
            goContentSetting()
        }

        binding.fspMtvContent.setOnClickListener {
            goContentSetting()
        }

        binding.fspMbDateColor.setOnClickListener {
            goDateSetting()
        }

        binding.fspMtvDate.setOnClickListener {
            goDateSetting()
        }

        binding.fspMbHorizontalLineColor.setOnClickListener {

        }
    }

    private fun goBackNotePage() {
        val action = SettingPageFragmentDirections.actionSettingNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    private fun goTitleSetting() {
        viewModel.setting.value?.textSize?.settingPage?.let {
            val action =
                SettingPageFragmentDirections.actionSettingPageFragmentToSettingTitleFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun goContentSetting() {
        viewModel.setting.value?.textSize?.settingPage?.let {
            val action =
                SettingPageFragmentDirections.actionSettingPageFragmentToSettingContentFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun goDateSetting() {
        viewModel.setting.value?.textSize?.settingPage?.let {
            val action =
                SettingPageFragmentDirections.actionSettingPageFragmentToSettingDateFragment(it)
            findNavController().navigate(action)
        }
    }

    companion object {

    }
}