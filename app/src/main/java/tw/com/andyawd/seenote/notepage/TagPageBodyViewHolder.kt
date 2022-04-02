package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderTagPageBinding

class TagPageBodyViewHolder(private val binding: ViewholderTagPageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: String, setting: Setting?, tagPageListener: TagPageListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.executePendingBindings()

        binding.vtpMtvTag.text = tag
        binding.vtpMtvTag.setOnClickListener {
            tagPageListener.onItemClick(tag)
        }
    }

    fun bind(tag: String, setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }

        binding.executePendingBindings()

        binding.vtpMtvTag.text = tag
    }

    companion object {
        fun from(parent: ViewGroup): TagPageBodyViewHolder {
            val binding = ViewholderTagPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TagPageBodyViewHolder(binding)
        }
    }
}