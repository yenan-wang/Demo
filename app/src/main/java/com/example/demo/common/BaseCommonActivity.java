package com.example.demo.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseCommonActivity extends AppCompatActivity {
    private List<BaseCommonActivity> mActivityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }
}
