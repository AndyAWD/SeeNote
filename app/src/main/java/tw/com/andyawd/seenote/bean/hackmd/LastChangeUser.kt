package tw.com.andyawd.seenote.bean.hackmd

import kotlinx.serialization.Serializable

@Serializable
data class LastChangeUser(
    val biography: String?,
    val name: String?,
    val photo: String?,
    val userPath: String?
)