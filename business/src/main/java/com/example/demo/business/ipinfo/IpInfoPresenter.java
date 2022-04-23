package com.example.demo.business.ipinfo;

import com.example.demo.business.callBack.IpInfoLoadTasksCallback;
import com.example.demo.business.model.IpInfo;
import com.example.demo.business.net.NetTask;

public class IpInfoPresenter implements IpInfoContract.Presenter{

    private NetTask<String, IpInfo> mNetTask;
    private IpInfoContract.InfoView mInfoView;

    public IpInfoPresenter(NetTask<String, IpInfo> netTask, IpInfoContract.InfoView infoView) {
        this.mInfoView = infoView;
        this.mNetTask = netTask;
    }
    @Override
    public void getIpInfo(String ip) {
        mNetTask.execute(ip, new IpInfoLoadTasksCallback(mInfoView));
    }
}
