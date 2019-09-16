package com.ztf.realkot.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by ztf on 2018/8/10.
 */

class NetUtil {
    companion object {
        fun isWifiConnected(context: Context?): Boolean {
            if (context == null) {
                return false
            }
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return info?.isAvailable ?: false
        }

        fun isMobileConnected(context: Context?): Boolean {
            if (context == null) {
                return false
            }
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return info?.isAvailable ?: false
        }

        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) {
                return false
            }
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return info?.isAvailable ?: false
        }

        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = manager.activeNetworkInfo
                if (info != null && info.isAvailable) {
                    return info.type
                }
            }
            return -1
        }

        fun ping(): Boolean {

            var result: String? = null
            try {
                val ip = "www.baidu.com"// ping 的地址，可以换成任何一种可靠的外网
                val p = Runtime.getRuntime().exec("ping -c 3 -w 100 $ip")// ping网址3次
                // 读取ping的内容，可以不加
                val input = p.inputStream
                val `in` = BufferedReader(InputStreamReader(input))
                val stringBuffer = StringBuilder()
                var content = `in`.readLine()
                while (content != null) {
                    stringBuffer.append(content)
                    content = `in`.readLine()
                }
                Log.d("------ping-----", "result content : $stringBuffer")
                // ping的状态
                val status = p.waitFor()
                if (status == 0) {
                    result = "success"
                    return true
                } else {
                    result = "failed"
                }
            } catch (e: IOException) {
                result = "IOException"
            } catch (e: InterruptedException) {
                result = "InterruptedException"
            } finally {
                Log.d("----result---", "result = " + result!!)
            }
            return false
        }
    }
}
