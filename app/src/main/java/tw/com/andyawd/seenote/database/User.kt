package tw.com.andyawd.seenote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = BaseConstants.NICKNAME)
    var nickname: String = BaseConstants.EMPTY_STRING,

    @ColumnInfo(name = BaseConstants.HACKMD_TOKEN)
    var hackmdToken: String = BaseConstants.EMPTY_STRING,
)
