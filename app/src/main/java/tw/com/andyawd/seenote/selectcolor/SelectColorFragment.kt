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
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.SeeNoteDatabase
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

        val application = requireNotNull(this.activity).application
        val dataSource = SeeNoteDatabase.getInstance(application).settingDatabaseDao
        viewModelFactory = SelectColorViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[SelectColorViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initListener(binding)
    }

    private fun initObserve() {
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.fscAcsbTextSize.progress = size.toInt()
        }
    }

    private fun initListener(binding: FragmentSelectColorBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            goBackSettingNote()
        }

        binding.fscMtToolBar.setNavigationOnClickListener {
            viewModel.updateSelectSize()
            goBackSettingNote()
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

    private fun goBackSettingNote() {
        val action =
            SelectColorFragmentDirections.actionSelectColorFragmentToSettingNoteFragment()
        findNavController().navigate(action)
    }

    companion object {

    }
}