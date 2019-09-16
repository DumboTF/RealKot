package com.ztf.realkot.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.ztf.realkot.R
import com.ztf.realkot.app.App
import com.ztf.realkot.bean.MessageWrap
import com.ztf.realkot.ui.BaseActivity
import com.ztf.realkot.ui.BaseFragment
import com.ztf.realkot.ui.fragment.OneFragment
import com.ztf.realkot.ui.fragment.ThreeFragment
import com.ztf.realkot.ui.fragment.TwoFragment
import com.ztf.realkot.utils.PkgManagerUtil
import com.ztf.realkot.utils.ScreenUtil
import com.ztf.realkot.utils.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    var bottomNavigationView: BottomNavigationView? = null
    var userHead: ImageView? = null
    var fragments: MutableList<BaseFragment>? = null

    var lastFragPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        userHead = navView.getHeaderView(0).findViewById(R.id.user_head)
        userHead!!.setOnClickListener {
            ToastUtil.toast("head")
        }
        init()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    override fun onResume() {
        super.onResume()
        App.screenWidth = ScreenUtil.getScreenWidth(this)
    }

    private fun init() {

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initFragment()

    }

    private fun initFragment() {
        fragments = mutableListOf()
        fragments!!.add(OneFragment())
        fragments!!.add(TwoFragment())
        fragments!!.add(ThreeFragment())
        fragments!!.add(OneFragment())
        setFragmentPosition(0)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                if (lastFragPosition == 0)
                    false
                else {
                    setFragmentPosition(0)
                    true
                }
            }
            R.id.navigation_dashboard -> {
                if (lastFragPosition == 1)
                    false
                else {
                    setFragmentPosition(1)
                    true
                }
            }
            R.id.navigation_notifications -> {
                if (lastFragPosition == 2)
                    false
                else {
                    setFragmentPosition(2)
                    true
                }
            }
            R.id.navigation_trans -> {
                if (lastFragPosition == 3)
                    false
                else {
                    setFragmentPosition(3)
                    true
                }
            }
            else -> {
                false
            }
        }
    }

    private fun setFragmentPosition(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        val curFragment = fragments?.get(position)
        val lastFragment = fragments?.get(lastFragPosition)
        if (lastFragment != null) {
            ft.hide(lastFragment)
        }
        lastFragPosition = position
        if (!curFragment?.isAdded!!) {
            supportFragmentManager.beginTransaction().remove(curFragment).commit()
            ft.add(R.id.ll_frameLayout, curFragment)
        }
        ft.show(curFragment)
        ft.commitAllowingStateLoss()
        ToastUtil.toast("" + position)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                PkgManagerUtil.create(baseContext).changeIcon(0)
                ToastUtil.toast("home")

            }
            R.id.nav_gallery -> {
                PkgManagerUtil.create(baseContext).changeIcon(1)
                ToastUtil.toast("gallery")
            }
            R.id.nav_slideshow -> {
                ToastUtil.toast("slideshow ")
            }
            R.id.nav_tools -> {
                ToastUtil.toast("tools")
            }
            R.id.nav_share -> {
                ToastUtil.toast("share")
            }
            R.id.nav_send -> {
                ToastUtil.toast("send")
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetEventMsg(messageWrap: MessageWrap) {
        println("main = ${messageWrap.message}")
    }
}
