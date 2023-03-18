package com.example.demo.main.six.toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.main.R;
import com.ngb.wyn.common.ui.CommonButton;
import com.ngb.wyn.common.utils.AppUtil;
import com.ngb.wyn.common.utils.ToastUtil;

public class ToastActivity extends AppCompatActivity implements View.OnClickListener {

    private CommonButton mButtonPopWindow;
    private CommonButton mButtonSysToast;
    private CommonButton mButtonCustomToast;
    private CommonButton mButtonCustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        mButtonPopWindow = findViewById(R.id.pop_window);
        mButtonPopWindow.setOnClickListener(this);
        mButtonSysToast = findViewById(R.id.sys_toast);
        mButtonSysToast.setOnClickListener(this);
        mButtonCustomToast = findViewById(R.id.custom_toast);
        mButtonCustomToast.setOnClickListener(this);
        mButtonCustomDialog = findViewById(R.id.custom_dialog);
        mButtonCustomDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pop_window) {
            popWindow();
        } else if (id == R.id.sys_toast) {
            ToastUtil.toastView(inflateLayout(LayoutInflater.from(this)), Toast.LENGTH_LONG);
        } else if (id == R.id.custom_toast) {
            customToast();
        } else if (id == R.id.custom_dialog) {
            customDialog();
        }
    }

    private void popWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(inflateLayout(LayoutInflater.from(this)));
        popupWindow.showAsDropDown(mButtonPopWindow);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 3000);
    }

    private void customToast() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflateLayout(inflater);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        if (Settings.canDrawOverlays(this)) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            ToastUtil.toastLong("请打开应用浮窗权限.");
        }
        params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //params.gravity = Gravity.BOTTOM;
        params.verticalMargin = 0.4F;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(layoutView, params);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //layoutView.animate().alpha(0);
                windowManager.removeView(layoutView);
            }
        }, 3000);
    }

    private void customDialog() {
        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(this);
        AlertDialog dialog = mBuilder.create();
        dialog.setView(inflateLayout(LayoutInflater.from(this)));
        if (Settings.canDrawOverlays(this)) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            ToastUtil.toastLong("请打开应用浮窗权限.");
        }

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setDimAmount(0);
        dialog.getWindow().getAttributes().verticalMargin = 0.3F;
        dialog.getWindow().getAttributes().flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }, 3000);
    }

    private void openPage() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("oaps://soloop/home"));
        intent.setPackage("com.coloros.videoeditor");
        startActivity(intent);
    }

    private View inflateLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_toast_layout, null);
        TextView content = view.findViewById(R.id.content);
        String pkgName = "com.coloros.videoeditor";
        //String pkgName = "com.tencent.mm";
        content.setText(getString(R.string.install_tip, AppUtil.getAppName(view.getContext(), pkgName)));
        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageDrawable(AppUtil.getAppIcon(view.getContext(), pkgName));
        CommonButton commonButton = view.findViewById(R.id.open_button);
        /*commonButton.setFocusable(true);

        commonButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtil.d("点击touch");
                Rect rect = new Rect();
                v.getDrawingRect(rect);
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = rect.right + location[0];
                rect.bottom = rect.bottom + location[1];
                if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    openPage();
                }
                return true;
            }
        });*/
        commonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage();
            }
        });
        return view;
    }
}