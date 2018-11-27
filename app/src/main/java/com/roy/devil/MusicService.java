package com.roy.devil;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.roy.devil.repository.MusicRepository;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
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

    public void setOnSeekCompleteListener(@NonNull MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
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
