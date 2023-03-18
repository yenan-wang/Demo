package com.example.demo.main.fifth.ballslide;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.ngb.wyn.common.utils.LogUtil;

public class RunBall extends Ball {
    public static final int UP_RIGHT_X = 0;
    public static final int UP_RIGHT_Y = 1;
    public static final int UP_LEFT_Y = 2;
    public static final int UP_LEFT_X = 3;
    public static final int DOWN_LEFT_X = 4;
    public static final int DOWN_LEFT_Y = 5;
    public static final int DOWN_RIGHT_Y = 6;
    public static final int DOWN_RIGHT_X = 7;
    public static final int DIRECTION_HORIZONTAL = 0;
    public static final int DIRECTION_VERTICAL = 1;

    public static final double DEFAULT_ANGEL = Math.PI / 3;

    private static final int MIN_SPEED = 100;

    private float mPositionX;
    private float mPositionY;
    private float mLastPositionX;
    private float mLastPositionY;
    private double mAngel = DEFAULT_ANGEL;
    private Scroller mScroller;
    private boolean mIsMoved;
    private VelocityTracker mVelocityTracker;
    private float mVelocityX;
    private float mVelocityY;


    public RunBall(Context context) {
        super(context);
    }

    public RunBall(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RunBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    public double getAngel() {
        return mAngel;
    }

    public void setAngel(double degreeAngel) {
        mAngel = deal(degreeAngel);
    }

    @Override
    public void initPaint() {
        super.initPaint();
        mScroller = new Scroller(getContext());
    }

    public void moveBack() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "translationX", 0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "translationY", 0);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.start();
    }

    @Override
    public boolean performClick() {
        startMove(mAngel);
        return super.performClick();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mIsMoved = false;
            //记录当前触摸的位置
            mPositionX = event.getX();
            mPositionY = event.getY();
            mLastPositionX = getTranslationX();
            mLastPositionY = getTranslationY();
            //如果处于在滚动状态，则让其停止滚动
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = event.getX() - mPositionX;
            float deltaY = event.getY() - mPositionY;
            if (!mIsMoved && deltaX == 0 && deltaY == 0) {
                mIsMoved = false;
                return super.onTouchEvent(event);
            } else {
                mIsMoved = true;
            }
            float moveX = getTranslationX() + deltaX;
            float moveY = getTranslationY() + deltaY;
            if (moveX < -getLeft()) {
                moveY = -getLeft();
            } else if (moveX > getThisParent().getWidth() - getRight()) {
                moveX = getThisParent().getWidth() - getRight();
            }
            if (moveY < -getTop()) {
                moveY = -getTop();
            } else if (moveY > getThisParent().getHeight() - getBottom()) {
                moveY = getThisParent().getHeight() - getBottom();
            }
            setTranslationX(moveX);
            setTranslationY(moveY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!mIsMoved) {
                performClick();
                return super.onTouchEvent(event);
            }

           mVelocityTracker.computeCurrentVelocity(1000);
            mVelocityX = -mVelocityTracker.getXVelocity();
            mVelocityY = -mVelocityTracker.getYVelocity();
            LogUtil.d("mVelocityX:" + mVelocityX + ", mVelocityY:" + mVelocityY);
            //最后抬手时速度大于最小速度才有效
            if (Math.sqrt(mVelocityX * mVelocityX + mVelocityY * mVelocityY) > MIN_SPEED) {
                LogUtil.d("yy:" + -(getTranslationY() - mLastPositionY) + ", xx:" + (getTranslationX() - mLastPositionX));
                startMove(dealAngelRange(getRadius(-(getTranslationY() - mLastPositionY), getTranslationX() - mLastPositionX)));
            }
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVelocityTracker!= null) {
            mVelocityTracker.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private ViewGroup getThisParent() {
        return (ViewGroup) getParent();
    }

    private void startMove() {
        mScroller.fling(getScrollX(), getScrollY(), (int) mVelocityX, (int) mVelocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        invalidate();
    }
    private void startMove(double angel) {
        LogUtil.d("angel:" + angel);
        float[] distance = getDistance(getThisParent(), angel);
        LogUtil.d("distance[0]:" + distance[0] + ", distance[1]:" + distance[1]);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "translationX", distance[0]);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "translationY", distance[1]);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private float[] getDistance(ViewGroup parent, double angel) {
        float[] distance = new float[2];
        double rightUpAngel = getRadius(getTop() + getTranslationY(), parent.getWidth() - getRight() - getTranslationX());
        double leftUpAngel = getRadius(getTop() + getTranslationY(), getLeft() + getTranslationX());
        double leftDownAngel = getRadius(parent.getHeight() - getBottom() - getTranslationY(), getLeft() + getTranslationX());
        double rightDownAngel = getRadius(parent.getHeight() - getBottom() - getTranslationY(), parent.getWidth() - getRight() - getTranslationX());
        if ((Math.PI * 2 - rightDownAngel <= angel && angel <= Math.PI * 2)
                || (0 <= angel && angel < rightUpAngel)) {
            distance[0] = parent.getWidth() - getRight();
            distance[1] = -(float) (distance[0] * Math.tan(angel));
        } else if (rightDownAngel <= angel && angel < Math.PI - leftUpAngel) {
            distance[1] = -(getTop());
            distance[0] = -(float) (distance[1] / Math.tan(angel));
        } else if (Math.PI - leftUpAngel <= angel && angel < Math.PI + leftDownAngel) {
            distance[0] = -(getLeft());
            distance[1] = -(float) (distance[0] * Math.tan(angel));
        } else if (Math.PI + leftDownAngel <= angel && angel < Math.PI * 2 - rightDownAngel) {
            distance[1] = parent.getHeight() - getBottom();
            distance[0] = -(float) (distance[1] / Math.tan(angel));
        }

        return distance;
    }

    private double deal(double degreeAngel) {
        return Math.toRadians(degreeAngel) % (Math.PI * 2);
    }

    /**
     *
     * @param angel {@link #getRadius(int, int)} {@link #getRadius(float, float)}
     *                                          这两个函数返回的弧度制角度范围为(-π,π)，
     *                                          需要将其结果转换为(0,2π)的范围
     * @return 返回(0,2π)范围内的角度
     */
    private double dealAngelRange(double angel) {
        return (angel + 2 * Math.PI) % (2 * Math.PI);
    }

    private double getRadius(int y, int x) {
        return Math.atan2(y, x);
    }

    private double getRadius(float y, float x) {
        return Math.atan2(y, x);
    }

    /*

    private int[] getDistance(ViewGroup parent, double angel) {
        int[] distance = new int[2];
        if (0 <= angel && angel < Math.PI / 4) {
            distance[0] = -(parent.getWidth() - getRight());
            distance[1] = -(int) (distance[0] * Math.tan(mAngel));
        } else if (Math.PI / 4 <= angel && angel < Math.PI / 2) {
            distance[1] = getTop();
            distance[0] = -(int) (distance[1] / Math.tan(mAngel));
        } else if (Math.PI / 2 <= angel && angel < Math.PI / 4 * 3) {
            distance[1] = -getTop();
            distance[0] = (int) (distance[1] / Math.tan(mAngel));
        } else if (Math.PI / 4 * 3 <= angel && angel < Math.PI) {
            distance[0] = getLeft();
            distance[1] = (int) (distance[0] * Math.tan(mAngel));
        } else if (Math.PI <= angel && angel < Math.PI / 4 * 5) {
            distance[0] = getLeft();
            distance[1] = -(int) (distance[0] * Math.tan(mAngel));
        } else if (Math.PI / 4 * 5 <= angel && angel < Math.PI / 2 * 3) {
            distance[1] = -(parent.getHeight() - getBottom());
            distance[0] = (int) (distance[1] / Math.tan(mAngel));
        } else if (Math.PI / 2 * 3 <= angel && angel < Math.PI / 4 * 7) {
            distance[1] = -(parent.getHeight() - getBottom());
            distance[0] = (int) (distance[1] / Math.tan(mAngel));
        } else if (Math.PI / 4 * 7 <= angel && angel <= Math.PI * 2) {
            distance[0] = -(parent.getWidth() - getLeft());
            distance[1] = -(int) (distance[0] * Math.tan(mAngel));
        }

        return distance;
    }

    private int getDirection(int scrollX, int scrollY) {
        double angel = Math.atan2(scrollY, scrollX);
        return getDirection(angel);
    }

    private int getDirection(double angel) {
        if (-Math.PI <= angel && angel < -Math.PI / 4 * 3) {
            return UP_RIGHT_X;
        } else if (-Math.PI / 4 * 3 <= angel && angel < -Math.PI / 2) {
            return UP_RIGHT_Y;
        } else if (-Math.PI / 2 <= angel && angel < -Math.PI / 4) {
            return UP_LEFT_Y;
        } else if (-Math.PI / 4 <= angel && angel < 0) {
            return UP_LEFT_X;
        } else if (0 <= angel && angel < Math.PI / 4) {
            return DOWN_LEFT_X;
        } else if (Math.PI / 4 <= angel && angel < Math.PI / 2) {
            return DOWN_LEFT_Y;
        } else if (Math.PI / 2 <= angel && angel < Math.PI / 4 * 3) {
            return DOWN_RIGHT_Y;
        } else if (Math.PI / 4 * 3 <= angel && angel <= Math.PI) {
            return DOWN_RIGHT_X;
        }
        return UP_RIGHT_X;
    }

    private int getDirection(double angel) {
        if (-Math.PI <= angel && angel < -Math.PI / 4 * 3) {
            return DOWN_LEFT_X;
        } else if (-Math.PI / 4 * 3 <= angel && angel < -Math.PI / 2) {
            return DOWN_LEFT_Y;
        } else if (-Math.PI / 2 <= angel && angel < -Math.PI / 4) {
            return DOWN_RIGHT_Y;
        } else if (-Math.PI / 4 <= angel && angel < 0) {
            return DOWN_RIGHT_X;
        } else if (0 <= angel && angel < Math.PI / 4) {
            return UP_RIGHT_X;
        } else if (Math.PI / 4 <= angel && angel < Math.PI / 2) {
            return UP_RIGHT_Y;
        } else if (Math.PI / 2 <= angel && angel < Math.PI / 4 * 3) {
            return UP_LEFT_Y;
        } else if (Math.PI / 4 * 3 <= angel && angel <= Math.PI) {
            return UP_LEFT_X;
        }
        return UP_RIGHT_X;
    }

    private int getDirectionXY(int scrollX, int scrollY) {
        double angel = Math.atan2(scrollY, scrollX);
        return getDirectionXY(angel);
    }

    private int getDirectionXY(double angel) {
        if (-Math.PI <= angel && angel < -Math.PI / 4 * 3) {
            return DIRECTION_HORIZONTAL;
        } else if (-Math.PI / 4 * 3 <= angel && angel < -Math.PI / 4) {
            return DIRECTION_VERTICAL;
        } else if (-Math.PI / 4 <= angel && angel < Math.PI / 4) {
            return DIRECTION_HORIZONTAL;
        } else if (Math.PI / 4 <= angel && angel < Math.PI / 4 * 3) {
            return DIRECTION_VERTICAL;
        } else if (Math.PI / 4 * 3 <= angel && angel <= Math.PI) {
            return DIRECTION_HORIZONTAL;
        }
        return DIRECTION_VERTICAL;
    }*/
}
