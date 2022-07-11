package com.roy.devil.activities;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.roy.devil.R;
import com.roy.devil.VictorApplication;
import com.roy.devil.adapter.RankingListPagerAdapter;
import com.roy.devil.specific.WidgetUtils;

import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class ViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.horizontal_scrollview)
    HorizontalScrollView mHorizontalScrollView;
    @BindView(R.id.horizontal_radio_group)
    RadioGroup mRadioGroup;
    @BindViews({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07})
    List<RadioButton> mRadioButtons;
    @BindView(R.id.view_pager)
    VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        RankingListPagerAdapter pagerAdapter = new RankingListPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    @OnClick({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07}) void onTabClick(View view) {
        WidgetUtils.controlTabsPosition(mHorizontalScrollView, mRadioButtons, mRadioButtons.indexOf(view));
        mViewPager.setCurrentItem(mRadioButtons.indexOf(view), true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mRadioGroup.check(mRadioButtons.get(position).getId());
        WidgetUtils.controlTabsPosition(mHorizontalScrollView, mRadioButtons, position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private GestureDetector mGestureDetector = new GestureDetector(VictorApplication.getInstance(),
            new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(ViewPagerActivity.this.getApplicationContext(), "fling", Toast.LENGTH_SHORT).show();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(ViewPagerActivity.this.getApplicationContext(), "single tap", Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(ViewPagerActivity.this.getApplicationContext(), "double tap", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }
    });
}
