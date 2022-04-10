package tw.com.andyawd.seenote.bean

import androidx.room.*
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.StringTypeConverter

@Entity(tableName = BaseConstants.NOTE_TABLE)
@TypeConverters(StringTypeConverter::class)
data class Note(
    @ColumnInfo(name = BaseConstants.ID)
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.TITLE)
    var title: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT)
    var content: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.TAG)
    var tag: List<String> = listOfNotNull(),

    @ColumnInfo(name = BaseConstants.HACKMD_ID)
    var hackmdId: String = BaseConstants.EMPTY_STRING,

    @Embedded(prefix = "date_")
    var date: Date?,

    @Embedded(prefix = "titleColor_")
    var titleColor: Color?,

    @Embedded(prefix = "contentColor_")
    var contentColor: Color?,

    @Embedded(prefix = "tagColor_")
    var tagColor: Color?,

    @Embedded(prefix = "dateColor_")
    var dateColor: Color?
)
