package com.ngb.common.ui.basics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Graph extends View {
    private Paint mPaint;
    private RectF mRectF;
    private float mX = -1;
    private float mY = -1;

    public Graph(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Graph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Graph(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mRectF = new RectF(200, 100, 500, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRectF.contains(mX, mY)) {
            mPaint.setColor(Color.GREEN);
        } else {
            mPaint.setColor(Color.BLUE);
        }
        canvas.drawRoundRect(mRectF, 10, 10, mPaint);

        canvas.drawOval(100, 100, 300, 400, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = event.getX();
        mY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mX = -1;
            mY = -1;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

}
