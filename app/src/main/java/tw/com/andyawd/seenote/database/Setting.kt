package tw.com.andyawd.seenote.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.com.andyawd.seenote.BaseConstants

@Entity(tableName = BaseConstants.SETTING_TABLE)
data class Setting(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @Embedded(prefix = "textSize_")
    var textSize: TextSize?,

    @Embedded(prefix = "title_")
    var title: Color?,

    @Embedded(prefix = "content_")
    var content: Color?,

    @Embedded(prefix = "date_")
    var date: Color?,

    @Embedded(prefix = "label_")
    var label: Color?,

    @Embedded(prefix = "user_")
    var user: User?
)
