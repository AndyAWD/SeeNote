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
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        viewModelFactory = SelectColorViewModelFactory(
            settingDataSource,
            noteDataSource,
            args.isFromWriteNote,
            args.isFromTagPage,
            args.type,
            args.noteId
        )

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

    private fun initComponent() {
        viewModel.changeSettingSize(args.size)
    }

    private fun initObserve() {
        viewModel.isUpdateFinish.observe(viewLifecycleOwner) { isUpdateFinish ->
            isUpdateFinish?.let {
                if (it) {
                    goBackPage()
                }
            }
        }

        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                binding.fscAcsbTextSize.progress =
                    it.textSize?.selectColor ?: BaseConstants.TEXT_SIZE
            }
        }
    }

    private fun initListener(binding: FragmentSelectColorBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackPage()
        }

        binding.fscMtToolBar.setNavigationOnClickListener {
            goBackPage()
        }

        binding.fscAcsbTextSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeSettingSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.updateTextSize()
            }
        })
    }

    private fun initClickListener(binding: FragmentSelectColorBinding) {
        binding.fscMbBlack.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.BLACK)
        }
        binding.fscMbGray.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.GRAY)
        }
        binding.fscMbSilver.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.SILVER)
        }
        binding.fscMbGainsboro.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.GAINSBORO)
        }
        binding.fscMbWhite.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.WHITE)
        }
        binding.fscMbMidnightBlue.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.MIDNIGHTBLUE)
        }
        binding.fscMbMediumBlue.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.MEDIUMBLUE)
        }
        binding.fscMbDodgerBlue.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.DODGERBLUE)
        }
        binding.fscMbSkyBlue.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.SKYBLUE)
        }
        binding.fscMbLightCyan.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.LIGHTCYAN)
        }
        binding.fscMbDarkGreen.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.DARKGREEN)
        }
        binding.fscMbForestGreen.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.FORESTGREEN)
        }
        binding.fscMbLimeGreen.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.LIMEGREEN)
        }
        binding.fscMbLightGreen.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.LIGHTGREEN)
        }
        binding.fscMbGreenYellow.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.GREENYELLOW)
        }
        binding.fscMbMaroon.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.MAROON)
        }
        binding.fscMbFirebrick.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.FIREBRICK)
        }
        binding.fscMbRed.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.RED)
        }
        binding.fscMbLightSalmon.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.LIGHTSALMON)
        }
        binding.fscMbPink.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.PINK)
        }
        binding.fscMbSaddleBrown.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.SADDLEBROWN)
        }
        binding.fscMbChocolate.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.CHOCOLATE)
        }
        binding.fscMbOrange.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.ORANGE)
        }
        binding.fscMbGold.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.GOLD)
        }
        binding.fscMbLightYellow.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.LIGHTYELLOW)
        }
        binding.fscMbDefault.setOnClickListener {
            viewModel.updateSelectColor(BaseConstants.EMPTY_STRING)
        }
    }

    private fun goBackPage() {
        val action = SelectColorFragmentDirections.actionSelectColorFragmentToSettingPageFragment(
            noteId = args.noteId,
            isFromTagPage = args.isFromTagPage,
            isFromWriteNote = args.isFromWriteNote,
            tag = args.tag
        )
        findNavController().navigate(action)
    }

    companion object {

    }
}