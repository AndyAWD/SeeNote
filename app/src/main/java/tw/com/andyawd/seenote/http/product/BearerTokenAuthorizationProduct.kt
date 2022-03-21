package tw.com.andyawd.seenote.http.product

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class BearerTokenAuthorizationProduct(private val token: String) : AuthorizationProduct {
    override fun getAuthorization(): String {
        return "$BEARER ${getEncodeToken(token)}"
    }

    private fun getEncodeToken(token: String) =
        URLEncoder.encode(token, StandardCharsets.UTF_8.toString())

    companion object {
        const val BEARER = "Bearer"
    }
}