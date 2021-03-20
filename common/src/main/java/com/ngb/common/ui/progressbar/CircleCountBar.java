package com.ngb.common.ui.progressbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.ngb.common.R;

public class CircleCountBar extends View {
    private int mSolidColor;
    private int mStrokeColor;
    private int mStrokeWidth;
    private int mRadius;
    private int mSmallSolidColor;
    private int mSmallStrokeColor;
    private int mSmallStrokeWidth;
    private int mSmallRadius;
    private int mProcessColor;
    private int mProcessWidth;
    private int mTextColor;
    private int mTextSize;
    private Paint mCirclePaint;
    private Paint mSmallCirclePaint;
    private Paint mSmallCircleSolidPaint;
    private Paint mProcessPaint;
    private Paint mTextPaint;
    private String mTextString = "0%";
    private long mCountDownTime;
    private float mCurrentAngle;

    public CircleCountBar(Context context) {
        this(context, null);
    }

    public CircleCountBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleCountBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleCountBar);
        mSolidColor = array.getColor(R.styleable.CircleCountBar_CCBSolidColor, Color.LTGRAY);
        mStrokeColor = array.getColor(R.styleable.CircleCountBar_CCBStrokeColor, Color.GRAY);
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBStrokeWidth, R.dimen.dp_5);
        mRadius = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBRadius, R.dimen.dp_20);
        mSmallSolidColor = array.getColor(R.styleable.CircleCountBar_CCBSmallSolidColor, Color.YELLOW);
        mSmallStrokeColor = array.getColor(R.styleable.CircleCountBar_CCBSmallStrokeColor, Color.RED);
        mSmallStrokeWidth = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBSmallStrokeWidth, R.dimen.dp_2);
        mSmallRadius = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBSmallRadius, R.dimen.dp_5);
        mProcessColor = array.getColor(R.styleable.CircleCountBar_CCBProcessColor, Color.GREEN);
        mProcessWidth = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBProcessWidth, R.dimen.dp_5);
        mTextColor = array.getColor(R.styleable.CircleCountBar_CCBTextColor, Color.BLACK);
        mTextSize = array.getDimensionPixelSize(R.styleable.CircleCountBar_CCBTextSize, R.dimen.common_small_text_size);
        array.recycle();
        setPaint();
    }

    public void setPaint() {
        //默认圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true); //抗锯齿
        mCirclePaint.setDither(true); // 防抖动
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setColor(mStrokeColor);

        //进度弧度
        mProcessPaint = new Paint();
        mProcessPaint.setAntiAlias(true);
        mProcessPaint.setDither(true);
        mProcessPaint.setStyle(Paint.Style.STROKE);
        mProcessPaint.setStrokeWidth(mProcessWidth);
        mProcessPaint.setColor(mProcessColor);
        mProcessPaint.setStrokeCap(Paint.Cap.ROUND);

        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setDither(true);
        mSmallCirclePaint.setStyle(Paint.Style.STROKE);
        mSmallCirclePaint.setStrokeWidth(mSmallStrokeWidth);
        mSmallCirclePaint.setColor(mSmallStrokeColor);

        mSmallCircleSolidPaint = new Paint();
        mSmallCircleSolidPaint.setAntiAlias(true);
        mSmallCircleSolidPaint.setDither(true);
        mSmallCircleSolidPaint.setStyle(Paint.Style.FILL);
        mSmallCircleSolidPaint.setColor(mSmallSolidColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        int strokeWidth = Math.max(mStrokeWidth, mProcessWidth);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = getPaddingLeft() + mRadius * 2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = getPaddingTop() + mRadius * 2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        //画圆
        canvas.drawCircle(mRadius, mRadius, mRadius, mCirclePaint);

        //画圆弧
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, 360 * mCurrentAngle, false, mProcessPaint);

        //画文字
        float textWidth = mTextPaint.measureText(mTextString);
        float textHeight = (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        canvas.drawText(mTextString, mRadius - textWidth / 2, mRadius - textHeight, mTextPaint);

        //画小圆
        float currentDegreeFlag = 360 * mCurrentAngle + 0.7f;
        float smallCircleX = 0;
        float smallCircleY = 0;
        float hudu = (float) Math.abs(Math.PI * currentDegreeFlag / 180);//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
        smallCircleX = (float) Math.abs(Math.sin(hudu) * mRadius + mRadius);
        smallCircleY = (float) Math.abs(mRadius - Math.cos(hudu) * mRadius);
        canvas.drawCircle(smallCircleX, smallCircleY, mSmallRadius, mSmallCirclePaint);
        canvas.drawCircle(smallCircleX, smallCircleY, mSmallRadius - mSmallStrokeWidth, mSmallCircleSolidPaint);//画小圆的实心

        canvas.restore();
    }

    public void setCountDownTime(long countDownTime) {
        mCountDownTime = countDownTime;
        mTextString = mCountDownTime / 1000 + "″";
    }

    public void startCountDownTime() {
        setClickable(false);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setDuration(mCountDownTime + 1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                 * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                 * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                 */
                mCurrentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCountDownTime > 0) {
                    setClickable(true);
                } else {
                    setClickable(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        countDownMethod();
    }

    private void countDownMethod() {
        new CountDownTimer(mCountDownTime + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mCountDownTime = mCountDownTime - 1000;
                mTextString = mCountDownTime /1000 + "″";
                invalidate();
            }

            @Override
            public void onFinish() {
                mTextString = "时间到";
                mSmallCirclePaint.setColor(getResources().getColor(R.color.transparent));
                mSmallCircleSolidPaint.setColor(getResources().getColor(R.color.transparent));
                invalidate();
            }
        }.start();
    }
}
