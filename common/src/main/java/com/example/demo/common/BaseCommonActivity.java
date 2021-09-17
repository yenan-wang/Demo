package com.example.demo.common;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class BaseCommonActivity extends AppCompatActivity {
    private List<BaseCommonActivity> mActivityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent, getTheme()));
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }

    public int getActivityCount() {
        return mActivityList.size();
    }
}
