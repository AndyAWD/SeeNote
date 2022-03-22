package tw.com.andyawd.seenote.http.product

class HackmdUpdateNoteUrlProduct(private val noteId: String) : UrlProduct {
    override fun getUrl(): String {
        return "$API_URL/$noteId"
    }

    companion object {
        const val API_URL = "https://api.hackmd.io/v1/notes"
    }
}