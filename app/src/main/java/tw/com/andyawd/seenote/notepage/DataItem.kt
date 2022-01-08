package tw.com.andyawd.seenote.notepage

import tw.com.andyawd.seenote.database.Note

sealed class DataItem {
    abstract val id: Long

    object Header : DataItem() {
        override val id: Long
            get() = Long.MAX_VALUE
    }

    data class NotePageItem(val note: Note) : DataItem() {
        override val id: Long
            get() = note.id
    }
}
