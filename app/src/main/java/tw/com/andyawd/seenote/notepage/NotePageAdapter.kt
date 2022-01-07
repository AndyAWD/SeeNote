package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.database.Note

class NotePageAdapter() : ListAdapter<Note, NotePageViewHolder>(NotePageDiffCallback()) {

    private var notePageListener: NotePageListener? = null

    fun setOnItemClickListener(notePageListener: NotePageListener) {
        this.notePageListener = notePageListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotePageViewHolder {
        return NotePageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotePageViewHolder, position: Int) {
        val item = getItem(position)

        if (notePageListener == null) {
            holder.bind(item)
        } else {
            holder.bind(item, notePageListener!!)
        }
    }
}