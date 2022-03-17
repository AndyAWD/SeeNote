package tw.com.andyawd.seenote.http.factory

import tw.com.andyawd.seenote.http.product.BodyProduct
import tw.com.andyawd.seenote.http.product.CreateNoteBodyProduct

class CreateNoteBodyFactory(private val title: String, private val content: String) : BodyFactory {
    override var createPostBody: BodyProduct
        get() = CreateNoteBodyProduct(title, content)
        set(value) {}
}