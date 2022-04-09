package tw.com.andyawd.seenote.tagpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.databinding.ViewholderTagPageHeaderBinding

class TagPageHeaderViewHolder(private val binding: ViewholderTagPageHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(setting: Setting?, tagPageListener: TagPageListener) {
        setting?.let {
            binding.setting = setting
        }

        binding.executePendingBindings()

        binding.vtphClTag.setOnClickListener {
            tagPageListener.onItemClick(BaseConstants.ALL_TAG)
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
            val binding = ViewholderTagPageHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TagPageHeaderViewHolder(binding)
        }
    }
}