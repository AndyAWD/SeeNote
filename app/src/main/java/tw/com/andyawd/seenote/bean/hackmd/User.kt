package tw.com.andyawd.seenote.bean.hackmd

data class User(
    val email: Any,
    val id: String,
    val name: String,
    val photo: String,
    val teams: List<Team>,
    val userPath: String
)

