package com.robpridham.starwarschars

import android.os.Handler
import com.robpridham.starwarschars.data.SwapiDotCoStarWarsService
import com.robpridham.starwarschars.json.JacksonParser
import com.robpridham.starwarschars.network.OkHttpNetworkService
import okhttp3.OkHttpClient

class ServiceContainer(handler: Handler) {

    private val networkService = OkHttpNetworkService(OkHttpClient(), handler)
    val starWarsService =  SwapiDotCoStarWarsService(networkService, JacksonParser())
}