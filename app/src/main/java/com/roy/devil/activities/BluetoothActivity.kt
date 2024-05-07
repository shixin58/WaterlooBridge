package com.roy.devil.activities

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.bride.baselib.LogUtils
import com.bride.ui_lib.BaseActivity
import com.roy.devil.databinding.ActivityBluetoothBinding

class BluetoothActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityBluetoothBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        this.registerReceiver(mReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(mReceiver)
    }

    private val mReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                val preState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0)
                viewBinding.tvState.text = "state = $state; preState = $preState"
                LogUtils.i("BluetoothActivity", "state = $state; preState = $preState")
                if (state == BluetoothAdapter.STATE_ON) {

                } else if (state == BluetoothAdapter.STATE_OFF) {

                }
            }
        }
    }
}