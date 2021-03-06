package tw.com.andyawd.seenote.noteinventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.R

class NoteInventoryFragment : Fragment() {

    companion object {
        fun newInstance() = NoteInventoryFragment()
    }

    private lateinit var viewModel: NoteInventoryViewModel
    private lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_inventory, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteInventoryViewModel::class.java)
        binding.setVariable(BR.noteInventoryViewModel, viewModel)
    }

}