package com.example.demo.business.ipinfo;

import androidx.fragment.app.Fragment;

import com.example.demo.business.model.IpInfo;

public class IpInfoFragment extends Fragment implements IpInfoContract.InfoView {

    private IpInfoContract.Presenter mPresenter;

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
