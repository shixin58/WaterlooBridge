package com.roy.devil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.roy.devil.service.DaemonService;

/**
 * <p>Created by shixin on 2019/4/14.
 */
public class CustomBootReceiver extends BroadcastReceiver {
    private static final String TAG = CustomBootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "com.roy.devil.action.MUSIC":
                Toast.makeText(context, "com.roy.devil.action.MUSIC", Toast.LENGTH_SHORT).show();
                Log.i(TAG, context+" com.roy.devil.action.MUSIC");
                context.startService(new Intent(context, DaemonService.class));
                break;
            case "com.roy.devil.action.LOCAL":
                Toast.makeText(context.getApplicationContext(), "com.roy.devil.action.LOCAL", Toast.LENGTH_SHORT).show();
                Log.i(TAG, context.getApplicationContext()+" com.roy.devil.action.LOCAL");
                context.getApplicationContext().startService(new Intent(context.getApplicationContext(), DaemonService.class));
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                Toast.makeText(context, "Intent.ACTION_BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Intent.ACTION_BOOT_COMPLETED");
                break;
            case Intent.ACTION_USER_PRESENT:
                Toast.makeText(context, "Intent.ACTION_USER_PRESENT", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Intent.ACTION_USER_PRESENT");
                break;
            case Intent.ACTION_PACKAGE_RESTARTED:
                Toast.makeText(context, "Intent.ACTION_PACKAGE_RESTARTED", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Intent.ACTION_PACKAGE_RESTARTED");
                break;
        }
    }
}
