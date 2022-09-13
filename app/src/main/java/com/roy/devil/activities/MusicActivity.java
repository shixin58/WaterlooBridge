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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.baselib.widget.BaseRecyclerAdapter;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by shixin on 2018/10/20.
 */
public class MusicActivity extends BaseActivity {
    private static final String TAG = MusicActivity.class.getSimpleName();
    public static final String KEY_FROM = "from";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.view_pointer)
    View mViewPointer;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_play)
    TextView mTvPlay;

    private boolean mFlagPlay = false;

    private MusicService mMusicService;
    private final Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // singleTop
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
        mRecyclerView.setLayoutManager(layoutManager);
        MusicAdapter adapter = new MusicAdapter();
        mRecyclerView.setAdapter(adapter);

        List<String> list = MusicRepository.getPathList();
        if(list!=null && !list.isEmpty()) {
            adapter.setList(list);
            initService();
        }else {
            mTvEmpty.setVisibility(View.VISIBLE);
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                mMusicService.play(i);
                mFlagPlay = true;
                mTvPlay.setText(R.string.play);
            }
        });
        mProgressBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mMusicService.seek(750);
                return true;
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    mProgressBar.setProgress(progress);
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mViewPointer.getLayoutParams();
                    lp.leftMargin = mProgressBar.getWidth() * progress / mProgressBar.getMax();
                    mViewPointer.setLayoutParams(lp);
                    mSeekBar.setProgress(mMusicService.getPermillage());
                }
            });
            mMusicService.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(mMusicService.isLoop()) {
                        mMusicService.next();
                        mFlagPlay = true;
                        mTvPlay.setText(R.string.play);
                    }else {
                        mFlagPlay = false;
                        mTvPlay.setText(R.string.pause);
                    }
                }
            });
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!mMusicService.isPlaying()) return;
                    // 切换至UI线程
                    mProgressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            int progress = mMusicService.getPermillage();
                            mProgressBar.setProgress(progress);
                            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mViewPointer.getLayoutParams();
                            lp.leftMargin = mProgressBar.getWidth() * progress / mProgressBar.getMax();
                            mViewPointer.setLayoutParams(lp);
                            mSeekBar.setProgress(mMusicService.getPermillage());
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

    @OnClick(R.id.tv_play) void onPlayClick(View view) {
        Log.i(TAG, "onPlayClick");
        mMusicService.playOrPause();
        mFlagPlay = !mFlagPlay;
        mTvPlay.setText(mFlagPlay ?R.string.play:R.string.pause);
    }

    @OnClick(R.id.tv_stop) void onStopClick(View view) {
        Log.i(TAG, "onStopClick");
        mMusicService.stop();
        mFlagPlay = false;
        mTvPlay.setText(R.string.pause);
    }

    @OnClick(R.id.tv_previous) void onPreviousClick(View view) {
        Log.i(TAG, "onPreviousClick");
        mMusicService.previous();
        mFlagPlay = true;
        mTvPlay.setText(R.string.play);
    }

    @OnClick(R.id.tv_next) void onNextClick(View view) {
        Log.i(TAG, "onNextClick");
        mMusicService.next();
        mFlagPlay = true;
        mTvPlay.setText(R.string.play);
    }

    @OnClick(R.id.tv_loop) void onLoopClick(View view) {
        Log.i(TAG, "onLoopClick");
        boolean loop = mMusicService.isLoop();
        mMusicService.setLoop(loop=!loop);
        TextView textView = findViewById(R.id.tv_loop);
        textView.setText(loop?R.string.loop:R.string.single_song);
    }

    @OnClick(R.id.tv_empty) void onEmptyClick(View view) {
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
