package tw.com.andyawd.seenote.notepage

import androidx.recyclerview.widget.DiffUtil
import tw.com.andyawd.seenote.database.Note

class NotePageDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}