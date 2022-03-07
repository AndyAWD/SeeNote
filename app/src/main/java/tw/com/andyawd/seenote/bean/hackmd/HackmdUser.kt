package tw.com.andyawd.seenote.bean.hackmd

data class HackmdUser(
    val email: String,
    val id: String,
    val name: String,
    val photo: String,
    val teams: List<Team>,
    val userPath: String
)

