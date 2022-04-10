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
import tw.com.andyawd.andyawdlibrary.AWDLog
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
                application,
                settingDataSource,
                noteDataSource,
                hackmdDatabaseDao,
                args.isFromWriteNote,
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

                if (it.user?.hackmdToken.isNullOrEmpty()) {
                    binding.fspMbHackmdToken.text =
                        resources.getString(R.string.token_save)
                    binding.fspMbHackmdToken.setIconResource(R.drawable.ic_baseline_token_24)
                    binding.fspAcetHackmdToken.isEnabled = true
                } else {
                    binding.fspMbHackmdToken.text =
                        resources.getString(R.string.token_remove)
                    binding.fspMbHackmdToken.setIconResource(R.drawable.ic_baseline_delete_forever_24)
                    binding.fspAcetHackmdToken.isEnabled = false
                }
            }
        }

        viewModel.hackmdToken.observe(viewLifecycleOwner) { token ->
            token?.let {

            }
        }

//        viewModel.hackmdDownloadStatus.observe(viewLifecycleOwner) { status ->
//            status?.let {
//                when (it) {
//                    BaseConstants.DOWNLOAD -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.note_list_download)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_download_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.DOWNLOADING -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.note_list_downloading)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_downloading_24)
//                        binding.fspMbDownloadNoteList.isEnabled = false
//                    }
//                    BaseConstants.DOWNLOAD_DONE -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.note_list_download_done)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_download_done_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.HTTP_4XX_FAIL -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.http_4xx_file)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.HTTP_5XX_FAIL -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.http_5xx_file)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.HACKMD_TOKEN_FAIL -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.hackmd_token_file)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.NETWORK_FAIL -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.network_fail)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                    BaseConstants.DOWNLOAD_FAIL -> {
//                        binding.fspMbDownloadNoteList.text =
//                            resources.getString(R.string.note_list_download_fail)
//                        binding.fspMbDownloadNoteList.setIconResource(R.drawable.ic_baseline_file_download_off_24)
//                        binding.fspMbDownloadNoteList.isEnabled = true
//                    }
//                }
//            }
//        }
    }

    private fun initClickListener(binding: FragmentSettingPageBinding) {
        binding.fspMtToolBar.setNavigationOnClickListener {
            goBackPage()
        }

        binding.fspMtvTag.setOnClickListener {
            goSelectColor(BaseConstants.TAG_TEXT_COLOR)
        }

        binding.fspMtvTagTextColor.setOnClickListener {
            goSelectColor(BaseConstants.TAG_TEXT_COLOR)
        }

        binding.fspMtvTagBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.TAG_BACKGROUND_COLOR)
        }

        binding.fspMtvTitle.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fspMtvTitleTextColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_TEXT_COLOR)
        }

        binding.fspMtvTitleBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.TITLE_BACKGROUND_COLOR)
        }

        binding.fspMtvContent.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fspMtvContentTextColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_TEXT_COLOR)
        }

        binding.fspMtvContentBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.CONTENT_BACKGROUND_COLOR)
        }

        binding.fspMtvDate.setOnClickListener {
            goSelectColor(BaseConstants.DATE_TEXT_COLOR)
        }

        binding.fspMtvDateTextColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_TEXT_COLOR)
        }

        binding.fspMtvDateBackgroundColor.setOnClickListener {
            goSelectColor(BaseConstants.DATE_BACKGROUND_COLOR)
        }

        binding.fspMbHackmdToken.setOnClickListener {
            if (viewModel.setting.value?.user?.hackmdToken.isNullOrEmpty()) {
                viewModel.saveHackmdToken()
            } else {
                viewModel.removeHackmdToken()
            }
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

//        binding.fspMbDownloadNoteList.setOnClickListener {
//            viewModel.downloadNoteList()
//        }
    }

    private fun goBackPage() {
        AWDLog.d("isFromTagPage: ${args.isFromTagPage} / isFromWriteNote: ${args.isFromWriteNote}")

        if (args.isFromWriteNote) {
            goWriteNote()
            return
        }

        if (args.isFromTagPage) {
            goTagPage()
            return
        }
    }

    private fun goWriteNote() {
        val action = SettingPageFragmentDirections.actionSettingPageFragmentToWriteNoteFragment(
            noteId = args.noteId,
            isFromTagPage = args.isFromTagPage,
            isFromNotePage = args.isFromWriteNote,
            tag = args.tag
        )
        findNavController().navigate(action)
    }

    private fun goTagPage() {
        val action = SettingPageFragmentDirections.actionSettingPageFragmentToTagPageFragment()
        findNavController().navigate(action)
    }

    private fun goSelectColor(type: String) {
        val action = SettingPageFragmentDirections.actionSettingPageFragmentToSelectColorFragment(
            size = viewModel.setting.value?.textSize?.selectColor ?: BaseConstants.COLOR_SIZE,
            type = type,
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