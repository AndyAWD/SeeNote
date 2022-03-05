package tw.com.andyawd.seenote.writenote

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentWriteNoteBinding
import java.util.*


class WriteNoteFragment : Fragment() {

    private lateinit var viewModel: WriteNoteViewModel
    private lateinit var binding: FragmentWriteNoteBinding
    private lateinit var textToSpeech: TextToSpeech

    private val args: WriteNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_write_note,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        val viewModelFactory =
            WriteNoteViewModelFactory(
                noteDataSource,
                settingDataSource,
                application,
                args.noteId
            )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[WriteNoteViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        textToSpeech = TextToSpeech(requireNotNull(this.activity), textToSpeechListener)


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
        textToSpeech.language = Locale.TAIWAN
        //音調
        //textToSpeech.setPitch(20F)

        //速度
        //textToSpeech.setSpeechRate(2F)
//        textToSpeech.isSpeaking
    }

    private fun initObserve() {
        viewModel.isDatabaseDeleted.observe(viewLifecycleOwner) { isDatabaseDeleted ->
            isDatabaseDeleted?.let {
                if (it) {
                    goBackNotePage()
                }
            }
        }

        viewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting?.let {
                binding.fwnAcsbTextSize.progress =
                    it.textSize?.writerNote ?: BaseConstants.TEXT_SIZE
            }
        }

        viewModel.speakStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                WriteNoteViewModel.SPEAKING -> {
                    binding.fwnActvVoiceTitle.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_stop_speak
                    )

                    binding.fwnActvVoiceContent.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_stop_speak
                    )
                }
                WriteNoteViewModel.SPOKEN -> {
                    textToSpeech.stop()

                    binding.fwnActvVoiceTitle.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_voice_title
                    )

                    binding.fwnActvVoiceContent.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_voice_content
                    )
                }
                WriteNoteViewModel.PLAY_ERROR -> {
                    binding.fwnActvVoiceTitle.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_play_error
                    )

                    binding.fwnActvVoiceContent.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_play_error
                    )
                }
                WriteNoteViewModel.ENABLE_TRUE -> {
                    binding.fwnActvVoiceTitle.isEnabled = true
                    binding.fwnActvVoiceContent.isEnabled = true
                }
                WriteNoteViewModel.ENABLE_FALSE -> {
                    binding.fwnActvVoiceTitle.isEnabled = false
                    binding.fwnActvVoiceContent.isEnabled = false
                }
                WriteNoteViewModel.READY_PLAY_TITLE -> {
                    if (binding.fwnAcetNoteTitle.text.isNullOrEmpty()) {
                        speakText(resources.getString(R.string.title_empty))
                    } else {
                        speakText(binding.fwnAcetNoteTitle.text.toString())
                    }

                    binding.fwnActvVoiceTitle.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_ready_play
                    )

                    binding.fwnActvVoiceContent.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_ready_play
                    )
                }
                WriteNoteViewModel.READY_PLAY_CONTENT -> {
                    if (binding.fwnAcetNoteContent.text.isNullOrEmpty()) {
                        speakText(resources.getString(R.string.content_empty))
                    } else {
                        speakText(binding.fwnAcetNoteContent.text.toString())
                    }

                    binding.fwnActvVoiceTitle.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_ready_play
                    )

                    binding.fwnActvVoiceContent.background = ActivityCompat.getDrawable(
                        requireNotNull(this.activity),
                        R.drawable.ripple_ic_ready_play
                    )
                }
                WriteNoteViewModel.SHUTDOWN -> {
                    textToSpeech.shutdown()
                }
            }
        }
    }

    private fun initListener(binding: FragmentWriteNoteBinding) {

        requireActivity().onBackPressedDispatcher.addCallback {
            goBackNotePage()
        }

        binding.fwnAcetNoteTitle.addTextChangedListener {
            updateTitle()
        }

        binding.fwnAcetNoteContent.addTextChangedListener {
            updateContent()
        }

        binding.fwnAcsbTextSize.setOnSeekBarChangeListener(object :
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

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                viewModel.speaking()
            }

            override fun onDone(utteranceId: String?) {
                viewModel.spoken()
            }

            override fun onError(utteranceId: String?) {
                viewModel.playError()
            }
        })
    }

    private fun initClickListener(binding: FragmentWriteNoteBinding) {
        binding.fwnMtToolbar.setNavigationOnClickListener {
            goBackNotePage()
        }

        binding.fwnMtToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tmITop -> {
                    binding.fwnAcetNoteContent.setSelection(0)
                    true
                }
                R.id.tmIBottom -> {
                    binding.fwnAcetNoteContent.setSelection(
                        binding.fwnAcetNoteContent.text?.length ?: 0
                    )
                    true
                }
                R.id.tmIDelete -> {
                    viewModel.deleteNote()
                    true
                }
                R.id.tmIUpload -> {
                    true
                }
                else -> false
            }
        }

        binding.fwnActvEditColor.setOnClickListener {
            goSettingPage()
        }

        binding.fwnActvInvertColor.setOnClickListener {
            viewModel.invertColor()
        }

        binding.fwnActvVoiceTitle.setOnClickListener {
            when (viewModel.speakStatus.value) {
                WriteNoteViewModel.SPEAKING -> {
                    viewModel.spoken()
                }
                WriteNoteViewModel.ENABLE_TRUE,
                WriteNoteViewModel.SPOKEN -> {
                    viewModel.readyPlayTitle()
                }
                WriteNoteViewModel.READY_PLAY_TITLE,
                WriteNoteViewModel.READY_PLAY_CONTENT,
                WriteNoteViewModel.PLAY_ERROR -> {
                    return@setOnClickListener
                }
            }
        }

        binding.fwnActvVoiceContent.setOnClickListener {
            when (viewModel.speakStatus.value) {
                WriteNoteViewModel.SPEAKING -> {
                    viewModel.spoken()
                }
                WriteNoteViewModel.ENABLE_TRUE,
                WriteNoteViewModel.SPOKEN -> {
                    viewModel.readyPlayContent()
                }
                WriteNoteViewModel.READY_PLAY_TITLE,
                WriteNoteViewModel.READY_PLAY_CONTENT,
                WriteNoteViewModel.PLAY_ERROR -> {
                    return@setOnClickListener
                }
            }
        }
    }

    private val textToSpeechListener = TextToSpeech.OnInitListener { status ->
        if (TextToSpeech.SUCCESS == status) {
            viewModel.enabled(true)
        } else {
            viewModel.playError()
        }
    }

    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)
    }

    private fun goBackNotePage() {
        viewModel.spoken()
        viewModel.shutdown()
        val action = WriteNoteFragmentDirections.actionWriteNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    private fun goSettingPage() {
        viewModel.note.value?.let {
            val action = WriteNoteFragmentDirections.actionWriteNoteFragmentToSettingPageFragment(
                BaseConstants.WRITER_NOTE,
                it.id
            )
            findNavController().navigate(action)
        }
    }

    private fun updateTitle() {
        viewModel.updateNoteTitle(binding.fwnAcetNoteTitle.text.toString())
    }

    private fun updateContent() {
        viewModel.updateNoteContent(binding.fwnAcetNoteContent.text.toString())
    }

    companion object {

    }
}