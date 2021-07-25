package com.example.demo.second.clicktouch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.demo.R;
import com.ngb.common.BaseApplication;
import com.ngb.common.BaseCommonActivity;

public class ClickEventAndDispatchActivity extends BaseCommonActivity implements OnTouchEventListener {
    private String[] mSelectArray = BaseApplication.getInstance().getResources().getStringArray(R.array.touch_select_array);
    private BottomGroup mBottomGroup;
    private MiddleGroup mMiddleGroup;
    private TopTextView mTopTextView;
    private Button mButtonActivity1, mButtonActivity2, mButtonActivity3;
    private Button mButtonBottom1, mButtonBottom2, mButtonBottom3;
    private Button mButtonMiddle1, mButtonMiddle2, mButtonMiddle3;
    private Button mButtonTop1, mButtonTop2, mButtonTop3;
    private Spinner mSpinnerActivityOnTouchEvent, mSpinnerActivityDispatchTouchEvent, mSpinnerActivityOnInterceptTouchEvent;
    private Spinner mSpinnerBottomOnTouchEvent, mSpinnerBottomDispatchTouchEvent, mSpinnerBottomOnInterceptTouchEvent;
    private Spinner mSpinnerMiddleOnTouchEvent, mSpinnerMiddleDispatchTouchEvent, mSpinnerMiddleOnInterceptTouchEvent;
    private Spinner mSpinnerTopOnTouchEvent, mSpinnerTopDispatchTouchEvent, mSpinnerTopOnInterceptTouchEvent;

    @InterceptType
    private String mDispatchTouchEventType;
    @InterceptType
    private String mOnTouchEventType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event_and_dispach_layout);
        mBottomGroup = findViewById(R.id.bottom_group);
        mBottomGroup.setOnTouchEventListener(this);
        mMiddleGroup = findViewById(R.id.middle_group);
        mMiddleGroup.setOnTouchEventListener(this);
        mTopTextView = findViewById(R.id.top_text_view);
        mTopTextView.setOnTouchEventListener(this);

        mButtonActivity1 = findViewById(R.id.button_activity_column_1);
        mButtonActivity2 = findViewById(R.id.button_activity_column_2);
        mButtonActivity3 = findViewById(R.id.button_activity_column_3);

        mButtonBottom1 = findViewById(R.id.button_bottom_column_1);
        mButtonBottom2 = findViewById(R.id.button_bottom_column_2);
        mButtonBottom3 = findViewById(R.id.button_bottom_column_3);

        mButtonMiddle1 = findViewById(R.id.button_middle_column_1);
        mButtonMiddle2 = findViewById(R.id.button_middle_column_2);
        mButtonMiddle3 = findViewById(R.id.button_middle_column_3);

        mButtonTop1 = findViewById(R.id.button_text_column_1);
        mButtonTop2 = findViewById(R.id.button_text_column_2);
        mButtonTop3 = findViewById(R.id.button_text_column_3);

        mSpinnerActivityOnTouchEvent = findViewById(R.id.spinner_activity_on_touch_event);
        mSpinnerActivityOnTouchEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setOnTouchEventType(mSelectArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerActivityDispatchTouchEvent = findViewById(R.id.spinner_activity_dispatch_touch_event);
        mSpinnerActivityDispatchTouchEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDispatchTouchEventType(mSelectArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerActivityOnInterceptTouchEvent = findViewById(R.id.spinner_activity_on_intercept_touch_event);
        mSpinnerActivityOnInterceptTouchEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerBottomOnTouchEvent = findViewById(R.id.spinner_bottom_on_touch_event);
        mSpinnerBottomOnTouchEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBottomGroup.setOnTouchEventType(mSelectArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerBottomDispatchTouchEvent = findViewById(R.id.spinner_bottom_dispatch_touch_event);
        mSpinnerBottomDispatchTouchEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBottomGroup.setDispatchTouchEventType(mSelectArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerBottomOnInterceptTouchEvent = findViewById(R.id.spinner_bottom_on_intercept_touch_event);
        mSpinnerBottomOnInterceptTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mBottomGroup.setOnInterceptTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        mSpinnerMiddleOnTouchEvent = findViewById(R.id.spinner_middle_on_touch_event);
        mSpinnerMiddleOnTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mMiddleGroup.setOnTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mSpinnerMiddleDispatchTouchEvent = findViewById(R.id.spinner_middle_dispatch_touch_event);
        mSpinnerMiddleDispatchTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mMiddleGroup.setDispatchTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mSpinnerMiddleOnInterceptTouchEvent = findViewById(R.id.spinner_middle_on_intercept_touch_event);
        mSpinnerMiddleOnInterceptTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mMiddleGroup.setOnInterceptTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        mSpinnerTopOnTouchEvent = findViewById(R.id.spinner_text_on_touch_event);
        mSpinnerTopOnTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mTopTextView.setOnTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mSpinnerTopDispatchTouchEvent = findViewById(R.id.spinner_text_dispatch_touch_event);
        mSpinnerTopDispatchTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mTopTextView.setDispatchTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
        mSpinnerTopOnInterceptTouchEvent = findViewById(R.id.spinner_text_on_intercept_touch_event);
        mSpinnerTopOnInterceptTouchEvent
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mBottomGroup.setDispatchTouchEventType(mSelectArray[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        touchResult(TouchEventFrom.FROM_ACTIVITY, ev.getAction(), TouchType.TYPE_DISPATCH_TOUCH_EVENT);
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
        touchResult(TouchEventFrom.FROM_ACTIVITY, event.getAction(), TouchType.TYPE_ON_TOUCH_EVENT);
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


    @Override
    public void touchResult(@TouchEventFrom String touchEventFrom, int touchEventAction, @TouchType String touchType) {
        if (TouchEventFrom.FROM_ACTIVITY.equals(touchEventFrom)) {
            if (TouchType.TYPE_ON_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonActivity1.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonActivity1.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonActivity1.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonActivity1.setText("UP");
                } else {
                    mButtonActivity1.setText("OTHER");
                }
            } else if (TouchType.TYPE_DISPATCH_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonActivity2.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonActivity2.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonActivity2.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonActivity2.setText("UP");
                } else {
                    mButtonActivity2.setText("OTHER");
                }
            } else if (TouchType.TYPE_ON_INTERCEPT_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonActivity3.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonActivity3.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonActivity3.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonActivity3.setText("UP");
                } else {
                    mButtonActivity3.setText("OTHER");
                }
            }
        } else if (TouchEventFrom.FROM_BOTTOM.equals(touchEventFrom)) {
            if (TouchType.TYPE_ON_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonBottom1.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonBottom1.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonBottom1.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonBottom1.setText("UP");
                } else {
                    mButtonBottom1.setText("OTHER");
                }
            } else if (TouchType.TYPE_DISPATCH_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonBottom2.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonBottom2.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonBottom2.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonBottom2.setText("UP");
                } else {
                    mButtonBottom2.setText("OTHER");
                }
            } else if (TouchType.TYPE_ON_INTERCEPT_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonBottom3.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonBottom3.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonBottom3.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonBottom3.setText("UP");
                } else {
                    mButtonBottom3.setText("OTHER");
                }
            }
        } else if (TouchEventFrom.FROM_MIDDLE.equals(touchEventFrom)) {
            if (TouchType.TYPE_ON_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonMiddle1.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonMiddle1.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonMiddle1.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonMiddle1.setText("UP");
                } else {
                    mButtonMiddle1.setText("OTHER");
                }
            } else if (TouchType.TYPE_DISPATCH_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonMiddle2.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonMiddle2.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonMiddle2.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonMiddle2.setText("UP");
                } else {
                    mButtonMiddle2.setText("OTHER");
                }
            } else if (TouchType.TYPE_ON_INTERCEPT_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonMiddle3.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonMiddle3.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonMiddle3.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonMiddle3.setText("UP");
                } else {
                    mButtonMiddle3.setText("OTHER");
                }
            }
        } else if (TouchEventFrom.FROM_TOP.equals(touchEventFrom)) {
            if (TouchType.TYPE_ON_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonTop1.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonTop1.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonTop1.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonTop1.setText("UP");
                } else {
                    mButtonTop1.setText("OTHER");
                }
            } else if (TouchType.TYPE_DISPATCH_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonTop2.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonTop2.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonTop2.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonTop2.setText("UP");
                } else {
                    mButtonTop2.setText("OTHER");
                }
            } else if (TouchType.TYPE_ON_INTERCEPT_TOUCH_EVENT.equals(touchType)) {
                if (MotionEvent.ACTION_DOWN == touchEventAction) {
                    mButtonTop3.setText("DOWN");
                } else if (MotionEvent.ACTION_MOVE == touchEventAction) {
                    mButtonTop3.setText("MOVE");
                } else if (MotionEvent.ACTION_CANCEL == touchEventAction) {
                    mButtonTop3.setText("CANCEL");
                } else if (MotionEvent.ACTION_UP == touchEventAction) {
                    mButtonTop3.setText("UP");
                } else {
                    mButtonTop3.setText("OTHER");
                }
            }
        }
    }

    public void addNumber(Button button) {
        String numberString = button.getText().toString();
        int number = Integer.parseInt(numberString);
        number = number + 1;
        button.setText(String.valueOf(number));
    }

    public void showEvent(Button button, String text) {
        button.setText(text);
    }
}
