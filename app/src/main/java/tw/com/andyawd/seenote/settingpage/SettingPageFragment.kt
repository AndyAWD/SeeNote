package tw.com.andyawd.seenote.settingpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.databinding.FragmentSettingPageBinding

class SettingPageFragment : Fragment() {

    private lateinit var viewModel: SettingPageViewModel
    private lateinit var viewModelFactory: SettingPageViewModelFactory
    private lateinit var binding: FragmentSettingPageBinding
    private val args: SettingPageFragmentArgs by navArgs()

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
        val settingDataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        val noteDataSource = SeeNoteDatabase.getInstance(application).noteDatabaseDao
        val hackmdDatabaseDao = SeeNoteDatabase.getInstance(application).hackmdDatabaseDao

        viewModelFactory =
            SettingPageViewModelFactory(
                settingDataSource,
                noteDataSource,
                hackmdDatabaseDao,
                args.page,
                args.noteId
            )
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
            goBackPage()
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

                binding.fspAcetHackmdToken.isEnabled = it.user?.hackmdToken?.isNotEmpty() != true
            }
        }

        viewModel.hackmdDownloadStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    BaseConstants.DOWNLOAD -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.note_list_download)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_download_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.DOWNLOADING -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.note_list_downloading)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_downloading_24)
                        binding.fspMbDownloadNoteList.isEnabled = false
                    }
                    BaseConstants.DOWNLOAD_DONE -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.note_list_download_done)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_download_done_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.HTTP_4XX_FAIL -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.http_4xx_file)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.HTTP_5XX_FAIL -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.http_5xx_file)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.HACKMD_TOKEN_FAIL -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.hackmd_token_file)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.NETWORK_FAIL -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.network_fail)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                    BaseConstants.DOWNLOAD_FAIL -> {
                        binding.fspMbDownloadNoteList.text =
                            resources.getString(R.string.note_list_download_fail)
                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
                        binding.fspMbDownloadNoteList.isEnabled = true
                    }
                }
            }
        }
    }

    private fun initClickListener(binding: FragmentSettingPageBinding) {
        binding.fspMtToolBar.setNavigationOnClickListener {
            goBackPage()
        }

        binding.fspMtvTitle.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fspMbTitleTextColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fspMbTitleBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_BACKGROUND_COLOR)
        }

        binding.fspMtvContent.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fspMbContentTextColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fspMbContentBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_BACKGROUND_COLOR)
        }

        binding.fspMtvDate.setOnClickListener {
            goSelectColor(BaseConstants.DATE_TEXT_COLOR)
        }

        binding.fspMbDateTextColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_TEXT_COLOR)
        }

        binding.fspMbDateBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_BACKGROUND_COLOR)
        }

        binding.fspMbHackmdTokenSave.setOnClickListener {
            viewModel.settingHackmdToken()
        }

        binding.fspMbHackmdTokenRemove.setOnClickListener {
            viewModel.changeHackmdToken(BaseConstants.EMPTY_STRING)
            viewModel.settingHackmdToken()
        }

        binding.fspAcetHackmdToken.addTextChangedListener {
            viewModel.changeHackmdToken(binding.fspAcetHackmdToken.text.toString())
        }

        binding.fspMbSponsorSeeNote.setOnClickListener {

        }

        binding.fspMbHackmdWebsite.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(BaseConstants.WEBSITE_HACKMD)
            startActivity(intent)
        }

        binding.fspMbHowToGetToken.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(BaseConstants.WEBSITE_HACKMD_GET_TOKEN)
            startActivity(intent)
        }

        binding.fspMbDownloadNoteList.setOnClickListener {
            viewModel.downloadNoteList()
        }
    }

    private fun goBackPage() {
        when (args.page) {
            BaseConstants.NOTE_PAGE -> {
                val action =
                    SettingPageFragmentDirections.actionSettingNoteFragmentToNotePageFragment()
                findNavController().navigate(action)
            }
            BaseConstants.WRITER_NOTE -> {
                val action =
                    SettingPageFragmentDirections.actionSettingPageFragmentToWriteNoteFragment(args.noteId)
                findNavController().navigate(action)
            }
        }
    }

    private fun goSelectColor(type: String) {
        val action =
            SettingPageFragmentDirections.actionSettingPageFragmentToSelectColorFragment(
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