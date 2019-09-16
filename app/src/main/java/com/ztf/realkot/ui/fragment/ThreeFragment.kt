package com.ztf.realkot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ztf.realkot.R
import com.ztf.realkot.bean.MessageWrap
import com.ztf.realkot.mwidgets.LineView
import com.ztf.realkot.mwidgets.ProView
import com.ztf.realkot.ui.BaseFragment
import org.greenrobot.eventbus.EventBus

/**
 * @author ztf
 * @date 2019/8/28
 */
class ThreeFragment : BaseFragment() {
    var rootView: View? = null
    var lineView: LineView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        } else {
            rootView = inflater.inflate(R.layout.frag_three, container, false)
        }
        init()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("")
    }

    override fun onResume() {
        super.onResume()
        print("")
    }

    private fun init() {
        lineView = rootView?.findViewById(R.id.line_view)
        lineView?.setOnClickListener {
            EventBus.getDefault().post(MessageWrap.getInstance("what"))
        }
    }
}