package tw.com.andyawd.seenote.http

interface HttpResponseListener {
    fun onFailure(status: String, responseBody: String)
    fun onSuccess(responseBody: String)
}