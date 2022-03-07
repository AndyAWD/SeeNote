package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.COLOR_TABLE)
data class Color(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.TEXT_COLOR)
    var textColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.BACKGROUND_COLOR)
    var backgroundColor: String = BaseConstants.EMPTY_STRING,
)
