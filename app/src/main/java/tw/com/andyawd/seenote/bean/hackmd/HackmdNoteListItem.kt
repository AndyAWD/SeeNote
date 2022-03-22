package tw.com.andyawd.seenote.bean.hackmd

import androidx.room.*
import kotlinx.serialization.Serializable
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.StringTypeConverter

@Entity(tableName = BaseConstants.HACKMD_NOTE_LIST_ITEM_TABLE)
@Serializable
@TypeConverters(StringTypeConverter::class)
data class HackmdNoteListItem(
    @ColumnInfo(name = BaseConstants.PRIMARY_ID)
    @PrimaryKey(autoGenerate = true)
    var primaryId: Long = 0L,

    @ColumnInfo(name = BaseConstants.CREATED_AT)
    val createdAt: Long,

    @ColumnInfo(name = BaseConstants.ID)
    val id: String?,

    @Embedded(prefix = "lastChangeUser_")
    val lastChangeUser: LastChangeUser?,

    @ColumnInfo(name = BaseConstants.LAST_CHANGED_AT)
    val lastChangedAt: Long,

    @ColumnInfo(name = BaseConstants.PERMALINK)
    val permalink: String?,

    @ColumnInfo(name = BaseConstants.PUBLISH_TYPE)
    val publishType: String?,

    @ColumnInfo(name = BaseConstants.PUBLISHED_AT)
    val publishedAt: String?,

    @ColumnInfo(name = BaseConstants.READ_PERMISSION)
    val readPermission: String?,

    @ColumnInfo(name = BaseConstants.SHORT_ID)
    val shortId: String?,

    val tags: List<String>?,

    @ColumnInfo(name = BaseConstants.TEAM_PATH)
    val teamPath: String?,

    @ColumnInfo(name = BaseConstants.TITLE)
    val title: String?,

    @ColumnInfo(name = BaseConstants.USER_PATH)
    val userPath: String?,

    @ColumnInfo(name = BaseConstants.WRITE_PERMISSION)
    val writePermission: String?
)