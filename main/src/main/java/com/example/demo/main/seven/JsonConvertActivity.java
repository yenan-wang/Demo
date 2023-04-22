package com.example.demo.main.seven;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.demo.common.BaseActivity;
import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.AssetsUtil;
import com.ngb.wyn.common.utils.JsonUtil;
import com.ngb.wyn.common.utils.LogUtil;
import com.ngb.wyn.common.utils.ToastUtil;

import org.json.JSONException;

public class JsonConvertActivity extends BaseActivity {

    private static final String TAG = "JsonConvertActivity";

    private CommonButton mReadButton;
    private CommonButton mDealButton;
    private TextView mJsonTextView;
    private String mOriginalString;
    private String mCompressString;
    private boolean isCompressed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_convert_layout);
        mReadButton = findViewById(R.id.read_json);
        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mOriginalString)) {
                    mOriginalString = AssetsUtil.readAssetsFile(JsonConvertActivity.this, "api.json");
                }
                mJsonTextView.setText(mOriginalString);
                isCompressed = false;
                mDealButton.setText(R.string.compress_json);
            }
        });
        mJsonTextView = findViewById(R.id.json_text);
        mDealButton = findViewById(R.id.deal_json);
        mDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mJsonTextView.getText().toString();
                if (isCompressed) {
                    expandJson(s);
                } else {
                    compressJson(s);
                }
            }
        });
    }

    private void expandJson(String text) {
        LogUtil.i(TAG, "expandJson.");
        if (TextUtils.isEmpty(text)) {
            return;
        }
        try {
            String expandText = JsonUtil.formatJson(text, 4);
            mJsonTextView.setText(expandText);
            isCompressed = false;
            mDealButton.setText(R.string.compress_json);
        } catch (JSONException e) {
            LogUtil.e(TAG, "expandJson, error:" + e.getMessage());
            ToastUtil.toastShort(getString(R.string.expand_error));
        }
    }

    private void compressJson(String text) {
        LogUtil.i(TAG, "compressJson.");
        if (TextUtils.isEmpty(text)) {
            return;
        }
        try {
            String compressText = JsonUtil.compressJson(text, true);
            mJsonTextView.setText(compressText);
            isCompressed = true;
            mDealButton.setText(R.string.expand_json);
        } catch (JSONException e) {
            LogUtil.e(TAG, "compressJson, error:" + e.getMessage());
            ToastUtil.toastShort(getString(R.string.compress_error));
        }
    }
}
