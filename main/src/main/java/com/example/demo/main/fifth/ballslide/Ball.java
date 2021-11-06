package com.example.demo.main.fifth.ballslide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

import com.example.demo.common.utils.LogUtil;
import com.example.demo.main.R;

public class Ball extends View {
    @BallColor
    protected int mBallColor = BallColor.COLOR_RED;
    protected Paint mPaint;
    protected float mRadius;
    protected int mTranslucent;
    @IntRange(from = 0, to = 255)
    protected int mAlpha;

    public Ball(Context context) {
        super(context);
    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Ball);
        mRadius = array.getDimensionPixelOffset(R.styleable.Ball_ballRadius, 100);
        mAlpha = array.getInteger(R.styleable.Ball_ballAlpha, 20);
        initPaint();
        mTranslucent = Color.argb(mAlpha, Color.red(mBallColor), Color.green(mBallColor), Color.blue(mBallColor));
        array.recycle();
        setClickable(true);
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mBallColor);
        mPaint.setAntiAlias(true);
        RadialGradient gradient = new RadialGradient(mRadius / 2, mRadius / 2, mRadius, mTranslucent, mBallColor, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.EXACTLY == measureWidthMode ? measureWidth : (int) (mRadius * 2);
        int height = MeasureSpec.EXACTLY == measureHeightMode ? measureHeight : (int) (mRadius * 2);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("onTouchEvent,event:" + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        LogUtil.d("performClick");
        return super.performClick();
    }

    private void setNewStatus(@BallColor int ballColor) {
        mBallColor = ballColor;
        RadialGradient gradient = new RadialGradient(mRadius / 2, mRadius / 2, mRadius, mTranslucent, ballColor, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
    }


}
