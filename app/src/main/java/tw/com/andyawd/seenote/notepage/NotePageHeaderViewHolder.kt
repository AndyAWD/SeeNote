package tw.com.andyawd.seenote.notepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.R

class NotePageHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): NotePageHeaderViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.viewholder_note_page_header, parent, false)
            return NotePageHeaderViewHolder(view)
        }
    }

}