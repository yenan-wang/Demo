package com.example.demo.main.third;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.constants.ARouterPath;
import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;

public class ARouterActivity extends BaseCommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        initView();
    }

    private void initView() {
        CommonButton button = findViewById(R.id.router_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.router_button) {
            ARouter.getInstance().build(ARouterPath.Business.HOME_ACTIVITY).navigation();
        }
    }
}