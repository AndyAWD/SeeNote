package tw.com.andyawd.seenote.writenote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderWriteTagBinding

class WriteTagViewHolder(private val binding: ViewholderWriteTagBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: String, note: Note?, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }

        note?.let {
            binding.note = note
        }

        binding.vwtMtvTag.text = tag
        binding.executePendingBindings()
    }

    fun bind(tag: String, note: Note?, setting: Setting?, writeTagListener: WriteTagListener) {
        setting?.let {
            binding.setting = setting
        }

        note?.let {
            binding.note = note
        }

        binding.vwtMtvTag.text = tag
        binding.executePendingBindings()
        binding.vwtMtvDelete.setOnClickListener {
            writeTagListener.onItemClick(tag)
        }
    }

    companion object {
        fun from(parent: ViewGroup): WriteTagViewHolder {
            val binding = ViewholderWriteTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return WriteTagViewHolder(binding)
        }
    }
}