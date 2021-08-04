package com.ngb.network;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ngb.common.BaseCommonActivity;

public class WebViewActivity extends BaseCommonActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mTextView = findViewById(R.id.text_view);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean isRunAlone = NetWorkApplication.isIsRunAlone();
            }
        });
    }
}
