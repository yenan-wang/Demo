package com.example.demo.network;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.demo.network.bean.PostInfo;
import com.example.demo.network.netservice.RetrofitService;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebViewActivity extends BaseCommonActivity {
    private TextView mTextView;
    //观察者
    private Subscriber<String> mSubscriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };

    //被观察者
    private Observable<String> mObservable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("南瓜饼");
            emitter.onNext("小王同志");
            emitter.onComplete();
        }
    });

    //被观察者just形式
    Observable<String> mObservableJust = Observable.just("just-南瓜饼", "just-小王同志");

    //被观察者from形式
    String[] fromStrings = {"just-南瓜饼", "just-小王同志"};
    Observable<String> mObservableFrom = Observable.fromArray(fromStrings);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mTextView = findViewById(R.id.text_view);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean isRunAlone = NetWorkApplication.isIsRunAlone();

                //beginRequest();
                function();
            }
        });
    }

    public void function() {
        Disposable disposable = CommonRepository.getInstance().getPosInfoRx("yuantong", "11111111111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostInfo>() {
                    @Override
                    public void accept(PostInfo postInfo) throws Exception {
                        if (postInfo != null) {
                            ToastUtil.toastLong(postInfo.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.toastLong("error:" + throwable.getMessage());
                    }
                });
    }

    public void beginRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitService mService = retrofit.create(RetrofitService.class);
        //http://www.kuaidi100.com/query?type=yuantong&postid=111111111111
        //{"message":"ok","nu":"111111111111","ischeck":"1","condition":"D01","com":"yuantong","status":"200","state":"3","data":[{"time":"2021-05-25 10:06:41","ftime":"2021-05-25 10:06:41","context":"[宁波市]您的快件代签收（前台），如有疑问请电联快递员【高贞状，电话：17857292580】。疫情期间顺丰每日对网点消毒、快递员每日测温、配戴口罩，感谢您使用顺丰，期待再次为您服务。（主单总件数：1件）","location":"宁波市鄞州区启新路营业部"},{"time":"2021-05-25 09:52:11","ftime":"2021-05-25 09:52:11","context":"[宁波市]快件交给高贞状,正在派送途中（联系电话：17857292580,顺丰已开启“安全呼叫”保护您的电话隐私,请放心接听！）（主单总件数：1件）","location":"启新路速运营业部"},{"time":"2021-05-25 09:39:24","ftime":"2021-05-25 09:39:24","context":"[宁波市]快件到达 【宁波市鄞州区启新路营业部】","location":"启新路速运营业部"},{"time":"2021-05-25 08:22:47","ftime":"2021-05-25 08:22:47","context":"[宁波市]快件已发车","location":"宁波鄞州中转场"},{"time":"2021-05-25 08:22:39","ftime":"2021-05-25 08:22:39","context":"[宁波市]快件在【宁波鄞州中转场】完成分拣,准备发往 【宁波市鄞州区启新路营业部】","location":"宁波鄞州中转场"},{"time":"2021-05-25 06:30:18","ftime":"2021-05-25 06:30:18","context":"[宁波市]快件到达 【宁波鄞州中转场】","location":"宁波鄞州中转场"},{"time":"2021-05-25 04:21:58","ftime":"2021-05-25 04:21:58","context":"[杭州市]快件已发车","location":"全国航空枢纽（萧山）"},{"time":"2021-05-25 03:18:24","ftime":"2021-05-25 03:18:24","context":"[杭州市]快件在【全国航空枢纽（萧山)】完成分拣,准备发往 【宁波鄞州中转场】","location":"全国航空枢纽（萧山）"},{"time":"2021-05-25 03:18:24","ftime":"2021-05-25 03:18:24","context":"[杭州市]快件到达 【全国航空枢纽（萧山)】","location":"全国航空枢纽（萧山）"},{"time":"2021-05-25 02:03:00","ftime":"2021-05-25 02:03:00","context":"[杭州市]快件到达【杭州】，准备发往【全国航空枢纽（萧山)】","location":"全国航空枢纽（萧山）"},{"time":"2021-05-25 00:45:00","ftime":"2021-05-25 00:45:00","context":"[武汉市]快件在【武汉飞往杭州航班上】已起飞","location":"武汉天河航空站点"},{"time":"2021-05-24 23:08:53","ftime":"2021-05-24 23:08:53","context":"[武汉市]快件在【武汉总集散中心】完成分拣,准备发往下一站","location":"武汉天河航空站点"},{"time":"2021-05-24 21:25:39","ftime":"2021-05-24 21:25:39","context":"[武汉市]快件到达 【武汉总集散中心】","location":"武汉天河航空站点"},{"time":"2021-05-24 20:38:45","ftime":"2021-05-24 20:38:45","context":"[武汉市]快件已发车","location":"武汉吴家山中转场"},{"time":"2021-05-24 19:51:31","ftime":"2021-05-24 19:51:31","context":"[武汉市]快件在【武汉吴家山中转场】完成分拣,准备发往 【武汉总集散中心】","location":"武汉吴家山中转场"},{"time":"2021-05-24 19:28:43","ftime":"2021-05-24 19:28:43","context":"[武汉市]快件到达 【武汉吴家山中转场】","location":"武汉吴家山中转场"},{"time":"2021-05-24 18:41:02","ftime":"2021-05-24 18:41:02","context":"[武汉市]快件已发车","location":"华侨城速运营业点"},{"time":"2021-05-24 18:28:47","ftime":"2021-05-24 18:28:47","context":"[武汉市]快件在【武汉洪山华侨城速运营业点】完成分拣,准备发往 【武汉吴家山中转场】","location":"华侨城速运营业点"},{"time":"2021-05-24 18:07:39","ftime":"2021-05-24 18:07:39","context":"[武汉市]顺丰速运 已收取快件","location":"华侨城速运营业点"}]}
        Flowable<PostInfo> observable = mService.getPosInfoRx("yuantong", "11111111111");
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostInfo>() {
                    @Override
                    public void accept(PostInfo postInfo) throws Exception {
                        if (postInfo != null) {
                            ToastUtil.toastLong(postInfo.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.toastLong("error:" + throwable.getMessage());
                    }
                });
    }
}
