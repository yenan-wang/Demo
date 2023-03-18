package com.example.demo.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.demo.account._pojo.UserInfo;
import com.example.demo.common.BaseCommonActivity;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.ToastUtil;

public class DemoActivity extends BaseCommonActivity {

    private CommonButton mCommonButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        mCommonButton = findViewById(R.id.demo_button);
        mCommonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId("123456");
                userInfo.setNickName("我的昵称");
                userInfo.setSex(1);
                userInfo.setAccountId("5555");
                userInfo.setRegisterTime(System.currentTimeMillis());
                ToastUtil.toastLong(userInfo.toString());
            }
        });
    }
}
