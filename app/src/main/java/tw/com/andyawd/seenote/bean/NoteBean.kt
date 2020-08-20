package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.base.BaseConstants

@Entity(tableName = BaseConstants.ROOM_NOTE)
data class NoteBean(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseConstants.ID)
    var id: Long = 0,

    @ColumnInfo(name = BaseConstants.TITLE)
    var title: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT)
    var content: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.TIME)
    var time: Int = 0
)