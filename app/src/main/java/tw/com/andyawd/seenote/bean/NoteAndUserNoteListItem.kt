package tw.com.andyawd.seenote.bean

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.hackmd.UserNoteListItem

data class NoteAndUserNoteListItem(
    @Embedded(prefix = "note_")
    val note: Note?,

    @Relation(
        parentColumn = "note_hackmd_id",
        entityColumn = BaseConstants.ID,
        entity = UserNoteListItem::class,
        associateBy = Junction(NoteAndUserNoteListItemRelation::class)
    )
    val notList: List<UserNoteListItem>?
)
