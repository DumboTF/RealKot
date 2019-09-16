package com.ztf.realkot.manager

import com.google.gson.GsonBuilder
import com.ztf.realkot.manager.apis.CustomRequest
import com.ztf.realkot.manager.interceptors.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author ztf
 * @date 2019/9/4
 */
class NetHelper {

    companion object {
        private var retrofit: Retrofit? = null
        private var client: OkHttpClient? = null
        private var sApiManager: NetHelper? = null
        val instance: NetHelper?
            get() {
                if(sApiManager==null){
                    sApiManager = NetHelper()
                }
                if (client == null) {
                    client = OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()
                }
                if (retrofit == null) {
                    val gson = GsonBuilder().setLenient().serializeNulls().create()
                    retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client!!)
                        .build()
                }
                return sApiManager
            }
    }

    private var custom: CustomRequest? = null
    fun getCustomService(): CustomRequest? {
        if (custom == null) {
            custom = retrofit?.create(CustomRequest::class.java)
        }
        return custom
    }
}