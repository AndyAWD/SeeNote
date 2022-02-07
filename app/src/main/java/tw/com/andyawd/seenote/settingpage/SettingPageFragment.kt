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
            viewModel.updateSettingSize()
            goBackNotePage()
        }

        binding.fspMtToolBar.setNavigationOnClickListener {
            viewModel.updateSettingSize()
            goBackNotePage()
        }

        binding.fspAcsbTextSize.setOnSeekBarChangeListener(object :
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
            binding.fspMbTitleColor.iconSize = size.toInt()
            binding.fspMbContentColor.iconSize = size.toInt()
            binding.fspMbDateColor.iconSize = size.toInt()
            binding.fspAcsbTextSize.progress = size.toInt()
            binding.fspMbSponsorSeeNote.iconSize = size.toInt()
            binding.fspMbHorizontalLineColor.iconSize = size.toInt()
        }
    }

    private fun initClickListener(binding: FragmentSettingPageBinding) {
        binding.fspMbTitleColor.setOnClickListener {

        }

        binding.fspMbContentColor.setOnClickListener {

        }

        binding.fspMbDateColor.setOnClickListener {

        }

        binding.fspMbHorizontalLineColor.setOnClickListener {

        }
    }

    private fun goBackNotePage() {
        val action = SettingPageFragmentDirections.actionSettingNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    companion object {

    }
}