package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.BodyProduct

interface BodyFactory {
    var createBody: BodyProduct
}