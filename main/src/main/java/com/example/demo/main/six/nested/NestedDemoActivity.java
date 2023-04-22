package com.example.demo.main.six.nested;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.demo.common.BaseActivity;
import com.example.demo.main.R;

public class NestedDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_demo_layout);
        /*Wrapper container = findViewById(R.id.container_child);
        NestedChild child = new NestedChild(this);
        container.addView(child);*/
    }
}
