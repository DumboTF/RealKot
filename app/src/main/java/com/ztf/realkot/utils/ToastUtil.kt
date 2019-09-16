package com.ztf.realkot.utils

import android.widget.Toast
import com.ztf.realkot.app.App

/**
 * @author ztf
 * @date 2019/7/30
 */
object ToastUtil {
    fun toast(string: String) {
        Toast.makeText(App.appContext, string, Toast.LENGTH_SHORT).show()
    }
}
