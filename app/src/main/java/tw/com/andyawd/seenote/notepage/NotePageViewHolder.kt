package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.databinding.ViewholderNotePageBinding

class NotePageViewHolder(private val binding: ViewholderNotePageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) {
        binding.notePageListViewModel = note
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NotePageViewHolder {
            val binding = ViewholderNotePageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NotePageViewHolder(binding)
        }
    }
}