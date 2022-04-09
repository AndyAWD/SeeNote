package tw.com.andyawd.seenote.tagpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderTagPageBodyBinding

class TagPageBodyViewHolder(private val binding: ViewholderTagPageBodyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: String, setting: Setting?, tagPageListener: TagPageListener) {
        setting?.let {
            binding.setting = setting
        }

        AWDLog.d("tag: $tag")
        binding.vtpbMtvTag.text = tag
        binding.vtpbClTag.setOnClickListener {
            tagPageListener.onItemClick(tag)
        }
        binding.executePendingBindings()


    }

    fun bind(tag: String, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }
        binding.vtpbMtvTag.text = tag
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TagPageBodyViewHolder {
            val binding = ViewholderTagPageBodyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TagPageBodyViewHolder(binding)
        }
    }
}