package tw.com.andyawd.seenote.bean.hackmd

data class HackmdNote(
    val content: String,
    val createdAt: Long,
    val id: String,
    val lastChangeUser: LastChangeUser,
    val lastChangedAt: Long,
    val permalink: Any,
    val publishType: String,
    val publishedAt: Any,
    val readPermission: String,
    val shortId: String,
    val tags: List<String>,
    val teamPath: Any,
    val title: String,
    val userPath: String,
    val writePermission: String
)