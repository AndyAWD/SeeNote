package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.TEXT_SIZE_TABLE)
data class TextSize(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.NOTE_PAGE)
    var notePage: Int = BaseConstants.TEXT_SIZE,

    @ColumnInfo(name = BaseConstants.SELECT_COLOR)
    var selectColor: Int = BaseConstants.TEXT_SIZE,

    @ColumnInfo(name = BaseConstants.SETTING_PAGE)
    var settingPage: Int = BaseConstants.TEXT_SIZE,

    @ColumnInfo(name = BaseConstants.WRITER_NOTE)
    var writerNote: Int = BaseConstants.TEXT_SIZE,
) {
    val notePageToFloat: Float
        get() = notePage.toFloat()

    val selectColorToFloat: Float
        get() = selectColor.toFloat()

    val settingPageToFloat: Float
        get() = settingPage.toFloat()

    val writerNoteToFloat: Float
        get() = writerNote.toFloat()
}
