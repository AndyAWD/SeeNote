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
    var writeSize: Float = 80F,

    @ColumnInfo(name = BaseConstants.BUTTON_TEXT_COLOR)
    var buttonTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.BUTTON_BACKGROUND_COLOR)
    var buttonBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.TITLE_TEXT_COLOR)
    var titleTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.TITLE_BACKGROUND_COLOR)
    var titleBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT_TEXT_COLOR)
    var contentTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CONTENT_BACKGROUND_COLOR)
    var contentBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CREATE_DATE_TEXT_COLOR)
    var createDateTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.CREATE_DATE_BACKGROUND_COLOR)
    var createDateBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.EDIT_DATE_TEXT_COLOR)
    var editDateTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.EDIT_DATE_BACKGROUND_COLOR)
    var editDateBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.HORIZONTAL_LINE_COLOR)
    var horizontalLineColor: String = BaseConstants.EMPTY_STRING,
)
