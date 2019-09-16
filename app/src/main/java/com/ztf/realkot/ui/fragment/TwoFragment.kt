package com.ztf.realkot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ztf.realkot.R
import com.ztf.realkot.bean.MessageWrap
import com.ztf.realkot.mwidgets.ProView
import com.ztf.realkot.ui.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author ztf
 * @date 2019/8/28
 */
class TwoFragment : BaseFragment() {
    var rootView: View? = null
    var proView: ProView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        } else {
            rootView = inflater.inflate(R.layout.frag_two, container, false)
        }
        init()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    private fun init() {
        proView = rootView?.findViewById(R.id.pro_view)
        proView?.setListener({ println("click") }, {
            println("long click")
            true
        })
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetEventMsg2(messageWrap: MessageWrap) {
        println("two frag = ${messageWrap.message}")
    }
}