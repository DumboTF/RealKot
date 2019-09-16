package com.ztf.realkot.manager.interceptors

import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.nio.charset.Charset

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

/**
 * Created by ztf on 2018/8/10.
 */

class EncryptInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //加密
        request = encrypt(request)
        //传输
        var response = chain.proceed(request)
        //解密
        response = decrypt(response)

        return response
    }

    @Throws(IOException::class)
    private fun encrypt(request: Request): Request {
        var req = request

        val requestBody = req.body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            var charset: Charset? = Charset.forName("UTF-8")
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset()
            }
            val srcStr = buffer.readString(charset!!)
            val enStr = "123$srcStr"
            val body = enStr.toRequestBody(contentType)
            req = request.newBuilder().post(body).build()
        }
        return request
    }

    @Throws(IOException::class)
    private fun decrypt(response: Response): Response {
        var resp = response
        if (resp.isSuccessful) {
            val responseBody = resp.body
            if (responseBody != null) {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE)

                val buffer = source.buffer

                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset()
                }

                val resStr = buffer.clone().readString(charset!!)
                val deStr = resStr.substring(3)

                val body = deStr.toResponseBody(contentType)
                resp = response.newBuilder().body(body).build()
            }
        }
        return resp
    }
}
