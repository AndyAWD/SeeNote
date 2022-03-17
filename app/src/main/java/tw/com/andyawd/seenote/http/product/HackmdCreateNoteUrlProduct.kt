package tw.com.andyawd.seenote.http.product

class HackmdCreateNoteUrlProduct : UrlProduct {
    override fun getUrl(): String {
        return API_URL
    }

    companion object {
        const val API_URL = "https://api.hackmd.io/v1/notes"
    }
}