package com.example.demo.main.second.clicktouch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TopTextView extends AppCompatTextView {
    @InterceptType
    private String mDispatchTouchEventType;
    @InterceptType
    private String mOnTouchEventType;
    private OnTouchEventListener mOnTouchEventListener;

    public TopTextView(@NonNull Context context) {
        super(context);
    }

    public TopTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mOnTouchEventListener != null) {
            mOnTouchEventListener.touchResult(OnTouchEventListener.TouchEventFrom.FROM_TOP,
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
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnTouchEventListener != null) {
            mOnTouchEventListener.touchResult(OnTouchEventListener.TouchEventFrom.FROM_TOP,
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
