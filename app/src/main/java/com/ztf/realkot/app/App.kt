package com.ztf.realkot.app

import android.app.Application
import androidx.multidex.MultiDex
import com.ztf.realkot.utils.ScreenUtil

/*
* @author ztf
 * @date 2019/9/9
 */
class App : Application() {

    //TODO REMINDER: register this class in AndroidManifest.xml


    override fun onCreate() {
        super.onCreate()
        appContext = this
        val crashHandler = CrashHandler.instance
        crashHandler.init(applicationContext)
        MultiDex.install(applicationContext)
    }

    companion object {
        var appContext: App? = null
        var screenWidth: Int = 0
    }
}