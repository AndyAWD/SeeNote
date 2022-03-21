package tw.com.andyawd.seenote.http.product

import okhttp3.RequestBody

interface BodyProduct {
    fun getBody(): RequestBody
}