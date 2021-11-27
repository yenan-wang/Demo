package com.example.demo.business;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.constants.ARouterPath;

@Route(path = ARouterPath.Business.HOME_ACTIVITY)
public class BusinessActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}