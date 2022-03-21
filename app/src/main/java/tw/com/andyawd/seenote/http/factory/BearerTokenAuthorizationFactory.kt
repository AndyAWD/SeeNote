package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.AuthorizationProduct
import tw.com.andyawd.seenote.http.product.BearerTokenAuthorizationProduct

class BearerTokenAuthorizationFactory(private val token: String) : AuthorizationFactory {
    override var createAuthorization: AuthorizationProduct
        get() = BearerTokenAuthorizationProduct(token)
        set(value) {}
}