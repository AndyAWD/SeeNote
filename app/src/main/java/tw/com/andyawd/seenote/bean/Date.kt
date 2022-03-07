package tw.com.andyawd.seenote.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.DATE_TABLE)
data class Date(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.CREATE)
    var create: Long = System.currentTimeMillis(),

    @ColumnInfo(name = BaseConstants.EDIT)
    var edit: Long = System.currentTimeMillis()
)
