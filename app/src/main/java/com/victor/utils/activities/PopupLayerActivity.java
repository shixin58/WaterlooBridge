package com.victor.utils.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.victor.utils.PreferenceUtils;
import com.victor.utils.R;
import com.victor.utils.ResUtils;
import com.victor.utils.specific.WidgetUtils;
import com.victor.utils.widget.BaseRecyclerAdapter;
import com.victor.utils.widget.BottomChooseDialog;
import com.victor.utils.widget.PrivacyPolicyDialog;

/**
 * <p>Created by shixin on 2018/4/7.
 */
public class PopupLayerActivity extends AppCompatActivity implements View.OnClickListener{

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
        View tvShowDialog = findViewById(R.id.tv_show_dialog);
        View tvDialogBottom = findViewById(R.id.tv_dialog_bottom);

        WidgetUtils.initSerialFollowTip(this, titleBar);
        WidgetUtils.initInventoryTip(this, inventoryEntry);
        clearPreferences.setOnClickListener(this);
        tvShowDialog.setOnClickListener(this);
        tvDialogBottom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_preferences:
                PreferenceUtils.clear();
                PreferenceUtils.commit();
                break;
            case R.id.tv_show_dialog:
                PrivacyPolicyDialog.show(this);
                break;
            case R.id.tv_dialog_bottom:
                final BottomChooseDialog dialog = new BottomChooseDialog(this);
                final String[] strings = ResUtils.getStringArray(R.array.loan_down_payment_ratio);
                dialog.setList(strings);
                dialog.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(PopupLayerActivity.this, strings[position], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }
}
