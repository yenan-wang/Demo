package com.example.demo.main.six.crash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.common.BaseCommonActivity;
import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.DialogUtil;
import com.ngb.wyn.common.utils.LogUtil;
import com.ngb.wyn.common.utils.SpUtil;
import com.ngb.wyn.common.utils.ToastUtil;

public class CrashActivity extends BaseCommonActivity {

    private static final int SEND_MESSAGE = 0;
    private static final String LAST_TIME = "last_time";

    private CommonButton mCrashButton;
    private CommonButton mStartButton;
    private TextView mTimeTextView;
    private Handler mHandler;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_layout);
        mTimeTextView = findViewById(R.id.time);
        mTimeTextView.setText(String.valueOf(count));
        mStartButton = findViewById(R.id.count_time);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessageDelayed(SEND_MESSAGE, 1000);
            }
        });
        mCrashButton = findViewById(R.id.execute_crash);
        mCrashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeCrash();
            }
        });
        mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SEND_MESSAGE:
                        count++;
                        mTimeTextView.setText(String.valueOf(count));
                        mHandler.sendEmptyMessageDelayed(SEND_MESSAGE, 1000);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("保存崩溃时数据。");
        saveData(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        LogUtil.d("恢复崩溃时数据。");
        super.onRestoreInstanceState(savedInstanceState);
        recoverData(savedInstanceState);
    }

    private void executeCrash() {
        throw new IllegalArgumentException(getString(R.string.execute_crash_code));
    }

    private void saveData(Bundle outState) {
        outState.putInt(LAST_TIME, count);
        SpUtil.putInt(LAST_TIME, count);
    }

    private void recoverData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int lastTime = savedInstanceState.getInt(LAST_TIME);
            if (lastTime != 0) {
                AlertDialog alertDialog = DialogUtil.createDialog(
                        this,
                        getResources().getQuantityString(R.plurals.stay_for_last_time, lastTime, lastTime),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                count = lastTime;
                                mTimeTextView.setText(String.valueOf(count));
                                ToastUtil.toastLong(getString(R.string.has_recovered));
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtil.toastLong(getString(R.string.recalculate_stay_time));
                            }
                        });
                alertDialog.show();
            }
        }
    }
}
