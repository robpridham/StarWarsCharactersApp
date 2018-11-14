package com.robpridham.starwarschars.network

import android.os.Handler
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Result
import com.robpridham.starwarschars.util.Success
import okhttp3.*
import java.io.IOException

interface NetworkService {
    fun getRequest(url: String, parameters: Map<String, String>? = null, onResult: (Result<String, Error>)->Unit)
}

class OkHttpNetworkService(private val okHttpClient: OkHttpClient, private val handler: Handler): NetworkService {

    override fun getRequest(url: String, parameters: Map<String, String>?, onResult: (Result<String, Error>)->Unit) {

        val httpUrlBuilder = HttpUrl.parse(url)?.newBuilder()
        parameters?.entries?.forEach {
            httpUrlBuilder?.addQueryParameter(it.key, it.value)
        }

        httpUrlBuilder?.let {

            val request = Request.Builder().url(httpUrlBuilder.toString()).build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    handler.post {
                        onResult(Failure(Error(e.message)))
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post {
                        response.body()?.let { body ->
                            onResult(Success(body.string()))
                        } ?: onResult(Failure(Error("Empty response")))
                    }
                }
            })
        } ?: onResult(Failure(Error("Couldn't form request URL")))
    }
}