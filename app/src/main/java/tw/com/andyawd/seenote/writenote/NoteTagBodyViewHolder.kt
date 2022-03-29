package tw.com.andyawd.seenote.writenote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderNoteTagBodyBinding

class NoteTagBodyViewHolder(private val binding: ViewholderNoteTagBodyBinding) :
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
        binding.vwtMtvDelete.setOnClickListener {
            noteTagListener.onItemClick(tag)
        }
    }

    companion object {
        fun from(parent: ViewGroup): NoteTagBodyViewHolder {
            val binding = ViewholderNoteTagBodyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoteTagBodyViewHolder(binding)
        }
    }
}