package tw.com.andyawd.seenote.notepage

import androidx.recyclerview.widget.DiffUtil
import tw.com.andyawd.seenote.bean.Tag

class TagPageDiffCallback : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem == newItem
    }
}