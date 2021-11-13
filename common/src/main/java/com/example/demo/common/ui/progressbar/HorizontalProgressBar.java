package com.example.demo.common.ui.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.demo.common.R;
import com.example.demo.common.utils.LogUtil;

import java.text.DecimalFormat;

public class HorizontalProgressBar extends View {
    public static final float PROGRESS_COMPILE = 1F;
    private Paint mBottomBackgroundPaint;
    private Paint mTextPaint;
    private int mBottomColor;
    private int mProgressColor;
    private int mTextColor;
    private RectF mLeftRectF;
    private RectF mRightRectF;
    private RectF mProgressRectF;
    private Path mBackgroundPath;
    private float mProgress;
    private DecimalFormat mFormat = new DecimalFormat("#.##");
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    private OnProcessStatusListener mOnProcessStatusListener;

    public HorizontalProgressBar(Context context) {
        super(context);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
        mBottomColor = t.getColor(R.styleable.HorizontalProgressBar_HPBBackgroundColor, getResources().getColor(R.color.colorPrimaryTrans, context.getTheme()));
        mProgressColor = t.getColor(R.styleable.HorizontalProgressBar_HPBProgressColor, getResources().getColor(R.color.colorPrimary, context.getTheme()));
        mTextColor = t.getColor(R.styleable.HorizontalProgressBar_HPBTextColor, Color.WHITE);
        initPaint();
    }

    public int getBackgroundColor() {
        return mBottomColor;
    }

    public void setBottomColor(int bottomColor) {
        mBottomColor = bottomColor;
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        postInvalidate();
    }

    public OnProcessStatusListener getOnProcessStatusListener() {
        return mOnProcessStatusListener;
    }

    public void setOnProcessStatusListener(OnProcessStatusListener onProcessStatusListener) {
        mOnProcessStatusListener = onProcessStatusListener;
    }

    private void initPaint() {
        setClickable(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBottomBackgroundPaint = new Paint();
        mBottomBackgroundPaint.setAntiAlias(true);
        mBottomBackgroundPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));

        mBackgroundPath = new Path();
        mLeftRectF = new RectF();
        mRightRectF = new RectF();
        mProgressRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width >= 2 * height) {
            mBackgroundPath.addArc(0F, 0F, (float) height, (float) height, 90F, 180F);
            mBackgroundPath.lineTo(width - height / 2F, 0F);
            mBackgroundPath.addArc((float) (width - height), 0F, (float) width, (float) height, -90F, 180F);
            mBackgroundPath.lineTo(height / 2F, height);
        } else {
            mBackgroundPath.addArc(0F, 0F, width / 3F * 2F, (float) height, 90F, 180F);
            mBackgroundPath.lineTo(width / 3F * 2F, 0F);
            mBackgroundPath.addArc(width / 3F, 0F, (float) width, (float) height, -90F, 180F);
            mBackgroundPath.lineTo(width / 3F, height);
        }

        //背景
        mBottomBackgroundPaint.setColor(mBottomColor);
        canvas.drawPath(mBackgroundPath, mBottomBackgroundPaint);

        //进度条
        mProgressRectF.left = 0;
        mProgressRectF.top = 0;
        mProgressRectF.right = mProgress * width;
        mProgressRectF.bottom = height;
        mBottomBackgroundPaint.setXfermode(mXfermode);
        mBottomBackgroundPaint.setColor(mProgressColor);
        canvas.drawRect(mProgressRectF, mBottomBackgroundPaint);
        mBottomBackgroundPaint.setXfermode(null);

        //画文字
        //控制进度在0-1之间
        if (mProgress < 0F) {
            mProgress = 0F;
        }
        if (mProgress > 1F) {
            mProgress = 1F;
        }
        //显示百分比时的格式化
        String s = mFormat.format(mProgress * 100);
        //根据宽自适应字号大小
        mTextPaint.setTextSize(width / 4F);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        float baseLineY = (height / 2F - top / 2F - bottom / 2F);//基线中间点的y轴计算公式

        canvas.drawText(s + "%", width / 2F, baseLineY, mTextPaint);
        //如果100%，则回调完成
        if (mProgress == PROGRESS_COMPILE && mOnProcessStatusListener != null) {
            mOnProcessStatusListener.complete();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = 280;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 150;
        }
        setMeasuredDimension(width, height);
    }

    public interface OnProcessStatusListener {

        void complete();

    }
}
