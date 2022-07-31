package com.example.demo.common.ui.basics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.demo.common.utils.LogUtil;
import com.example.demo.common.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://mp.weixin.qq.com/s/X-Je_KYZJHV2h0-YbiQaQg
 */
public class Bihua extends View {

    private static final float SVG_STROKE_WIDTH = 1024F;
    private static final float SVG_STROKE_HEIGHT = 1024F;

    private List<Path> mStrokePaths = new ArrayList<>();
    private List<Path> mMediansPaths = new ArrayList<>();
    private List<PathMeasure> mMediansPathMeasures = new ArrayList<>();
    private Paint mStrokePaint;
    private Paint mMediansPaint;
    private Path mTempPath;
    private float mProgress = 0;
    private int mIndex = -1;
    private float mWidth = SVG_STROKE_WIDTH;
    private float mHeight = SVG_STROKE_HEIGHT;

    public Bihua(Context context) {
        this(context, null);
    }

    public Bihua(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bihua(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Bihua(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = SVG_STROKE_WIDTH;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = SVG_STROKE_HEIGHT;
        }
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //源数据绘制出来是垂直翻转的，所以需要垂直镜像翻一下
        canvas.scale(1F, -1F);
        //因为垂直镜像翻转了，所以垂直方向要移动回来，汉字上下有边，一般为字体高度的 1/8
        canvas.translate(0, -SVG_STROKE_HEIGHT * 7 / 8);
        //按控件大小等比缩放汉字，注意默认(0, 0)左下缩，现在要求左上缩
        canvas.scale(mWidth / SVG_STROKE_WIDTH, mHeight / SVG_STROKE_HEIGHT, 0F, SVG_STROKE_HEIGHT * 7 / 8);
        for (Path path : mStrokePaths) {
            canvas.drawPath(path, mStrokePaint);
        }
        for (int i = 0; i < mMediansPathMeasures.size(); i++) {
            mTempPath.reset();
            mTempPath.lineTo(0, 0);
            PathMeasure pathMeasure = mMediansPathMeasures.get(i);
            float progress = mProgress;
            if (i < mIndex) {
                progress = 1;
            } else if (i > mIndex) {
                progress = 0;
            }
            pathMeasure.getSegment(0, pathMeasure.getLength() * progress, mTempPath, true);
            canvas.drawPath(mTempPath, mMediansPaint);
        }
    }

    public boolean parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return false;
        }

        mStrokePaths.clear();
        mMediansPaths.clear();
        mMediansPathMeasures.clear();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray strokePathArrays = jsonObject.optJSONArray("strokes");
            if (strokePathArrays == null) {
                return false;
            }
            for (int i = 0; i < strokePathArrays.length(); i++) {
                mStrokePaths.add(PathParser.createPathFromPathData(strokePathArrays.getString(i)));
            }
            JSONArray medianArrays = jsonObject.optJSONArray("medians");
            if (medianArrays == null) {
                return false;
            }
            //mediaArrays长度，其实就是笔划的个数
            for (int i = 0; i < medianArrays.length(); i++) {
                JSONArray pathsArrays = medianArrays.optJSONArray(i);
                Path path = new Path();
                for (int j = 0; j < pathsArrays.length(); j++) {
                    JSONArray point = pathsArrays.optJSONArray(j);
                    float x = point.getInt(0);
                    float y = point.getInt(1);
                    //起笔，要移动画笔
                    if (j == 0) {
                        path.moveTo(x, y);
                    } else {
                        path.lineTo(x, y);
                    }
                }
                mMediansPathMeasures.add(new PathMeasure(path, false));
                mMediansPaths.add(path);
            }
            boolean isParseSuccess = hasParse();
            if (isParseSuccess) {
                invalidate();
            }
            LogUtil.d("parse, isParseSuccess:" + isParseSuccess);
            return isParseSuccess;
        } catch (JSONException e) {
            LogUtil.e("parse, " + e.getMessage());
            return false;
        }
    }

    public void play() {
        if (mStrokePaths.isEmpty()) {
            ToastUtil.toastLong("数据为空，播放失败！");
            return;
        }
        ToastUtil.toastLong("开始播放！");
        startAnimator();
    }

    public boolean hasParse() {
        return !mStrokePaths.isEmpty() && !mMediansPaths.isEmpty();
    }

    private void init() {
        mStrokePaint = new Paint();
        mStrokePaint.setColor(Color.RED);
        mStrokePaint.setAntiAlias(true);
        mMediansPaint = new Paint();
        mMediansPaint.setColor(Color.GREEN);
        mMediansPaint.setAntiAlias(true);
        mMediansPaint.setStyle(Paint.Style.STROKE);
        mMediansPaint.setStrokeWidth(20);
        mTempPath = new Path();
    }

    private AnimatorSet createAnimator() {
        AnimatorSet set = new AnimatorSet();
        final List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < mMediansPaths.size(); i++) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mIndex = animators.indexOf(animation);
                    LogUtil.d("index = " + mIndex);
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
            animator.setDuration(750);
            animators.add(animator);
        }
        set.playSequentially(animators);
        return set;
    }

    private void startAnimator() {
        AnimatorSet set = createAnimator();
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
        set.start();
    }
}
