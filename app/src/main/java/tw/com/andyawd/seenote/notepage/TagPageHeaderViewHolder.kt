package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderTagPageBinding

class TagPageHeaderViewHolder(private val binding: ViewholderTagPageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(setting: Setting?, tagPageListener: TagPageListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.executePendingBindings()

        binding.vtpClTag.setOnClickListener {
            tagPageListener.onItemClick(BaseConstants.EMPTY_STRING)
        }
    }

    fun bind(setting: Setting?) {
        setting?.let {
            binding.setting = setting
        }

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TagPageHeaderViewHolder {
            val binding = ViewholderTagPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TagPageHeaderViewHolder(binding)
        }
    }
}