package com.ztf.realkot.manager.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header("User-Agent", "Android")
            .build()
        return chain.proceed(request)
    }
}