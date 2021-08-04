package com.example.demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ngb.common.BaseCommonActivity;
import com.ngb.common.ui.CommonButton;

public class DemoActivity extends BaseCommonActivity {

    private CommonButton mCommonButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        mCommonButton = findViewById(R.id.demo_button);
        mCommonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
