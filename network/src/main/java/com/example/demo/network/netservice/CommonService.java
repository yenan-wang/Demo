package com.example.demo.network.netservice;

import com.example.demo.network.bean.PostInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommonService {

    @GET("/query")
    Flowable<PostInfo> getPosInfoRx(@Query("type") String type, @Query("postid") String posId);

}
