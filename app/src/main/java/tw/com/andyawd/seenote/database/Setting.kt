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

    @ColumnInfo(name = BaseConstants.SETTING_TEXT_COLOR)
    var settingTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.SETTING_BACKGROUND_COLOR)
    var settingBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.PAGE_SIZE)
    var pageSize: Float = 80F,

    @ColumnInfo(name = BaseConstants.PAGE_TITLE_TEXT_COLOR)
    var pageTitleTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.PAGE_TITLE_BACKGROUND_COLOR)
    var pageTitleBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.PAGE_CONTENT_TEXT_COLOR)
    var pageContentTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.PAGE_CONTENT_BACKGROUND_COLOR)
    var pageContentBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.PAGE_HORIZONTAL_LINE_COLOR)
    var pageHorizontalLineColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.WRITE_SIZE)
    var writeSize: Float = 80F,

    @ColumnInfo(name = BaseConstants.WRITE_TITLE_TEXT_COLOR)
    var writeTitleTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.WRITE_TITLE_BACKGROUND_COLOR)
    var writeTitleBackgroundColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.WRITE_CONTENT_TEXT_COLOR)
    var writeContentTextColor: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.WRITE_CONTENT_BACKGROUND_COLOR)
    var writeContentBackgroundColor: String = BaseConstants.EMPTY_STRING
)
