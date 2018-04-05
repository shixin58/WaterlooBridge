package com.victor.utils.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.victor.utils.R;
import com.victor.utils.ResUtils;
import com.victor.utils.specific.DrawUtils;

/**
 * 绘制小icon
 * <p>Created by shixin on 2018/4/1.
 */
public class DrawSmallIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    }
}
