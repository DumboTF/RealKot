package com.ztf.realkot.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author ztf
 * @date 2019/9/9
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        activity = this
    }

    companion object {
        lateinit var activity: BaseActivity
    }
}
