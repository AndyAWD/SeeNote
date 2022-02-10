package tw.com.andyawd.seenote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.SETTING_TABLE)
data class Setting(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.SETTING_SIZE)
    var settingSize: Float = 80F,

    @ColumnInfo(name = BaseConstants.PAGE_SIZE)
    var pageSize: Float = 80F,

    @ColumnInfo(name = BaseConstants.WRITE_SIZE)
    var writeSize: Float = BaseConstants.TEXT_SIZE,

    @ColumnInfo(name = BaseConstants.SELECT_SIZE)
    var selectSize: Float = BaseConstants.TEXT_SIZE,

    @ColumnInfo(name = BaseConstants.TITLE_TEXT_COLOR)
    var titleTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.TITLE_BACKGROUND_COLOR)
    var titleBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT_TEXT_COLOR)
    var contentTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT_BACKGROUND_COLOR)
    var contentBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CREATE_DATE_TEXT_COLOR)
    var dateTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CREATE_DATE_BACKGROUND_COLOR)
    var dateBackgroundColor: String = BaseConstants.EMPTY_STRING,
)
