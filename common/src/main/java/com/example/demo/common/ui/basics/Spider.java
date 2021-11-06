package com.example.demo.common.ui.basics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Spider extends View {
    private Paint mRadarPaint;
    private Paint mValuePaint;
    private float mRadius;
    private int mCenterX;
    private int mCenterY;
    private Path mPath;
    private int mAngle = 60;

    public Spider(Context context) {
        this(context, null);
    }

    public Spider(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Spider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Spider(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        mRadarPaint = new Paint();
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setColor(Color.GREEN);

        mValuePaint = new Paint();
        mValuePaint.setStyle(Paint.Style.FILL);
        mValuePaint.setColor(Color.BLUE);

        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = (Math.min(w, h) / 2.0f * 0.9f);
        mCenterX = w / 2;
        mCenterY = h / 2;
        invalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画六边形的蜘蛛网
        drawSpiderNet(canvas);
        //画网格中线
        drawLines(canvas);
        //画数据图
        drawData(canvas);
    }

    private void drawSpiderNet(Canvas canvas) {

        mRadius = 500;


        float r = mRadius / 6;
        mRadarPaint.setStrokeWidth(20);
        //canvas.drawPoint(mCenterX, mCenterY, mRadarPaint);
        mPath.moveTo(mCenterX, mCenterY);
        //mRadarPaint.setStrokeWidth(5);
        for (int i = 1; i <= 6; i++) {
            float curR = r * i;
            mPath.reset();
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    mPath.moveTo(mCenterX + curR, mCenterY);
                } else {
                    float x = (float) (mCenterX + curR * Math.cos(Math.toRadians(mAngle) * j));
                    float y = (float) (mCenterY + curR * Math.sin(Math.toRadians(mAngle) * j));
                    mPath.lineTo(x, y);
                }
            }
            mPath.close();
            canvas.drawPath(mPath, mRadarPaint);
            //mRadius = mRadius - mDistance;
        }

    }

    private void drawLines(Canvas canvas) {
                for (int i = 1; i <= 6; i++) {
            mPath.reset();
            mPath.moveTo(mCenterX, mCenterY);
            float x = (float) (mCenterX + mRadius * Math.cos(Math.toRadians(mAngle) * i));
            float y = (float) (mCenterY + mRadius * Math.sin(Math.toRadians(mAngle) * i));
            mPath.lineTo(x, y);
            canvas.drawPath(mPath, mRadarPaint);
        }

    }

    private void drawData(Canvas canvas) {

    }
}
