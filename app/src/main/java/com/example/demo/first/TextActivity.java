package com.example.demo.first;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.demo.R;
import com.ngb.common.BaseCommonActivity;

public class TextActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_layout);
    }
}
