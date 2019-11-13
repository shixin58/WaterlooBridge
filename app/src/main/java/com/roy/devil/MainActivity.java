package com.roy.devil;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.baselib.PermissionUtils;
import com.roy.devil.fragment.HomeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class MainActivity extends BaseActivity {
    private boolean mIsExiting = false;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        PermissionUtils.requestAllPermissions(this, 1);
    }

    private void initView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = HomeFragment.newInstance();
        fragmentTransaction.replace(R.id.container_fragment, homeFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mIsExiting) {
                finish();
            } else {
                mIsExiting = true;
                Toast.makeText(this, "再点一次退出应用", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExiting = false;
                    }
                }, 2000L);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
