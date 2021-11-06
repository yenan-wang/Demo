package com.example.demo.main.fifth.customview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.common.ui.CommonButton;
import com.example.demo.common.ui.progressbar.HorizontalProgressBar;
import com.example.demo.common.utils.LogUtil;
import com.example.demo.common.utils.ToastUtil;
import com.example.demo.main.R;

import java.util.Timer;
import java.util.TimerTask;

public class CustomViewProgressFragment extends Fragment {
    private HorizontalProgressBar mHorizontalProgressBar;
    private CommonButton mCommonButton;
    private Timer mTimer = new Timer();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_fragment_progress_layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mHorizontalProgressBar = view.findViewById(R.id.progress_horizontal);
        mHorizontalProgressBar.setOnProcessStatusListener(new HorizontalProgressBar.OnProcessStatusListener() {
            @Override
            public void pauseStatus(boolean isPause, float process) {
                LogUtil.d("pause: " + isPause + ", process:" + process);
                if (isPause) {
                    cancel(false);
                    mCommonButton.setText("开始");
                } else {
                    mCommonButton.setText("暂停");
                    initTimer();
                }
            }

            @Override
            public void complete() {
                LogUtil.d("complete!");
                cancel(true);
                ToastUtil.toastLong("下载完成！");
                mCommonButton.setText("重新开始");
            }
        });
        mCommonButton = view.findViewById(R.id.button_start);
        mCommonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float process = mHorizontalProgressBar.getProgress();
                LogUtil.d("progress1:" + process);
                if (process == HorizontalProgressBar.PROGRESS_COMPILE) {
                    mHorizontalProgressBar.setProgress(0);
                    initTimer();
                    mHorizontalProgressBar.setPause(false);
                } else {
                    mHorizontalProgressBar.setPause(!mHorizontalProgressBar.isPause());
                }
            }
        });
    }

    private void initTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mHorizontalProgressBar.getProgress() < HorizontalProgressBar.PROGRESS_COMPILE) {
                    LogUtil.d("progress:" + mHorizontalProgressBar.getProgress());
                    mHorizontalProgressBar.setProgress(mHorizontalProgressBar.getProgress() + 0.0527F);
                }
            }
        }, 0, 100);
    }

    private void cancel(boolean isComplete) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mHorizontalProgressBar.setPause(!isComplete);
        }
    }
}
