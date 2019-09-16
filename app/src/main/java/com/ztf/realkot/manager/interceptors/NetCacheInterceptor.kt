package com.ztf.realkot.manager.interceptors

import com.ztf.realkot.app.App
import com.ztf.realkot.utils.NetUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

/**
 * Created by ztf on 2018/8/10.
 */

class NetCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetUtil.isWifiConnected(App.appContext)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }


        val response = chain.proceed(request)

        if (NetUtil.isNetworkAvailable(App.appContext)) {
            //有网时 cookie 保存时长
            val maxAge = 0
            response.newBuilder()
                .header("Cache-Control", "public,max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        } else {
            //无网时 cookie 保存时长
            val maxState = 0
            response.newBuilder()
                .header("Cache-Control", "public,only-if-cached,max-state=$maxState")
                .removeHeader("Pragma")
                .build()
        }
        return response
    }
}
