package com.victor.utils.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.victor.utils.PreferenceUtils;
import com.victor.utils.R;
import com.victor.utils.specific.WidgetUtils;

/**
 * <p>Created by shixin on 2018/4/7.
 */
public class PopupLayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_layer);
        initView();
    }

    private void initView() {
        View titleBar = findViewById(R.id.title_bar);
        View inventoryEntry = findViewById(R.id.inventory_entry);
        View clearPreferences = findViewById(R.id.tv_clear_preferences);

        WidgetUtils.initSerialFollowTip(this, titleBar);
        WidgetUtils.initInventoryTip(this, inventoryEntry);
        clearPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.clear();
                PreferenceUtils.commit();
            }
        });
    }
}
