package tw.com.andyawd.seenote.selectcolor

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
import tw.com.andyawd.seenote.databinding.FragmentSelectColorBinding

class SelectColorFragment : Fragment() {

    private lateinit var binding: FragmentSelectColorBinding
    private lateinit var viewModelFactory: SelectColorViewModelFactory
    private lateinit var viewModel: SelectColorViewModel

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

        viewModelFactory = SelectColorViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SelectColorViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            goBackSettingNote()
        }

    }

    private fun goBackSettingNote() {
        val action =
            SelectColorFragmentDirections.actionSelectColorFragmentToSettingNotePageFragment()
        findNavController().navigate(action)
    }

    companion object {

    }
}