package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.HackmdCreateNoteUrlProduct
import tw.com.andyawd.seenote.http.product.UrlProduct

class HackmdCreateNoteUrlFactory : UrlFactory {
    override var createUrl: UrlProduct
        get() = HackmdCreateNoteUrlProduct()
        set(value) {}
}