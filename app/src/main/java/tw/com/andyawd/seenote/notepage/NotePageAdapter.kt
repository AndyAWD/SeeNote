package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.database.Setting

class NotePageAdapter : ListAdapter<Note, NotePageBodyViewHolder>(NotePageDiffCallback()) {

    private var notePageListener: NotePageListener? = null
    private var setting: Setting? = null

    fun setOnItemClickListener(notePageListener: NotePageListener) {
        this.notePageListener = notePageListener
    }

    fun changeSetting(setting: Setting) {
        this.setting = setting
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotePageBodyViewHolder {
        return NotePageBodyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotePageBodyViewHolder, position: Int) {
        val item = getItem(position)

        if (notePageListener == null) {
            holder.bind(item, setting)
        } else {
            holder.bind(item, setting, notePageListener!!)
        }
    }
}