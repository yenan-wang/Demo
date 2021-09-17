package com.example.demo.common.ui.basics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Line extends View {
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;

    public Line(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Line(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Line(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(50, 50, 100, 100, mPaint);

        mPaint.setColor(Color.BLUE);
        float[] aa = {150, 150, 200, 200, 250, 250, 300, 300, 400, 400, 500, 500};
        //数组里从第0(offset)个开始，使用12(count)个数字，因为一条直线需要4个坐标值，因此一般count为4的倍数
        canvas.drawLines(aa, 0, 12, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawPoint(500, 500, mPaint);

        canvas.drawRect(600, 600, 800, 800, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        /*mPath.moveTo(100, 1000);
        mPath.lineTo(600, 1000);
        mPath.lineTo(600, 1600);
        mPath.close();
        canvas.drawPath(mPath, mPaint);*/

        canvas.drawArc(100, 10, 200, 100, 0, 90, true, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mRectF.set(300, 1000, 500, 1300);
        /*mPath.moveTo(300, 1000);
        mPath.arcTo(mRectF, 0, 120, true);*/
        mPath.addRect(mRectF, Path.Direction.CCW);
        mPath.addOval(400, 1000, 800, 1400, Path.Direction.CCW);
        mPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(mPath, mPaint);

    }
}
