package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderNotePageBinding

class NotePageBodyViewHolder(private val binding: ViewholderNotePageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, setting: Setting?, notePageListener: NotePageListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.note = note
        binding.executePendingBindings()
        binding.notePageItemClickListener = notePageListener
    }

    fun bind(note: Note, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }

        binding.note = note
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NotePageBodyViewHolder {
            val binding = ViewholderNotePageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NotePageBodyViewHolder(binding)
        }
    }
}