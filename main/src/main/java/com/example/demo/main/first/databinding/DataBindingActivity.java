package com.example.demo.main.first.databinding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.demo.common.BaseCommonActivity;
import com.example.demo.main.R;
import com.example.demo.main.databinding.ActivityDataBingingBinding;
import com.example.demo.main.first.data.TemplateData;

public class DataBindingActivity extends BaseCommonActivity {

    private ActivityDataBingingBinding mBinding;
    private TemplateData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binging);
        mData = new TemplateData();
        mData.setTime(System.currentTimeMillis());
        mData.setLikeCount(100);
        mData.setUrl("https://file02.16sucai.com/d/file/2014/0827/c0c92bd51bb72e6d12d5b877dce338e8.jpg");
        mData.setUserName("南瓜饼");

        mBinding.setTemplateData(mData);
        mBinding.timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.setTime(System.currentTimeMillis());
            }
        });
    }




}
