package tw.com.andyawd.seenote.settingdate

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
import tw.com.andyawd.seenote.databinding.FragmentSettingDateBinding
import tw.com.andyawd.seenote.settingcontent.SettingContentFragmentArgs

class SettingDateFragment : Fragment() {

    private lateinit var binding: FragmentSettingDateBinding
    private lateinit var viewModel: SettingDateViewModel
    private lateinit var viewModelFactory: SettingDateViewModelFactory
    private val args: SettingContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_date,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SettingDateViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[(SettingDateViewModel::class.java)]

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
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fsdAcsbTextSize.progress = size.toInt()
            binding.fsdMbDateTextColor.iconSize = size.toInt()
            binding.fsdMbDateBackgroundColor.iconSize = size.toInt()
        }

        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            binding.setting = setting
        }
    }

    private fun initListener(binding: FragmentSettingDateBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.updateSettingSize()
            goBackSettingPage()
        }

        binding.fsdAcsbTextSize.setOnSeekBarChangeListener(object :
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

    private fun initClickListener(binding: FragmentSettingDateBinding) {
        binding.fsdMtToolBar.setNavigationOnClickListener {
            viewModel.updateSettingSize()
            goBackSettingPage()
        }

        binding.fsdMbDateTextColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_TEXT_COLOR)
        }

        binding.fsdMbDateBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_BACKGROUND_COLOR)
        }
    }

    private fun goBackSettingPage() {
        val action = SettingDateFragmentDirections.actionSettingDateFragmentToSettingPageFragment()
        findNavController().navigate(action)
    }

    private fun goSelectColor(page: String) {
        val action = SettingDateFragmentDirections.actionSettingDateFragmentToSelectColorFragment(
            page,
            viewModel.setting.value?.selectSize ?: BaseConstants.COLOR_SIZE
        )
        findNavController().navigate(action)
    }

    companion object {

    }
}