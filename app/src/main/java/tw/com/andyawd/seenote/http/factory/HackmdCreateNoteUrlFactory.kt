package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.HackmdCreateNoteUrlProduct
import tw.com.andyawd.seenote.http.product.UrlProduct

class HackmdCreateNoteUrlFactory : UrlFactory {
    override var url: UrlProduct
        get() = HackmdCreateNoteUrlProduct()
        set(value) {}
}