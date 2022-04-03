package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Ignore
import tw.com.andyawd.seenote.BaseConstants

data class Tag(
    @Ignore
    var id: Long = 0L,
    @ColumnInfo(name = BaseConstants.TAG)
    var text: String = BaseConstants.EMPTY_STRING
)
