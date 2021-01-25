package com.ngb.common.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ngb.common.R;
import com.ngb.common.utils.ImageUtil;

public class CommonToolBar extends FrameLayout {

    private ImageView mBackImageView;
    private TextView mTitleTextView;
    private ImageView mIconImageView;

    public CommonToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.common_tool_bar_layout, this);
        initView(view);
    }

    public ImageView getBackImageView() {
        return mBackImageView;
    }

    public void setBackImageView(@DrawableRes int backImage) {
        mBackImageView.setImageResource(backImage);
    }

    public void setBackImageView(String backImageUrl) {
        if (TextUtils.isEmpty(backImageUrl)) {
            return;
        }
        ImageUtil.getInstance().loadImage(getContext(), backImageUrl, mBackImageView);
    }

    public void setBackImageViewVisible(int viewVisible) {
        mBackImageView.setVisibility(viewVisible);
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public void setTitleText(String titleText) {
        mTitleTextView.setText(titleText);
    }

    public void setTitleText(@StringRes int titleText) {
        mTitleTextView.setText(titleText);
    }

    public void setTitleTextVisible(int viewVisible) {
        mTitleTextView.setVisibility(viewVisible);
    }

    public ImageView getIconImageView() {
        return mIconImageView;
    }

    public void setIconImage(@DrawableRes int iconImage) {
        mIconImageView.setImageResource(iconImage);
    }

    public void setIconImage(String iconImageUrl) {
        if (TextUtils.isEmpty(iconImageUrl)) {
            return;
        }
        ImageUtil.getInstance().loadImage(getContext(), iconImageUrl, mIconImageView);
    }

    public void setIconImageViewVisible(int viewVisible) {
        mIconImageView.setVisibility(viewVisible);
    }

    public void setBackImageClickListener(OnClickListener listener) {
        mBackImageView.setOnClickListener(listener);
    }

    public void setTitleTextClickListener(OnClickListener listener) {
        mTitleTextView.setOnClickListener(listener);
    }

    public void setIconImageClickListener(OnClickListener listener) {
        mIconImageView.setOnClickListener(listener);
    }

    private void initView(View view) {
        mBackImageView = view.findViewById(R.id.iv_back);
        mBackImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
        mTitleTextView = view.findViewById(R.id.tv_title);
        mIconImageView = view.findViewById(R.id.iv_icon);
    }
}
