package tw.com.andyawd.seenote.notepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.databinding.FragmentNotePageBinding


class NotePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentNotePageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_page,
            container,
            false
        )

        binding.fnpMbWriteNote.setOnClickListener {
            findNavController().navigate(R.id.action_notePageFragment_to_writeNoteFragment)
        }

        return binding.root
    }

    companion object {

    }
}