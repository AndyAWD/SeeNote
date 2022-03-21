package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.UrlProduct

interface UrlFactory {
    var createUrl: UrlProduct
}