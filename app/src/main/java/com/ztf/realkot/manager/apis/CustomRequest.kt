package com.ztf.realkot.manager.apis

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author ztf
 * @date 2019/9/4
 */
interface CustomRequest {
    @FormUrlEncoded
    @POST("80708219")
    fun getResult(@FieldMap params: HashMap<String, String>): Call<String>
}
