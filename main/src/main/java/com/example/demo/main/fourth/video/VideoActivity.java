package com.example.demo.main.fourth.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.example.demo.main.R;
import com.example.demo.common.BaseCommonActivity;

public class VideoActivity extends BaseCommonActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setMediaController(new MediaController(this));
        //mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny));
        mVideoView.setVideoURI(Uri.parse("https://activity2.allsaints.top/c03eb9de/current/uploads/20220809_181839_f84e97332d.mp4"));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }
}
