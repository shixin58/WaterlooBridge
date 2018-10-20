package com.roy.devil.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bride.baselib.ResUtils;
import com.roy.devil.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shixin on 2017/10/19.
 */
public class PrivacyPolicyDialog extends AppCompatActivity {

    public static void show(Context context){
        Intent intent = new Intent(context, PrivacyPolicyDialog.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_policy);
        setFinishOnTouchOutside(false);

        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        initView();
    }

    private void initView(){

        TextView tipsTv = findViewById(R.id.tips_tv);
        SpannableStringBuilder fullString = new SpannableStringBuilder();
        String webServiceTitle = ResUtils.getString(R.string.web_service_agreement);
        String privacyPolicyTitle = ResUtils.getString(R.string.privacy_policy);

        fullString.append(ResUtils.getString(R.string.web_service_and_privacy_policy_updated));
        int positionOfWebService = fullString.toString().indexOf(webServiceTitle);
        int positionOfPrivacyPolicy = fullString.toString().indexOf(privacyPolicyTitle);
        fullString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ResUtils.getColor(R.color.tx_6));
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                if(widget instanceof TextView){
                    ((TextView)widget).setHighlightColor(Color.TRANSPARENT);
                }
                Toast.makeText(PrivacyPolicyDialog.this, ResUtils.getString(R.string.web_service_agreement),
                        Toast.LENGTH_SHORT).show();
            }
        }, positionOfWebService, positionOfWebService+webServiceTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        fullString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ResUtils.getColor(R.color.tx_6));
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                if(widget instanceof TextView){
                    ((TextView)widget).setHighlightColor(Color.TRANSPARENT);
                }
                Toast.makeText(PrivacyPolicyDialog.this, ResUtils.getString(R.string.privacy_policy),
                        Toast.LENGTH_SHORT).show();
            }
        }, positionOfPrivacyPolicy, positionOfPrivacyPolicy+privacyPolicyTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tipsTv.setText(fullString);
        tipsTv.setMovementMethod(LinkMovementMethod.getInstance());

        TextView agreeTv = findViewById(R.id.agree_tv);
        agreeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
