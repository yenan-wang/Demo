package com.ngb.network;

import com.ngb.network.bean.PostInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommonService {

    @GET("/query")
    Flowable<PostInfo> getPosInfoRx(@Query("type") String type, @Query("postid") String posId);

}
