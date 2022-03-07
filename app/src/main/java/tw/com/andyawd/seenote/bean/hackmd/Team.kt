package tw.com.andyawd.seenote.bean.hackmd

data class Team(
    val createdAt: Long,
    val description: String,
    val id: String,
    val logo: String,
    val name: String,
    val ownerId: String,
    val path: String,
    val visibility: String
)