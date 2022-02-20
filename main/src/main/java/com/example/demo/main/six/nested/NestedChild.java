package com.example.demo.main.six.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.common.R;

public class NestedChild extends FrameLayout {
    private float mLastActionX = 0;
    private float mLastActionY = 0;
    private int[] mConsume = new int[2];
    private int[] mOffWindow = new int[2];
    private VelocityTracker mVelocityTracker;

    public NestedChild(Context context) {
        this(context, null);
    }

    public NestedChild(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NestedChild(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addView(recyclerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
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

                if (dispatchNestedPreScroll(deltaX, deltaY, mConsume, mOffWindow)) {
                    //deltaX -= mConsume[0];
                    deltaY -= mConsume[1];
                }
                /*if (mConsume[1] != 0) {
                    scrollBy(0, -deltaY);
                }*/

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


                if (dispatchNestedScroll(0, consumeY, 0, (deltaY - consumeY), mOffWindow)) {
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
        return true;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_text_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setText("文字啦啦啦啦" + position);
    }

    @Override
    public int getItemCount() {
        return 80;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setText(String s) {
        TextView textView = itemView.findViewById(R.id.text_view);
        textView.setText(s);
    }
}