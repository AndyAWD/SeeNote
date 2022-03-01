package tw.com.andyawd.seenote.selectcolor

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
import tw.com.andyawd.seenote.databinding.FragmentSelectColorBinding

class SelectColorFragment : Fragment() {

    private lateinit var binding: FragmentSelectColorBinding
    private lateinit var viewModelFactory: SelectColorViewModelFactory
    private lateinit var viewModel: SelectColorViewModel
    private val args: SelectColorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_color,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SelectColorViewModelFactory(dataSource, args.page)
        viewModel = ViewModelProvider(this, viewModelFactory)[SelectColorViewModel::class.java]

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

    private fun initClickListener(binding: FragmentSelectColorBinding) {
        binding.fscMbBlack.setOnClickListener {
            viewModel.selectColor(BaseConstants.BLACK)
        }
        binding.fscMbGray.setOnClickListener {
            viewModel.selectColor(BaseConstants.GRAY)
        }
        binding.fscMbSilver.setOnClickListener {
            viewModel.selectColor(BaseConstants.SILVER)
        }
        binding.fscMbGainsboro.setOnClickListener {
            viewModel.selectColor(BaseConstants.GAINSBORO)
        }
        binding.fscMbWhite.setOnClickListener {
            viewModel.selectColor(BaseConstants.WHITE)
        }
        binding.fscMbMidnightBlue.setOnClickListener {
            viewModel.selectColor(BaseConstants.MIDNIGHTBLUE)
        }
        binding.fscMbMediumBlue.setOnClickListener {
            viewModel.selectColor(BaseConstants.MEDIUMBLUE)
        }
        binding.fscMbDodgerBlue.setOnClickListener {
            viewModel.selectColor(BaseConstants.DODGERBLUE)
        }
        binding.fscMbSkyBlue.setOnClickListener {
            viewModel.selectColor(BaseConstants.SKYBLUE)
        }
        binding.fscMbLightCyan.setOnClickListener {
            viewModel.selectColor(BaseConstants.LIGHTCYAN)
        }
        binding.fscMbDarkGreen.setOnClickListener {
            viewModel.selectColor(BaseConstants.DARKGREEN)
        }
        binding.fscMbForestGreen.setOnClickListener {
            viewModel.selectColor(BaseConstants.FORESTGREEN)
        }
        binding.fscMbLimeGreen.setOnClickListener {
            viewModel.selectColor(BaseConstants.LIMEGREEN)
        }
        binding.fscMbLightGreen.setOnClickListener {
            viewModel.selectColor(BaseConstants.LIGHTGREEN)
        }
        binding.fscMbGreenYellow.setOnClickListener {
            viewModel.selectColor(BaseConstants.GREENYELLOW)
        }
        binding.fscMbMaroon.setOnClickListener {
            viewModel.selectColor(BaseConstants.MAROON)
        }
        binding.fscMbFirebrick.setOnClickListener {
            viewModel.selectColor(BaseConstants.FIREBRICK)
        }
        binding.fscMbRed.setOnClickListener {
            viewModel.selectColor(BaseConstants.RED)
        }
        binding.fscMbLightSalmon.setOnClickListener {
            viewModel.selectColor(BaseConstants.LIGHTSALMON)
        }
        binding.fscMbPink.setOnClickListener {
            viewModel.selectColor(BaseConstants.PINK)
        }
        binding.fscMbSaddleBrown.setOnClickListener {
            viewModel.selectColor(BaseConstants.SADDLEBROWN)
        }
        binding.fscMbChocolate.setOnClickListener {
            viewModel.selectColor(BaseConstants.CHOCOLATE)
        }
        binding.fscMbOrange.setOnClickListener {
            viewModel.selectColor(BaseConstants.ORANGE)
        }
        binding.fscMbGold.setOnClickListener {
            viewModel.selectColor(BaseConstants.GOLD)
        }
        binding.fscMbLightYellow.setOnClickListener {
            viewModel.selectColor(BaseConstants.LIGHTYELLOW)
        }
        binding.fscMbDefault.setOnClickListener {
            viewModel.selectColor(BaseConstants.EMPTY_STRING)
        }
    }

    private fun initComponent() {
        viewModel.changeSelectSize(args.size)
    }

    private fun initObserve() {
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fscAcsbTextSize.progress = size.toInt()
        }

        viewModel.color.observe(viewLifecycleOwner) { color ->
            color?.let {
                onUpdateSelectColor()
            }
        }

        viewModel.isUpdateFinish.observe(viewLifecycleOwner) { isUpdateSelectSize ->
            if (isUpdateSelectSize) {
                goBackSetting(args.page)
            }
        }
    }

    private fun initListener(binding: FragmentSelectColorBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            onUpdateSelectSize()
        }

        binding.fscMtToolBar.setNavigationOnClickListener {
            onUpdateSelectSize()
        }

        binding.fscAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeSelectSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun goBackSetting(page: String) {
        when (page) {
            BaseConstants.TITLE_TEXT_COLOR,
            BaseConstants.TITLE_BACKGROUND_COLOR -> {
                val action =
                    SelectColorFragmentDirections.actionSelectColorFragmentToSettingTitleFragment(
                        viewModel.size.value ?: BaseConstants.TEXT_SIZE
                    )
                findNavController().navigate(action)
            }
            BaseConstants.CONTENT_TEXT_COLOR,
            BaseConstants.CONTENT_BACKGROUND_COLOR -> {
                val action =
                    SelectColorFragmentDirections.actionSelectColorFragmentToSettingContentFragment(
                        viewModel.size.value ?: BaseConstants.TEXT_SIZE
                    )
                findNavController().navigate(action)
            }
            BaseConstants.DATE_TEXT_COLOR,
            BaseConstants.DATE_BACKGROUND_COLOR -> {
                val action =
                    SelectColorFragmentDirections.actionSelectColorFragmentToSettingDateFragment(
                        viewModel.size.value ?: BaseConstants.TEXT_SIZE
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun onUpdateSelectColor() {
        viewModel.updateSelectColor()
    }

    private fun onUpdateSelectSize() {
        viewModel.updateSelectSize()
    }

    companion object {

    }
}