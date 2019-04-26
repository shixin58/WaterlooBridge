package com.roy.devil.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.bride.baselib.widget.BaseRecyclerAdapter;
import com.roy.devil.R;
import com.roy.devil.specific.WidgetUtils;
import com.roy.devil.widget.BottomChooseDialog;
import com.roy.devil.widget.PrivacyPolicyDialog;

/**
 * Toast打开可用Application Context, 可在任意线程打开。
 * LayoutInflater#from(Context)#inflate可用Application Context。
 * PopupWindow showAtLocation由parent提供token(View#getWindowToken())，showAsDropDown由anchor提供token(View#getApplicationWindowToken()).
 * 用Application Context打开Dialog，报错：WindowManager$BadTokenException: Unable to add window -- token null for displayid = 0 is not valid; is your activity running?
 * Dialog若不指定上下文，使用Context#getTheme().
 * <p>Created by shixin on 2018/4/7.
 */
public class PopupLayerActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
