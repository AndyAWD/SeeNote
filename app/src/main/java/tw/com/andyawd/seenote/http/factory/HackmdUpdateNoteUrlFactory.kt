package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.HackmdUpdateNoteUrlProduct
import tw.com.andyawd.seenote.http.product.UrlProduct

class HackmdUpdateNoteUrlFactory(private val noteId: String) : UrlFactory {
    override var createUrl: UrlProduct
        get() = HackmdUpdateNoteUrlProduct(noteId)
        set(value) {}
}