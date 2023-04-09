package com.roy.devil

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import com.bride.baselib.PermissionUtils
import com.bride.ui_lib.BaseActivity
import com.roy.devil.fragment.HomeFragment

class MainActivity : BaseActivity() {
    private var mIsExiting = false
    private val mHandler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        PermissionUtils.requestAllPermissions(this, 1)
    }

    private fun initView() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment, HomeFragment())
            .commitAllowingStateLoss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            if (mIsExiting) {
                finish()
            } else {
                mIsExiting = true
                Toast.makeText(this, "再点一次退出应用", Toast.LENGTH_SHORT).show()
                mHandler.postDelayed({ mIsExiting = false }, 2000L)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}