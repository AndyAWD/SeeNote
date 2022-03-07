package tw.com.andyawd.seenote.bean

import androidx.room.*
import tw.com.andyawd.seenote.BaseConstants

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = BaseConstants.NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.TITLE)
    var title: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT)
    var content: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.LABEL)
    var label: String = BaseConstants.EMPTY_STRING,

    @Embedded(prefix = "date_")
    var date: Date?,

    @Embedded(prefix = "titleColor_")
    var titleColor: Color?,

    @Embedded(prefix = "contentColor_")
    var contentColor: Color?,

    @Embedded(prefix = "labelColor_")
    var labelColor: Color?,

    @Embedded(prefix = "dateColor_")
    var dateColor: Color?
)
