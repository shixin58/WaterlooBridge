package com.roy.devil.activities;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bride.baselib.BaseActivity;
import com.bride.baselib.ResUtils;
import com.roy.devil.R;
import com.roy.devil.specific.DrawUtils;

/**
 * 绘制小icon
 * <p>Created by shixin on 2018/4/1.
 */
public class DrawSmallIconActivity extends BaseActivity {

    private ImageView mIvAnimStatic;
    private AnimationDrawable mAnimDrawableStatic;

    private ImageView mIvAnim;
    private AnimationDrawable mAnimationDrawable;

    private ImageView mIvState;
    private Switch mSwitch;
    private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
    private static final int[] STATE_UNCHECKED = new int[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_small_icon);
        initView();
    }

    private void initView() {
        View viewFlag = findViewById(R.id.view_flag);
        DrawUtils.drawTag(this, viewFlag, "热卖");

        TextView tvTag = findViewById(R.id.tv_tag);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(ResUtils.getString(R.string.drink));
        DrawUtils.assembleText(spannableStringBuilder, "超值", R.color.black, R.color.red);
        tvTag.setText(spannableStringBuilder);

        mIvAnimStatic = findViewById(R.id.iv_animation_static);
        mAnimDrawableStatic = (AnimationDrawable) mIvAnimStatic.getBackground();
        mIvAnim = findViewById(R.id.iv_animation);
        mIvState = findViewById(R.id.iv_state);
        mSwitch = findViewById(R.id.tv_check);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_anim_static_start:
                mAnimDrawableStatic.start();
                break;
            case R.id.tv_anim_static_stop:
                mAnimDrawableStatic.stop();
                break;
            case R.id.tv_anim_start:
                if (mAnimationDrawable == null) {
                    mAnimationDrawable = new AnimationDrawable();
                    for (int i=1; i<=4; i++) {
                        int id = getResources().getIdentifier("animation_list_"+i, "drawable", getPackageName());
                        Drawable drawable = getResources().getDrawable(id);
                        mAnimationDrawable.addFrame(drawable, 150);
                    }
                    mAnimationDrawable.setOneShot(false);
                    mIvAnim.setImageDrawable(mAnimationDrawable);
                }
                mAnimationDrawable.start();
                break;
            case R.id.tv_anim_stop:
                if (mAnimationDrawable!=null && mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                break;
            case R.id.tv_check:
                // CompoundButton#setChecked, View#setSelected
                if (mSwitch.isChecked()) {
                    mIvState.setSelected(true);
                    mIvState.setImageState(STATE_CHECKED, true);
                } else {
                    mIvState.setSelected(false);
                    mIvState.setImageState(STATE_UNCHECKED, true);
                }
                break;
        }
    }
}
