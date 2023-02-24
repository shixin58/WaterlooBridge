package com.roy.devil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bride.ui_lib.BaseActivity;
import com.roy.devil.R;
import com.roy.devil.adapter.FollowAnimationAdapter;
import com.roy.devil.databinding.ActivityFollowAnimationBinding;
import com.roy.devil.specific.FollowAnimationUtils;

/**
 * 关注动画
 * <p>Created by shixin on 2018/4/1.
 */
public class FollowAnimationActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FollowAnimationActivity";

    private ActivityFollowAnimationBinding mBinding;

    private final Handler handler = new Handler();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FollowAnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFollowAnimationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        FollowAnimationAdapter followAnimationAdapter = new FollowAnimationAdapter(this);
        mBinding.listView.setAdapter(followAnimationAdapter);
        mBinding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        FollowAnimationUtils.collapseInventory(mBinding.rootInventory, 0);
                        break;
                    case SCROLL_STATE_FLING:
                    case SCROLL_STATE_TOUCH_SCROLL:
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mBinding.rootInventory.setOnClickListener(this);
        FollowAnimationUtils.collapseInventory(mBinding.rootInventory, 1200);
        setAnimation();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.flFollow) {
            // 圆角矩形收缩成圆0.1s -> 小蓝球掉入清单0.2s
            // 清单入口展开 -> 0.3s
            FollowAnimationUtils.shrinkRoundRectangle(view);
            FollowAnimationUtils.throwBall(view, mBinding.rootInventory, mBinding.ivPoint);
            FollowAnimationUtils.expandInventory(mBinding.rootInventory);
        } else if (view.getId() == R.id.root_inventory) {
            if (mBinding.buttonInventory.getVisibility() == View.GONE) {
                FollowAnimationUtils.expandInventory(mBinding.rootInventory);
            } else {
                HandlerThread handlerThread = new HandlerThread("Thread - Toast");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            Log.i(TAG, "handleMessage " + Thread.currentThread().getName());
                            Toast.makeText(FollowAnimationActivity.this.getApplicationContext(), "打开购物车", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                handler.sendEmptyMessage(1);
            }
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(() -> mBinding.swipeRefreshLayout.setRefreshing(false), 2000L);
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.5f);
        mBinding.listView.setLayoutAnimation(layoutAnimationController);
    }
}
