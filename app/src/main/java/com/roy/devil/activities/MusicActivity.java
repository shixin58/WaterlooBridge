package com.roy.devil.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ClipDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bride.ui_lib.BaseActivity;
import com.bride.ui_lib.BaseRecyclerAdapter;
import com.roy.devil.databinding.ActivityMusicBinding;
import com.roy.devil.service.MusicService;
import com.roy.devil.R;
import com.roy.devil.adapter.MusicAdapter;
import com.roy.devil.repository.MusicRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/10/20.
 */
public class MusicActivity extends BaseActivity {
    private static final String TAG = MusicActivity.class.getSimpleName();
    public static final String KEY_FROM = "from";

    private ActivityMusicBinding mBinding;

    private boolean mFlagPlay = false;

    private MusicService mMusicService;
    private final Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initData();
        initView();
    }

    private void initData() {
        String from = getIntent().getStringExtra(KEY_FROM);
        if (!TextUtils.isEmpty(from) || (getIntent().getData() != null
                && !TextUtils.isEmpty(from=getIntent().getData().getQueryParameter(KEY_FROM)))) {
            Toast.makeText(this.getApplicationContext(), "从"+from+"开启"+TAG, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "从"+from+"开启"+TAG);
        }
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mBinding.recyclerView.setLayoutManager(layoutManager);
        MusicAdapter adapter = new MusicAdapter();
        mBinding.recyclerView.setAdapter(adapter);

        List<String> list = MusicRepository.getPathList();
        if (!list.isEmpty()) {
            adapter.setList(list);
            initService();
        } else {
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                mMusicService.play(i);
                mFlagPlay = true;
                mBinding.tvPlay.setText(R.string.play);
            }
        });
        mBinding.progressBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mMusicService.seek(750);
                return true;
            }
        });
        mBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mMusicService.seek(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ImageView imageView = findViewById(R.id.iv_clip);
        ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
        clipDrawable.setLevel(40 * 100);
    }

    private void initService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = ((MusicService.MyBinder)service).getService();
            mMusicService.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    int progress = mMusicService.getPermillage();
                    mBinding.progressBar.setProgress(progress);
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mBinding.viewPointer.getLayoutParams();
                    lp.leftMargin = mBinding.progressBar.getWidth() * progress / mBinding.progressBar.getMax();
                    mBinding.viewPointer.setLayoutParams(lp);
                    mBinding.seekBar.setProgress(mMusicService.getPermillage());
                }
            });
            mMusicService.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(mMusicService.isLoop()) {
                        mMusicService.next();
                        mFlagPlay = true;
                        mBinding.tvPlay.setText(R.string.play);
                    }else {
                        mFlagPlay = false;
                        mBinding.tvPlay.setText(R.string.pause);
                    }
                }
            });
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!mMusicService.isPlaying()) return;
                    // 切换至UI线程
                    mBinding.progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            int progress = mMusicService.getPermillage();
                            mBinding.progressBar.setProgress(progress);
                            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mBinding.viewPointer.getLayoutParams();
                            lp.leftMargin = mBinding.progressBar.getWidth() * progress / mBinding.progressBar.getMax();
                            mBinding.viewPointer.setLayoutParams(lp);
                            mBinding.seekBar.setProgress(mMusicService.getPermillage());
                        }
                    });
                }
            }, 100, 100);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    };

    public void onPlayClick(View view) {
        Log.i(TAG, "onPlayClick");
        mMusicService.playOrPause();
        mFlagPlay = !mFlagPlay;
        mBinding.tvPlay.setText(mFlagPlay ?R.string.play:R.string.pause);
    }

    public void onStopClick(View view) {
        Log.i(TAG, "onStopClick");
        mMusicService.stop();
        mFlagPlay = false;
        mBinding.tvPlay.setText(R.string.pause);
    }

    public void onPreviousClick(View view) {
        Log.i(TAG, "onPreviousClick");
        mMusicService.previous();
        mFlagPlay = true;
        mBinding.tvPlay.setText(R.string.play);
    }

    public void onNextClick(View view) {
        Log.i(TAG, "onNextClick");
        mMusicService.next();
        mFlagPlay = true;
        mBinding.tvPlay.setText(R.string.play);
    }

    public void onLoopClick(View view) {
        Log.i(TAG, "onLoopClick");
        boolean loop = mMusicService.isLoop();
        mMusicService.setLoop(loop=!loop);
        TextView textView = findViewById(R.id.tv_loop);
        textView.setText(loop?R.string.loop:R.string.single_song);
    }

    public void onEmptyClick(View view) {
        Log.i(TAG, "onEmptyClick");
    }

    @Override
    protected void onDestroy() {
        if(mMusicService != null) {
            unbindService(serviceConnection);
        }
        mTimer.cancel();
        super.onDestroy();
    }
}
