package tw.com.andyawd.seenote.notepage

import tw.com.andyawd.seenote.bean.Note

class NotePageListener(val itemClickListener: (id: Long) -> Unit) {
    fun onItemClick(note: Note) = itemClickListener(note.id)
}