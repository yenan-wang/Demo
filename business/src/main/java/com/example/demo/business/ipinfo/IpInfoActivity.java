package com.example.demo.business.ipinfo;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.demo.business.net.IpInfoTask;
import com.example.demo.common.BaseActivity;

public class IpInfoActivity extends BaseActivity {

    private IpInfoPresenter mIpInfoPresenter;
    private IpInfoFragment mIpInfoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIpInfoFragment = new IpInfoFragment();
        mIpInfoPresenter = new IpInfoPresenter(IpInfoTask.getInstance(), mIpInfoFragment);
        mIpInfoFragment.setPresenter(mIpInfoPresenter);

    }
}
