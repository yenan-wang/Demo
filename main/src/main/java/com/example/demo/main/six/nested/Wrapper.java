package com.example.demo.main.six.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

@Deprecated //错误的示例
public class Wrapper extends FrameLayout {
    private float mLastActionX = 0;
    private float mLastActionY = 0;
    private int[] mConsumed = new int[2];
    private int[] mOffsetWindow = new int[2];
    private VelocityTracker mVelocityTracker;
    private boolean eventDownDispatched = false;
    private boolean eventCancelDispatched = false;
    private float mNestedYOffset;
    private Scroller mScroller;
    private NestedChild mNestedChild;
    private int mTouchSlop;
    private int mActivePointerId = ViewDragHelper.INVALID_POINTER;

    public Wrapper(@NonNull Context context) {
        this(context, null);
    }

    public Wrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Wrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mVelocityTracker = VelocityTracker.obtain();
                mVelocityTracker.addMovement(event);
                mLastActionX = event.getX();
                mLastActionY = event.getY();
                startNestedScroll(SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mLastActionX - event.getX();
                float dy = mLastActionY - event.getY();
                //int deltaX = (int) dx;
                int deltaX = 0;
                int deltaY = (int) dy;

                if (dispatchNestedPreScroll(deltaX, deltaY, mConsumed, mOffsetWindow)) {
                    //deltaX -= mConsumed[0];
                    deltaY -= mConsumed[1];
                }
                *//*if (mConsume[1] != 0) {
                    scrollBy(0, -deltaY);
                }*//*

                Log.d("ngb_log", "deltaY:" + deltaY);
                int consumeY;
                if (deltaY > 0) {
                    if (canScrollHorizontally(1)) {
                        Log.d("ngb_log", "> 0:" + true);
                        consumeY = deltaY;
                    } else {
                        Log.d("ngb_log", "> 0:" + false);
                        consumeY = 0;
                    }
                } else {
                    if (canScrollHorizontally(-1)) {
                        Log.d("ngb_log", "< 0:" + true);
                        consumeY = 0;
                    } else {
                        Log.d("ngb_log", "< 0:" + false);
                        consumeY = deltaY;
                    }
                }
                Log.d("ngb_log", "consumeY:" + consumeY);


                if (dispatchNestedScroll(0, consumeY, 0, (deltaY - consumeY), mOffsetWindow)) {
                    Log.d("ngb_log", "继续消耗");
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float vx = mVelocityTracker.getXVelocity();
                float vy = mVelocityTracker.getYVelocity();
                Log.d("ngb_log", "vx:" + vx + ", vy: " + vy);
                if (!dispatchNestedPreFling(vx, vy)) {

                }
                dispatchNestedFling(vx, vy, true);
                stopNestedScroll();
                mVelocityTracker.recycle();
                break;
        }
        return true;*/
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getChildCount() == 0) {
            return super.dispatchTouchEvent(ev);
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        MotionEvent motionEvent = MotionEvent.obtain(ev);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                findNestedChild(this);
                mNestedYOffset = 0;
                motionEvent.offsetLocation(0, mNestedYOffset);
                mLastActionX = ev.getX();
                mLastActionY = ev.getY();
                eventDownDispatched = true;

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mActivePointerId = ev.getPointerId(0);
                startNestedScroll(SCROLL_AXIS_VERTICAL);
                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == ViewDragHelper.INVALID_POINTER) {
                    break;
                }
                int y = (int) ev.getY(activePointerIndex);
                int deltaY = (int) (mLastActionY - y);

                if (dispatchNestedPreScroll(0, deltaY, mConsumed, mOffsetWindow)) {
                    deltaY -= mConsumed[1];
                    motionEvent.offsetLocation(0, mOffsetWindow[1]);
                    mNestedYOffset += mOffsetWindow[1];
                }

                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (deltaY > 0) {
                    deltaY -= mTouchSlop;
                } else {
                    deltaY += mTouchSlop;
                }

                mLastActionY = y - mOffsetWindow[1];
                MotionEvent motionEvent1 = MotionEvent.obtain(motionEvent);
                motionEvent1.setAction(MotionEvent.ACTION_MOVE);
                super.dispatchTouchEvent(motionEvent1);
                motionEvent1.recycle();

                int scrollY = 0;
                if (mNestedChild != null) {
                    scrollY = mNestedChild.getScrollY();
                }

                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        /*View view = (View) getParent();
        view.invalidate();*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }*/
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void findNestedChild(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (getChildAt(i) instanceof NestedChild) {
                mNestedChild = (NestedChild) getChildAt(i);
            }
        }
    }
}
