package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import tw.com.andyawd.seenote.BaseConstants

data class Tag(
    @ColumnInfo(name = BaseConstants.TAG)
    val text: String = BaseConstants.EMPTY_STRING
)
