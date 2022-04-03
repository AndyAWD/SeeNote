package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.Tag
import tw.com.andyawd.seenote.databinding.ViewholderTagPageBodyBinding

class TagPageBodyViewHolder(private val binding: ViewholderTagPageBodyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: Tag, setting: Setting?, tagPageListener: TagPageListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.tag = tag
        binding.clickListener = tagPageListener
        binding.executePendingBindings()
    }

    fun bind(tag: Tag, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }
        binding.tag = tag
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