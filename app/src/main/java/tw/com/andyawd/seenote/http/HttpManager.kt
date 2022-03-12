package tw.com.andyawd.seenote.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.*
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseApplication
import tw.com.andyawd.seenote.BaseConstants
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class HttpManager {
    companion object {
        val INSTANCE = HttpManagerHolder.httpManagerHolder
    }

    private object HttpManagerHolder {
        val httpManagerHolder = HttpManager()
    }

    fun get(value: String, token: String, listener: HttpResponseListener) {
        if (isNetworkAvailable()) {
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .addHeader(BaseConstants.AUTHORIZATION, getHeaderValue(token))
                .url("${BaseConstants.API_HACKMD}/$value")
                .get()
                .build()
            httpResponse(okHttpClient, request, listener)
        } else {
            listener.onFailure(BaseConstants.NETWORK_FAIL, BaseConstants.EMPTY_STRING)
        }
    }

    private fun httpResponse(
        okHttpClient: OkHttpClient,
        request: Request,
        listener: HttpResponseListener
    ) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                AWDLog.d("onFailure call: $call / IOException: $e")

                if (call.execute().code in 400..499) {
                    listener.onFailure(BaseConstants.HTTP_4XX_FAIL, call.request().body.toString())
                    return
                }

                if (call.execute().code in 500..599) {
                    listener.onFailure(BaseConstants.HTTP_5XX_FAIL, call.request().body.toString())
                    return
                }

                listener.onFailure(
                    BaseConstants.DOWNLOAD_FAIL,
                    call.request().body.toString()
                )
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string().toString()
                AWDLog.d("onResponse call: $call / responseBody: $responseBody")

                if (BaseConstants.UNAUTHORIZED == responseBody) {
                    listener.onFailure(
                        BaseConstants.HACKMD_TOKEN_FAIL,
                        call.request().body.toString()
                    )
                    return
                }

                listener.onSuccess(responseBody)
            }
        })
    }

    private fun getHeaderValue(token: String) = "${BaseConstants.BEARER} ${getEncodeToken(token)}"

    private fun getEncodeToken(token: String) =
        URLEncoder.encode(token, StandardCharsets.UTF_8.toString())

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = BaseApplication.context()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkActive = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            networkActive.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkActive.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            networkActive.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            networkActive.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}