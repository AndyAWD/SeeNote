package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.Note

class NotePageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val vnpMtvTitle: TextView = itemView.findViewById(R.id.vnpMtvTitle)
    private val vnpMtvContent: TextView = itemView.findViewById(R.id.vnpMtvContent)
    private val vnpMtvCreateTime: TextView = itemView.findViewById(R.id.vnpMtvCreateTime)

    fun setData(note: Note) {
        vnpMtvTitle.text = note.title
        vnpMtvContent.text = note.content
        vnpMtvCreateTime.text = note.createTime.toString()
    }

    companion object {
        fun from(parent: ViewGroup): NotePageViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.viewholder_note_page, parent, false)
            return NotePageViewHolder(view)
        }
    }
}