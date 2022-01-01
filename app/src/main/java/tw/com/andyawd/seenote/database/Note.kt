package tw.com.andyawd.seenote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = BaseConstants.NOTE_TITLE)
    var noteTitle: String = "",

    @ColumnInfo(name = BaseConstants.NOTE_CONTENT)
    var noteContent: String = "",

    @ColumnInfo(name = BaseConstants.NOTE_CREATE_TIME)
    var noteCreateTime: Long = System.currentTimeMillis(),
)
