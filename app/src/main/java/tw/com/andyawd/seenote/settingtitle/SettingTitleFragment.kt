package tw.com.andyawd.seenote.settingtitle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.databinding.FragmentSettingNotePageBinding

class SettingTitleFragment : Fragment() {

    private lateinit var binding: FragmentSettingNotePageBinding
    private lateinit var viewModel: SettingTitleViewModel
    private lateinit var viewModelFactory: SettingTitleViewModelFactory

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
        viewModelFactory = SettingTitleViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(SettingTitleViewModel::class.java)]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener(binding)
    }

    private fun initClickListener(binding: FragmentSettingNotePageBinding) {

    }

    private fun goBackSettingNote() {

    }

    private fun selectColor(setting: String) {

    }

    companion object {

    }
}