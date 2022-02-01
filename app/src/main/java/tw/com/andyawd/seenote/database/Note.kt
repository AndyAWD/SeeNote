package tw.com.andyawd.seenote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.TITLE)
    var title: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT)
    var content: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CREATE_DATE)
    var createDate: Long = System.currentTimeMillis(),
)
