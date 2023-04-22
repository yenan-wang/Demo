package com.example.demo.main.fifth;

import android.os.Bundle;
import android.view.View;

import com.example.demo.main.R;
import com.example.demo.network.netservice.CommonRepository;
import com.example.demo.network.bean.PostInfo;
import com.example.demo.common.BaseActivity;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivity extends BaseActivity {
    private CommonButton mButton;
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
        mButton = findViewById(R.id.text_view);
        mButton.setOnClickListener(new View.OnClickListener() {
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
}
