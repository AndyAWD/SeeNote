package tw.com.andyawd.seenote.settingnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.databinding.FragmentSettingNoteBinding

class SettingNoteFragment : Fragment() {

    private lateinit var viewModel: SettingNoteViewModel
    private lateinit var binding: FragmentSettingNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting_note,
            container,
            false
        )

        viewModel = ViewModelProvider(this)[(SettingNoteViewModel::class.java)]
        binding.settingNoteViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            goBackNotePage()
        }

        binding.fenMtToolBar.setOnClickListener {
            goBackNotePage()
        }
    }

    private fun goBackNotePage() {
        val action = SettingNoteFragmentDirections.actionSettingNoteFragmentToNotePageFragment()
        findNavController().navigate(action)
    }

    companion object {

    }
}