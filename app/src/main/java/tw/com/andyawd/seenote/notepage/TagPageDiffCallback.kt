package tw.com.andyawd.seenote.notepage

import androidx.recyclerview.widget.DiffUtil

class TagPageDiffCallback : DiffUtil.ItemCallback<TagDataItem>() {
    override fun areItemsTheSame(oldItem: TagDataItem, newItem: TagDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TagDataItem, newItem: TagDataItem): Boolean {
        return oldItem == newItem
    }
}