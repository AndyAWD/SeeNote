package tw.com.andyawd.seenote.writenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.databinding.FragmentWriteNoteBinding

class WriteNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentWriteNoteBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_write_note,
            container,
            false
        )


        return binding.root
    }

    companion object {

    }
}