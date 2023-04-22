package com.example.demo.main.fifth.ballslide;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.demo.common.BaseActivity;
import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.ToastUtil;

public class BallSlidingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_sliding);
        initView();
    }

    private void initView() {
        EditText textView = findViewById(R.id.angel_text);
        textView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        RunBall runBall = findViewById(R.id.run_ball);
        CommonButton setAngel = findViewById(R.id.set_angel);
        setAngel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double angel = RunBall.DEFAULT_ANGEL;
                try {
                    angel = Double.parseDouble(textView.getText().toString());
                } catch (NumberFormatException e) {
                    ToastUtil.toastLong("请输入正确的角度数！");
                }
                runBall.setAngel(angel);
            }
        });

        CommonButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBall.moveBack();
            }
        });
    }

}