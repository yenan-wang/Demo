package com.example.demo.second.clicktouch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class BottomGroup extends RelativeLayout {

    @InterceptType
    private String mDispatchTouchEventType;
    @InterceptType
    private String mOnInterceptTouchEventType;
    @InterceptType
    private String mOnTouchEventType;
    private OnTouchEventListener mOnTouchEventListener;

    public BottomGroup(Context context) {
        super(context);
    }

    public BottomGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mOnTouchEventListener != null) {
            mOnTouchEventListener.touchResult(OnTouchEventListener.TouchEventFrom.FROM_BOTTOM,
                    ev.getAction(), OnTouchEventListener.TouchType.TYPE_DISPATCH_TOUCH_EVENT);
        }
        switch (mDispatchTouchEventType) {
            case InterceptType.DEFAULT:
                return super.dispatchTouchEvent(ev);
            case InterceptType.TRUE:
                return true;
            case InterceptType.FALSE:
                return false;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOnTouchEventListener != null) {
            mOnTouchEventListener.touchResult(OnTouchEventListener.TouchEventFrom.FROM_BOTTOM,
                    ev.getAction(), OnTouchEventListener.TouchType.TYPE_ON_INTERCEPT_TOUCH_EVENT);
        }
        switch (mOnInterceptTouchEventType) {
            case InterceptType.DEFAULT:
                return super.onInterceptTouchEvent(ev);
            case InterceptType.TRUE:
                return true;
            case InterceptType.FALSE:
                return false;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnTouchEventListener != null) {
            mOnTouchEventListener.touchResult(OnTouchEventListener.TouchEventFrom.FROM_BOTTOM,
                    event.getAction(), OnTouchEventListener.TouchType.TYPE_ON_TOUCH_EVENT);
        }
        switch (mOnTouchEventType) {
            case InterceptType.DEFAULT:
                return super.onTouchEvent(event);
            case InterceptType.TRUE:
                return true;
            case InterceptType.FALSE:
                return false;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public String getDispatchTouchEventType() {
        return mDispatchTouchEventType;
    }

    public void setDispatchTouchEventType(@InterceptType String dispatchTouchEventType) {
        mDispatchTouchEventType = dispatchTouchEventType;
    }

    public String getOnInterceptTouchEventType() {
        return mOnInterceptTouchEventType;
    }

    public void setOnInterceptTouchEventType(@InterceptType String onInterceptTouchEventType) {
        mOnInterceptTouchEventType = onInterceptTouchEventType;
    }

    public String getOnTouchEventType() {
        return mOnTouchEventType;
    }

    public void setOnTouchEventType(@InterceptType String onTouchEventType) {
        mOnTouchEventType = onTouchEventType;
    }

    public void setOnTouchEventListener(OnTouchEventListener onTouchEventListener) {
        mOnTouchEventListener = onTouchEventListener;
    }

}
