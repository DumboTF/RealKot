package com.ztf.realkot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ztf.realkot.R
import com.ztf.realkot.bean.MessageWrap
import com.ztf.realkot.bean.User
import com.ztf.realkot.manager.DbHelper
import com.ztf.realkot.manager.NetHelper
import com.ztf.realkot.ui.BaseFragment
import com.ztf.realkot.utils.CustomDivider
import kotlinx.android.synthetic.main.frag_one.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

/**
 * @author ztf
 * @date 2019/8/28
 */
class OneFragment : BaseFragment() {
    var rootView: View? = null
    var users: ArrayList<User> = arrayListOf()
    private val random = Random
    private val strs = mutableListOf("随机的", "是不是", "什么")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        } else {
            rootView = inflater.inflate(R.layout.frag_one, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler_view?.layoutManager = layoutManager
        recycler_view?.addItemDecoration(CustomDivider(this.context!!, LinearLayoutManager.VERTICAL))
        initUsers()
        val adapter = OneListAdapter(this.context!!, users)
        recycler_view?.adapter = adapter

        btn_add.setOnClickListener {
            val u = User()
            u.userName = et_name.text.toString()
            u.userAge = et_age.text.toString().toInt()
            DbHelper.instance.addUser(u)
        }
        btn_get.setOnClickListener {
            val rst = DbHelper.instance.queryUser()
            users.addAll(rst)
            adapter.notifyItemInserted(users.size - rst.size)
        }
        refresh_layout.setOnRefreshListener {
            val map = hashMapOf<String,String>()
            val customRequest = NetHelper.instance?.getCustomService()
            customRequest?.getResult(map)?.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    println(t.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        val result = response.body()
                        val str = result.toString()
                        println("")
                    }
                }

            })
            refresh_layout.isRefreshing = false
        }
    }

    private fun initUsers() {
        var user = User()
        user.userName = "a"
        users.add(user)
        user = User()
        user.userName = "b"
        users.add(user)
        user = User()
        user.userName = "c"
        users.add(user)
        user = User()
        user.userName = "d"
        users.add(user)
        user = User()
        user.userName = "e"
        users.add(user)
    }
}