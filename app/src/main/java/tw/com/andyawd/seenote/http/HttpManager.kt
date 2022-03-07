package tw.com.andyawd.seenote.http

import okhttp3.*
import tw.com.andyawd.seenote.BaseConstants
import java.io.IOException

class HttpManager {
    companion object {
        val INSTANCE = HttpManagerHolder.httpManagerHolder
    }

    private object HttpManagerHolder {
        val httpManagerHolder = HttpManager()
    }

    fun get(value: String, token: String, listener: HttpResponseListener) {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .addHeader(BaseConstants.AUTHORIZATION, "${BaseConstants.BEARER} $token")
            .url("${BaseConstants.API_HACKMD}/$value")
            .get()
            .build()
        httpResponse(okHttpClient, request, listener)
    }

    private fun httpResponse(
        okHttpClient: OkHttpClient,
        request: Request,
        listener: HttpResponseListener
    ) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                listener.onFailure(
                    call.execute().code.toString(),
                    call.request().body.toString()
                )
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string().toString()
                listener.onSuccess(responseBody)
            }
        })
    }
}