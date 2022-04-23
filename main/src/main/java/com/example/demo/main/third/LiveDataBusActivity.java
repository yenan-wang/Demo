package com.example.demo.main.third;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.utils.LiveDataBus;
import com.example.demo.common.utils.ToastUtil;
import com.example.demo.main.R;

public class LiveDataBusActivity extends BaseCommonActivity {

    public static final String LIVE_DATA_BUS_KEY_HELLO = "hello";

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_bus);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.get().with(LIVE_DATA_BUS_KEY_HELLO).postValue("你好，我是demo！");
            }
        });
        mTextView = findViewById(R.id.text);
        initLiveDataBus();
    }

    private void initLiveDataBus() {
        LiveDataBus.get().with(LIVE_DATA_BUS_KEY_HELLO, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtil.toastShort(s);
                mTextView.setText(s);
            }
        });
    }


}
