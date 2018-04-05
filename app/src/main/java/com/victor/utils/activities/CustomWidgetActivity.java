package com.victor.utils.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;

import com.victor.utils.R;
import com.victor.utils.specific.WidgetUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by shixin on 2018/4/5.
 */
public class CustomWidgetActivity extends AppCompatActivity {

    @BindView(R.id.horizontal_scrollview)
    HorizontalScrollView horizontalScrollView;
    @BindViews({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07})
    List<RadioButton> radioButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn00, R.id.btn01, R.id.btn02, R.id.btn03, R.id.btn04, R.id.btn05, R.id.btn06, R.id.btn07}) void onTabClick(View view) {
        WidgetUtils.controlTabsPosition(horizontalScrollView, radioButtons, radioButtons.indexOf(view));
    }
}
