package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.database.Note

class NotePageAdapter() : ListAdapter<Note, NotePageViewHolder>(NotePageDiffCallback()) {

    override fun onBindViewHolder(holder: NotePageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotePageViewHolder {
        return NotePageViewHolder.from(parent)
    }
}
