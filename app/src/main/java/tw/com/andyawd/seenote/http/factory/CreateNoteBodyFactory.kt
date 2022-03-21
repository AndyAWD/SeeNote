package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.BodyProduct
import tw.com.andyawd.seenote.http.product.CreateNoteBodyProduct

class CreateNoteBodyFactory(private val content: String) : BodyFactory {
    override var createBody: BodyProduct
        get() = CreateNoteBodyProduct(content)
        set(value) {}
}