package com.example.demo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.demo.main.customview.CustomViewActivity;
import com.example.demo.main.first.GlideActivity;
import com.example.demo.main.first.TextActivity;
//import com.example.demo.main.fourth.AidlActivity;
import com.example.demo.main.fourth.VideoActivity;
import com.example.demo.main.fourth.recyclerview.RecyclerViewActivity;
import com.example.demo.main.second.NotificationActivity;
import com.example.demo.main.second.clicktouch.ClickEventAndDispatchActivity;
import com.ngb.common.BaseCommonActivity;
import com.ngb.common.ui.CommonButton;
import com.ngb.network.WebViewActivity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends BaseCommonActivity {
    private static final int DEFAULT_COLUMN_COUNT = 3;
    private LinearLayout mEntryButtonArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEntryButtonArea = findViewById(R.id.entry_button_area);
        final Map<String, Class> map = getButtonNameAndActivityMap();
        Set<String> strings = map.keySet();
        int pos = 0;
        for (final String buttonName : strings) {
            CommonButton commonButton = new CommonButton(this);
            commonButton.setTag(buttonName);
            commonButton.setText(buttonName);
            commonButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, map.get(buttonName)));
                }
            });
            int padding = getResources().getDimensionPixelOffset(R.dimen.dp_3);
            if ((pos % DEFAULT_COLUMN_COUNT) == 0) {
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(0, padding, 0, padding);
                linearLayout.addView(commonButton);
                mEntryButtonArea.addView(linearLayout);
            } else {
                LinearLayout linearLayout = (LinearLayout) mEntryButtonArea.getChildAt((pos / DEFAULT_COLUMN_COUNT));
                linearLayout.addView(commonButton);
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) commonButton.getLayoutParams();
            params.leftMargin = padding;
            params.rightMargin = padding;
            commonButton.setLayoutParams(params);
            pos++;
        }
    }

    private Map<String, Class> getButtonNameAndActivityMap() {
        Map<String, Class> buttonNameList = new LinkedHashMap<>();
        buttonNameList.put(getString(R.string.button_1_1), TextActivity.class);
        buttonNameList.put(getString(R.string.button_1_2), TextActivity.class);
        buttonNameList.put(getString(R.string.button_1_3), GlideActivity.class);

        buttonNameList.put(getString(R.string.button_2_1), ClickEventAndDispatchActivity.class);
        buttonNameList.put(getString(R.string.button_2_2), TextActivity.class);
        buttonNameList.put(getString(R.string.button_2_3), NotificationActivity.class);

        //buttonNameList.put(getString(R.string.button_3_1), DemoActivity.class);

        buttonNameList.put(getString(R.string.button_3_2), WebViewActivity.class);
 /*       buttonNameList.put(getString(R.string.button_3_3), TextActivity.class);
*/
        //buttonNameList.put(getString(R.string.button_4_1), AidlActivity.class);
        buttonNameList.put(getString(R.string.button_4_2), VideoActivity.class);
        buttonNameList.put(getString(R.string.button_4_3), RecyclerViewActivity.class);
/*
        buttonNameList.put(getString(R.string.button_5_1), TextActivity.class);
        buttonNameList.put(getString(R.string.button_5_2), TextActivity.class);
        */
        buttonNameList.put(getString(R.string.button_5_3), CustomViewActivity.class);
/*
        buttonNameList.put(getString(R.string.button_6_1), TextActivity.class);
        buttonNameList.put(getString(R.string.button_6_2), TextActivity.class);
        buttonNameList.put(getString(R.string.button_6_3), TextActivity.class);
*/

        return buttonNameList;
    }
}