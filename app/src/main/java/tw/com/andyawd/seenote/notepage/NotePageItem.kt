package tw.com.andyawd.seenote.notepage

import tw.com.andyawd.seenote.database.Note

sealed class NotePageItem {
    abstract val id: Long

    object Header : NotePageItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }

    data class Body(val note: Note) : NotePageItem() {
        override val id: Long
            get() = note.id
    }
}
