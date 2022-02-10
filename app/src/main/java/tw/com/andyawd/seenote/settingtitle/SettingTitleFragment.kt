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
import tw.com.andyawd.andyawdlibrary.AWDLog
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
        AWDLog.d("args.size: ${args.size}")
        viewModel.changeSettingSize(args.size)
    }

    private fun initObserve() {
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fstAcsbTextSize.progress = size.toInt()
            binding.fstMbTitleColor.iconSize = size.toInt()
            binding.fstMbContentColor.iconSize = size.toInt()
        }
    }

    private fun initListener(binding: FragmentSettingTitleBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.updateSettingSize()
            goBackSettingPage()
        }

        binding.fstAcsbTextSize.setOnSeekBarChangeListener(object :
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

    private fun initClickListener(binding: FragmentSettingTitleBinding) {
        binding.fstMtToolBar.setNavigationOnClickListener {
            viewModel.updateSettingSize()
            goBackSettingPage()
        }
    }

    private fun goBackSettingPage() {
        val action =
            SettingTitleFragmentDirections.actionSettingTitleFragmentToSettingPageFragment()
        findNavController().navigate(action)
    }

    private fun selectColor(setting: String) {

    }

    companion object {

    }
}