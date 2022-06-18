package com.example.demo.main.third;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.ui.CommonButton;
import com.example.demo.common.ui.progressbar.HorizontalProgressBar;
import com.example.demo.common.utils.LogUtil;
import com.example.demo.common.utils.ToastUtil;
import com.example.demo.main.R;
import com.example.demo.network.CommonRequestUtil;

public class MusicPlayActivity extends BaseCommonActivity {

    //private static final String URL = "https://m801.music.126.net/20220506224335/19a2112c06d0e63b7ab2310d39b8d444/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/14096440447/f0d0/3fb1/34f3/07609c3e62164085dad86c7c6cdf7af3.mp3";
    //private static final String URL = "http://m801.music.126.net/20220506233855/e31a3cacf63fccd7d70bd2d70d6b58de/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/14096440447/f0d0/3fb1/34f3/07609c3e62164085dad86c7c6cdf7af3.mp3";
    //private static final String URL = "http://m701.music.126.net/20220506232040/8f473da7d9f77862d806ce9cbaa41008/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/14096440447/f0d0/3fb1/34f3/07609c3e62164085dad86c7c6cdf7af3.mp3";
    private static final String URL = "http://music.163.com/song/media/outer/url?id=1808492017.mp3";
    private static final String TAG = "MusicPlayActivity";

    private MediaPlayer mPlayer;
    private CommonButton mPlayOrPauseButton;
    private CommonButton mStopButton;
    private HorizontalProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_music_play);
        mPlayOrPauseButton = findViewById(R.id.play_or_pause);
        mPlayOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    setCurrentStatus(true);
                } else {
                    mPlayer.start();
                    setCurrentStatus(false);
                }
            }
        });
        mStopButton = findViewById(R.id.stop);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayOrPauseButton.setText(R.string.play);
            }
        });
        mProgressBar = findViewById(R.id.process);
        mProgressBar.setOnProcessStatusListener(new HorizontalProgressBar.OnProcessStatusListener() {
            @Override
            public void complete() {
                ToastUtil.toastShort(getString(R.string.buffer_complete));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

    private void initData() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        CommonRequestUtil.request("http://music.163.com/", new CommonRequestUtil.RequestResult() {
            @Override
            public void success(Object o) {
                LogUtil.d(o);
                try {
                    mPlayer.setDataSource(o.toString());
                    mPlayer.prepareAsync();
                } catch (Exception e) {
                    LogUtil.e(TAG, "initData, error:" + e.getMessage());
                }
            }

            @Override
            public void fail(Throwable throwable) {
                LogUtil.e(throwable.getMessage());
            }
        });
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                setCurrentStatus(true);
            }
        });
        mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                mProgressBar.setProgress(percent);
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setCurrentStatus(false);
            }
        });
    }

    private void setCurrentStatus(boolean isPlaying) {
        if (isPlaying) {
            mPlayOrPauseButton.setText(R.string.pause);
        } else {
            mPlayOrPauseButton.setText(R.string.play);
        }
    }
}
