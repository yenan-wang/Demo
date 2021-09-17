package com.example.demo.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.demo.common.R;

public class CommonButton extends AppCompatButton {
    private static final float DEFAULT_SCALE_X = 0.5f;
    private static final float DEFAULT_SCALE_Y = 0.5f;
    private static final int DEFAULT_DURATION = 300;

    private float mScaleX;
    private float mScaleY;
    private long mDuration;
    private Drawable mDrawable;

    public CommonButton(Context context) {
        super(context);
        init(context);
    }

    public CommonButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommonButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        TypedArray array = context.obtainStyledAttributes(R.styleable.CommonButton);
        mScaleX = array.getFloat(R.styleable.CommonButton_scaleX, DEFAULT_SCALE_X);
        mScaleY = array.getFloat(R.styleable.CommonButton_scaleY, DEFAULT_SCALE_Y);
        mDuration = array.getInt(R.styleable.CommonButton_duration, DEFAULT_DURATION);
        array.recycle();
        setBackgroundResource(R.drawable.common_button_background);
        int padding = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        setPadding(padding, getPaddingTop(), padding, getPaddingBottom());

    }
}
