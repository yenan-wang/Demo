package com.example.demo.business.ipinfo;

import com.example.demo.business.BaseView;
import com.example.demo.business.model.IpInfo;

public interface IpInfoContract {

    interface Presenter {
        void getIpInfo(String ip);
    }

    interface InfoView extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);
        void showLoading();
        void hideLoading();
        void showError();
        boolean isActive();
    }
}
