package com.example.demo.main.six.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.example.demo.main.R;

public class NestedParent extends FrameLayout {
    private Button mButton;
    private NestedChild mChild;
    private Wrapper mWrapper;
    private Scroller mScroller;

    public NestedParent(Context context) {
        super(context);
    }

    public NestedParent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedParent(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NestedParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mButton = findViewById(R.id.nested_button);
        mChild = findViewById(R.id.nested_child);
        //mWrapper = findViewById(R.id.container_child);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d("ngb_log", "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d("ngb_log", "onNestedPreScroll, dx=" + dx + ", dy=" + dy);
        if (dy > 0) {
            //向上滑
            Log.d("ngb_log", "dy > 0");
            int result = mButton.getMeasuredHeight() - getScrollY();
            int consume = Math.min(result, dy);
            Log.d("ngb_log", "result = " + result + ", scrollY:" + getScrollY());
            scrollBy(dx, consume);
            consumed[1] = consume;
        } else {
            consumed[1] = 0;
            //向下滑
            Log.d("ngb_log", "dy < 0");
        }
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d("ngb_log", "onNestedScrollAccepted, child=" + child.getClass().getSimpleName() + ", target=" + target.getClass().getSimpleName());
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d("ngb_log", "onNestedScroll, dxConsumed=" + dxConsumed + ", dyConsumed=" + dyConsumed + ", dxUnconsumed=" + dxUnconsumed + ", dyUnconsumed=" + dyUnconsumed);
        Log.d("ngb_log", "getScrollY():" + getScrollY());

        if (dyUnconsumed > 0) {
            if (getScrollY() < mButton.getMeasuredHeight()) {
                scrollBy(0, dyUnconsumed);
            }
        } else {
            if (getScrollY() >= 0) {
                scrollBy(0, dyUnconsumed);
            }
        }

    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (Math.abs(getScrollY()) < mButton.getMeasuredHeight()) {
            Log.d("ngb_log", "onNestedPreFling, true");
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            mScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, 0, mButton.getMeasuredHeight());
            invalidate();
            return true;
        } else {
            Log.d("ngb_log", "onNestedPreFling, false");
            return false;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void stopNestedScroll() {
        Log.d("ngb_log", "stopNestedScroll");
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d("ngb_log", "scrolling...");
            scrollTo(0, mScroller.getCurrY());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }
}
