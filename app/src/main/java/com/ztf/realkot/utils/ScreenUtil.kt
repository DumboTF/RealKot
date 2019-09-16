package com.ztf.realkot.utils

import android.app.Activity
import android.util.DisplayMetrics

/**
 * @author ztf
 * @date 2019/9/9
 */
class ScreenUtil {
    companion object {
        fun getScreenWidth(context: Activity): Int {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }
    }
}