package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.AuthorizationProduct

interface AuthorizationFactory {
    var createAuthorization: AuthorizationProduct
}