package com.roy.devil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import com.roy.devil.activities.MusicActivity;
import com.roy.devil.repository.MusicRepository;

import java.io.IOException;
import java.util.List;

import androidx.core.app.NotificationCompat;

/**
 * 在主进程主线程bindService，在主进程主线程执行Service
 * <p>Created by shixin on 2018/10/20.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
    private int mIndex = 0;
    List<String> mPathList = MusicRepository.getPathList();
    private MediaPlayer mMediaPlayer;
    private boolean loop = false;

    private MyBinder binder = new MyBinder();

    @Override
    public void onPrepared(MediaPlayer mp) {
        String title = mPathList.get(mIndex).substring(mPathList.get(mIndex).lastIndexOf('/')+1);
        Toast.makeText(this, "准备播放:\n"+title, Toast.LENGTH_SHORT).show();
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        setNotification();
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        try {
            mMediaPlayer.setDataSource(mPathList.get(0));
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNotification() {
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = "my_service";
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "my background service", NotificationManager.IMPORTANCE_NONE);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        // 点通知打开MainActivity
        Intent intent = new Intent(this, MusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Music")
                .setContentText("Less is more")
                .setSmallIcon(R.mipmap.ic_launcher)/* 在statusBar显示 */
                .setLargeIcon(largeIcon)
                .setContentIntent(pendingIntent)
                .build();
        // 以可见进程的模式启动
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void playOrPause() {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }else {
            mMediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void stop() {
        mMediaPlayer.stop();
        try {
            mMediaPlayer.prepare();
            mMediaPlayer.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        if(mIndex+1<mPathList.size()) {
            mIndex++;
        }else {
            mIndex = 0;
        }
        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mPathList.get(mIndex));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void previous() {
        if(mIndex-1>=0) {
            mIndex--;
        }else {
            mIndex = mPathList.size()-1;
        }
        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mPathList.get(mIndex));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(int index) {
        mIndex = index;
        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mPathList.get(mIndex));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 取值0-1000
    public void seek(int permillage) {
        int duration = mMediaPlayer.getDuration();
        mMediaPlayer.seekTo(duration*permillage/1000);
    }

    public int getPermillage() {
        return 1000*mMediaPlayer.getCurrentPosition()/mMediaPlayer.getDuration();
    }

    public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        mMediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.release();
        super.onDestroy();
    }
}
