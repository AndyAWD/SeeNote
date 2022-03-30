package tw.com.andyawd.seenote.http.product

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.hackmd.CreateNote

class CreateNoteBodyProduct(
    private val title: String,
    private val tag: List<String>?,
    private val content: String
) : BodyProduct {
    override fun getBody(): RequestBody {
        val mediaType = CONTENT_TYPE_UTF_8.toMediaType()
        val titleFormat = "# $title\n\n"
        val tagFormat: String = if (tag.isNullOrEmpty()) {
            BaseConstants.EMPTY_STRING
        } else {
            tag.joinToString(separator = "\n") { "###### tags: `$it`" }
        }
        val horizontalLine = "\n\n***"
        val contentFormat = "\n\n$content"

        val createNote = CreateNote(
            content = "$titleFormat$tagFormat$horizontalLine$contentFormat"
        )

        val json = Json.encodeToString(CreateNote.serializer(), createNote)
        return json.toRequestBody(mediaType)
    }

    companion object {
        const val CONTENT_TYPE_UTF_8 = "application/json; charset=utf-8"
    }
}