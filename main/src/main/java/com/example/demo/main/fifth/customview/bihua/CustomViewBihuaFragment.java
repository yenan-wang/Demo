package com.example.demo.main.fifth.customview.bihua;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.common.ui.CommonButton;
import com.example.demo.common.ui.basics.Bihua;
import com.example.demo.common.utils.AssetsUtil;
import com.example.demo.common.utils.ToastUtil;
import com.example.demo.main.R;

public class CustomViewBihuaFragment extends Fragment implements View.OnClickListener {

    private Bihua mBihua;
    private CommonButton mParseButton;
    private CommonButton mPlayButton;
    private EditText mEditText;
    private BihuaViewModel mBihuaViewModel;
    private boolean mIsNeedPlay = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.custom_fragment_bihua_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mParseButton = view.findViewById(R.id.parse_button);
        mParseButton.setOnClickListener(this);
        mPlayButton = view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(this);
        mEditText = view.findViewById(R.id.edit_text);
        mBihua = view.findViewById(R.id.play_area);
        mBihuaViewModel = new BihuaViewModel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.parse_button) {
            preParse();
        } else if (v.getId() == R.id.play_button) {
            if (mBihua.hasParse()) {
                mBihua.play();
            } else {
                mIsNeedPlay = true;
                preParse();
            }
        }
    }

    private void preParse() {
        String hanzi = mEditText.getText().toString();
        if (TextUtils.isEmpty(hanzi)) {
            parse();
        } else {
            request(hanzi);
        }
    }

    private void parse() {
        String content = AssetsUtil.readAssetsFile(getContext(), "wo.json");
        boolean isSuccess = mBihua.parse(content);
        if (isSuccess) {
            if (mIsNeedPlay) {
                mIsNeedPlay = false;
                mBihua.play();
            } else {
                ToastUtil.toastLong("解析成功！");
            }
        } else {
            ToastUtil.toastLong("解析失败！");
        }
    }

    private void request(String hanzi) {
        mBihuaViewModel.getHanziData(hanzi, new BihuaViewModel.OnRequestResult() {
            @Override
            public void onSuccess(String jsonString) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isParse = mBihua.parse(jsonString);
                        if (isParse) {
                            if (mIsNeedPlay) {
                                mIsNeedPlay = false;
                                mBihua.play();
                            } else {
                                ToastUtil.toastLong("解析成功！");
                            }
                        } else {
                            ToastUtil.toastLong("解析失败！");
                        }
                    }
                });
            }

            @Override
            public void onFail(Throwable throwable) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toastLong("请求失败！msg:" + throwable.getMessage());
                    }
                });
            }
        });
    }
}
