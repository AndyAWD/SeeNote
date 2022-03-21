package tw.com.andyawd.seenote.http.product

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.bean.hackmd.CreateNote

class CreateNoteBodyProduct(private val content: String) : BodyProduct {
    override fun getBody(): RequestBody {
        val mediaType = CONTENT_TYPE_UTF_8.toMediaType()
        val createNote = CreateNote(
            content = content
        )

        val json = Json.encodeToString(CreateNote.serializer(), createNote)
        AWDLog.d("json: $json")
        return json.toRequestBody(mediaType)
    }

    companion object {
        const val CONTENT_TYPE_UTF_8 = "application/json; charset=utf-8"
    }
}