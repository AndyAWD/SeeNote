package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable
import tw.com.andyawd.seenote.BaseConstants

@Entity(
    tableName = BaseConstants.NOTE_AND_USER_NOTE_LIST_ITEM_TABLE,
    primaryKeys = ["note_hackmd_id", BaseConstants.ID]
)
@Serializable
data class NoteAndUserNoteListItemRelation(
    @ColumnInfo(name = "note_hackmd_id")
    val hackmdId: String,

    @ColumnInfo(name = BaseConstants.ID)
    val id: String
)
