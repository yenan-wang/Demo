package com.example.demo.main.fifth.customview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.ui.progressbar.HorizontalProgressBar;
import com.ngb.wyn.common.utils.LogUtil;
import com.ngb.wyn.common.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class CustomViewProgressFragment extends Fragment {
    public static final float DEFAULT_PROCESS = 5.27F;
    public static final int DEFAULT_DURATION = 200;
    private HorizontalProgressBar mHorizontalProgressBar;
    private CommonButton mCommonButton;
    private float mProcess;
    private EditText mEditProcess;
    private EditText mEditDuration;
    private boolean mIsPause = true;
    private Timer mTimer;

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
        mEditProcess = view.findViewById(R.id.update_process);
        mEditDuration = view.findViewById(R.id.update_duration);
        mHorizontalProgressBar = view.findViewById(R.id.progress_horizontal);
        mHorizontalProgressBar.setOnProcessStatusListener(new HorizontalProgressBar.OnProcessStatusListener() {
            @Override
            public void complete() {
                LogUtil.d("complete!");
                cancel();
                updatePauseStatus();
                ToastUtil.toastLong("下载完成！");
            }
        });
        mHorizontalProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePauseStatus();
            }
        });
        mCommonButton = view.findViewById(R.id.button_start);
        mCommonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float process = mHorizontalProgressBar.getProgress();
                LogUtil.d("progress1:" + process);
                updatePauseStatus();
            }
        });
    }

    private void updatePauseStatus() {
        if (!mIsPause) {
            if (mHorizontalProgressBar.getProgress() == HorizontalProgressBar.PROGRESS_COMPILE) {
                mCommonButton.setText("重新开始");
            } else {
                mCommonButton.setText("继续");
            }
            cancel();
        } else {
            mCommonButton.setText("暂停");
            startTimer();
        }
        mIsPause = !mIsPause;
    }

    private void startTimer() {
        LogUtil.e("startTimer");
        if (mTimer == null) {
            mTimer = new Timer();
        }

        int duration = DEFAULT_DURATION;
        float process = DEFAULT_PROCESS;
        String durationText = mEditDuration.getText().toString();
        String processText = mEditProcess.getText().toString();
        if (!TextUtils.isEmpty(durationText)) {
            duration = Integer.parseInt(durationText);
        }
        if (!TextUtils.isEmpty(processText)) {
            process = Float.parseFloat(processText);
        }

        float finalProcess = process / 100F;
        if (mHorizontalProgressBar.getProgress() == HorizontalProgressBar.PROGRESS_COMPILE) {
            //这里会有一个问题，用户输入进度为0.01，间隔为1这种极端情况下，进度条满了之后，无法停止，会继续滚动进度；
            //不过这里是使用timer的演示demo，正常使用时不会用到timer去更新进度，所以不有这种问题
            mHorizontalProgressBar.setProgress(0);
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mHorizontalProgressBar.getProgress() < HorizontalProgressBar.PROGRESS_COMPILE) {
                    LogUtil.d("progress:" + mHorizontalProgressBar.getProgress());
                    mHorizontalProgressBar.setProgress(mHorizontalProgressBar.getProgress() + finalProcess);
                }
            }
        }, 0, duration);
    }

    private void cancel() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
    }
}
