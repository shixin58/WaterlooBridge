package com.roy.devil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bride.baselib.BaseActivity;
import com.roy.devil.R;
import com.roy.devil.adapter.FollowAnimationAdapter;
import com.roy.devil.specific.FollowAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关注动画
 * <p>Created by shixin on 2018/4/1.
 */
public class FollowAnimationActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.root_inventory)
    View rootInventory;
    @BindView(R.id.button_inventory)
    TextView buttonInventory;
    @BindView(R.id.iv_point)
    ImageView ivPoint;

    private Handler handler = new Handler();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FollowAnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_animation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        FollowAnimationAdapter followAnimationAdapter = new FollowAnimationAdapter(this);
        listView.setAdapter(followAnimationAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        FollowAnimationUtils.collapseInventory(rootInventory, 0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        rootInventory.setOnClickListener(this);
        FollowAnimationUtils.collapseInventory(rootInventory, 1200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flFollow:
                // 圆角矩形收缩成圆0.1s -> 小蓝球掉入清单0.2s
                // 清单入口展开 -> 0.3s
                FollowAnimationUtils.shrinkRoundRectangle(view);
                FollowAnimationUtils.throwBall(view, rootInventory, ivPoint);
                FollowAnimationUtils.expandInventory(rootInventory);
                break;
            case R.id.root_inventory:
                if(buttonInventory.getVisibility()==View.GONE) {
                    FollowAnimationUtils.expandInventory(rootInventory);
                }else {
                    HandlerThread handlerThread = new HandlerThread("Thread - Toast");
                    handlerThread.start();
                    Handler handler = new Handler(handlerThread.getLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 1:
                                    Log.i(TAG, "handleMessage "+Thread.currentThread().getName());
                                    Toast.makeText(FollowAnimationActivity.this.getApplicationContext(), "打开购物车", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    };
                    handler.sendEmptyMessage(1);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000L);
    }
}
