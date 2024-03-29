package com.example.demo.network.netservice;

import com.example.demo.network.bean.PostInfo;
import com.example.demo.network.net.WebFactory;

import io.reactivex.Flowable;

public class CommonRepository {
    private volatile static CommonRepository sInstance;
    private CommonService mCommonService;

    private CommonRepository() {
        mCommonService = WebFactory.getInstance().getService(CommonService.class);
    }

    public static CommonRepository getInstance() {
        if (sInstance == null) {
            synchronized (CommonRepository.class) {
                if (sInstance == null) {
                    sInstance = new CommonRepository();
                }
            }
        }
        return sInstance;
    }

    public Flowable<PostInfo> getPosInfoRx(String type, String posId) {
        return mCommonService.getPosInfoRx(type,posId);
    }
}
