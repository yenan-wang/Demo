package com.example.demo.main.second.clicktouch;

import android.view.MotionEvent;

public interface OnTouchEventListener {

    @interface TouchEventFrom {
        String FROM_ACTIVITY = "activity";
        String FROM_BOTTOM = "bottom";
        String FROM_MIDDLE = "middle";
        String FROM_TOP = "top";
    }

    @interface TouchType {
        String TYPE_DISPATCH_TOUCH_EVENT = "dispatchTouchEvent";
        String TYPE_ON_TOUCH_EVENT = "onTouchEvent";
        String TYPE_ON_INTERCEPT_TOUCH_EVENT = "OnInterceptTouchEvent";
    }

    void touchResult(@TouchEventFrom String touchEventFrom,
                     int touchEventAction,
                     @TouchType String touchType);
}
