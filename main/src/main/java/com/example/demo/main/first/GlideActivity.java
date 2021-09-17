package com.example.demo.main.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.demo.main.R;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.utils.ImageUtil;

public class GlideActivity extends BaseCommonActivity implements View.OnClickListener {
    private static final String IMAGE_URL = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg";
    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mImageView = findViewById(R.id.image_view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            ImageUtil.getInstance().loadImageSimple(this, IMAGE_URL, mImageView);
        }
    }
}
