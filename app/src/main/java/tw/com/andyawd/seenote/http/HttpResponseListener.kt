package tw.com.andyawd.seenote.http

interface HttpResponseListener {
    fun onFailure(code: String, responseBody: String)
    fun onSuccess(responseBody: String)
}