package tw.com.andyawd.seenote.notepage

import androidx.recyclerview.widget.DiffUtil

class NotePageDiffCallback : DiffUtil.ItemCallback<NotePageItem>() {
    override fun areItemsTheSame(oldItem: NotePageItem, newItem: NotePageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotePageItem, newItem: NotePageItem): Boolean {
        return oldItem == newItem
    }
}