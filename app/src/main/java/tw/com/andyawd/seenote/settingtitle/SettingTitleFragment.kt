package tw.com.andyawd.seenote.settingtitle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
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
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fstAcsbTextSize.progress = size.toInt()
            binding.fstMbTitleTextColor.iconSize = size.toInt()
            binding.fstMbTitleBackgroundColor.iconSize = size.toInt()
        }

        viewModel.titleTextColor.observe(viewLifecycleOwner) { color ->
            color?.let {
                binding.fstMtvTitle.setTextColor(
                    ActivityCompat.getColor(
                        requireActivity(),
                        getColorResource(it)
                    )
                )
            }
        }
    }

    private fun getColorResource(color: String): Int {
        when (color) {
            BaseConstants.BLACK -> return R.color.hexColor000_Black
            BaseConstants.GRAY -> return R.color.hexColor002_Gray
            BaseConstants.SILVER -> return R.color.hexColor004_Silver
            BaseConstants.GAINSBORO -> return R.color.hexColor006_Gainsboro
            BaseConstants.WHITE -> return R.color.hexColor007_White
            BaseConstants.MIDNIGHTBLUE -> return R.color.hexColor036_MidnightBlue
            BaseConstants.MEDIUMBLUE -> return R.color.hexColor039_MediumBlue
            BaseConstants.DODGERBLUE -> return R.color.hexColor041_DodgerBlue
            BaseConstants.SKYBLUE -> return R.color.hexColor045_SkyBlue
            BaseConstants.LIGHTCYAN -> return R.color.hexColor049_LightCyan
            BaseConstants.DARKGREEN -> return R.color.hexColor061_DarkGreen
            BaseConstants.FORESTGREEN -> return R.color.hexColor063_ForestGreen
            BaseConstants.LIMEGREEN -> return R.color.hexColor076_LimeGreen
            BaseConstants.LIGHTGREEN -> return R.color.hexColor069_LightGreen
            BaseConstants.GREENYELLOW -> return R.color.hexColor074_GreenYellow
            BaseConstants.MAROON -> return R.color.hexColor102_Maroon
            BaseConstants.FIREBRICK -> return R.color.hexColor105_Firebrick
            BaseConstants.RED -> return R.color.hexColor115_Red
            BaseConstants.LIGHTSALMON -> return R.color.hexColor111_LightSalmon
            BaseConstants.PINK -> return R.color.hexColor121_Pink
            BaseConstants.SADDLEBROWN -> return R.color.hexColor101_SaddleBrown
            BaseConstants.CHOCOLATE -> return R.color.hexColor099_Chocolate
            BaseConstants.ORANGE -> return R.color.hexColor093_Orange
            BaseConstants.GOLD -> return R.color.hexColor092_Gold
            BaseConstants.LIGHTYELLOW -> return R.color.hexColor084_LightYellow
            else -> return R.color.hexColor000_Black
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

        binding.fstMbTitleTextColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fstMbTitleBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_BACKGROUND_COLOR)
        }
    }

    private fun goBackSettingPage() {
        val action =
            SettingTitleFragmentDirections.actionSettingTitleFragmentToSettingPageFragment()
        findNavController().navigate(action)
    }

    private fun goSelectColor(page: String) {
        val action =
            SettingTitleFragmentDirections.actionSettingTitleFragmentToSelectColorFragment(
                page,
                viewModel.setting.value?.selectSize ?: BaseConstants.COLOR_SIZE
            )
        findNavController().navigate(action)
    }

    companion object {

    }
}