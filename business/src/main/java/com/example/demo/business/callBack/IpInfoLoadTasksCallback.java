package com.example.demo.business.callBack;

import com.example.demo.business.ipinfo.IpInfoContract;
import com.example.demo.business.model.IpInfo;

public class IpInfoLoadTasksCallback implements LoadTasksCallBack<IpInfo> {

    private IpInfoContract.InfoView mInfoView;

    public IpInfoLoadTasksCallback(IpInfoContract.InfoView infoView) {
        this.mInfoView = infoView;
    }

    @Override
    public void onSuccess(IpInfo t) {
        if (mInfoView.isActive()) {
            mInfoView.setIpInfo(t);
        }
    }

    @Override
    public void onStart() {
        if (mInfoView.isActive()) {
            mInfoView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (mInfoView.isActive()) {
            mInfoView.showError();
        }
    }

    @Override
    public void onFinish() {
        if (mInfoView.isActive()) {
            mInfoView.hideLoading();
        }
    }
}
