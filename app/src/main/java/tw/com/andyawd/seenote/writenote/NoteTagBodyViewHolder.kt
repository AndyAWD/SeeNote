package tw.com.andyawd.seenote.writenote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderNoteTagBinding

class NoteTagBodyViewHolder(private val binding: ViewholderNoteTagBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: String, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }

        binding.vwtMtvTag.text = tag
        binding.executePendingBindings()
    }

    fun bind(tag: String, setting: Setting?, noteTagListener: NoteTagListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.vwtMtvTag.text = tag
        binding.executePendingBindings()
        binding.noteTagItemClickListener = noteTagListener
    }

    companion object {
        fun from(parent: ViewGroup): NoteTagBodyViewHolder {
            val binding = ViewholderNoteTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoteTagBodyViewHolder(binding)
        }
    }
}