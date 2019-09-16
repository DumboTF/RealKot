package com.ztf.realkot.manager.interceptors

import okhttp3.*

import java.io.IOException

/**
 * Created by ztf on 2018/8/10.
 */

class LogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //发送
        val httpUrl = request.url

        val connection = chain.connection()

        val headers = request.headers

        //接收
        val response = chain.proceed(request)

        val allData = response.body
        val someData = response.peekBody(1024)

        val content = response.body!!.string()

        val resHeaders = response.headers
        return response
    }
}
