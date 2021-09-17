package com.example.demo.main.second;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.demo.main.MainActivity;
import com.example.demo.main.R;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.CommonConstants;
import com.example.demo.common.ui.CommonButton;
import com.example.demo.common.utils.ToastUtil;

public class NotificationActivity extends BaseCommonActivity implements View.OnClickListener {
    private static final String CHANNEL_ID_COMMON_MESSAGE = "common_message";
    private static final String CHANNEL_ID_FOLD_MESSAGE = "fold_message";
    private static final String CHANNEL_ID_SUSPEND_MESSAGE = "suspend_message";

    private static int REQUEST_CODE_COMMON = 0;
    private static int REQUEST_CODE_FOLD = 1;
    private static int REQUEST_CODE_SUSPEND = 2;
    private CommonButton mCommonMessageButton;
    private CommonButton mFoldMessageButton;
    private CommonButton mSuspendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        mCommonMessageButton = findViewById(R.id.common_notice_button);
        mCommonMessageButton.setOnClickListener(this);
        mFoldMessageButton = findViewById(R.id.fold_notice_button);
        mFoldMessageButton.setOnClickListener(this);
        mSuspendMessageButton = findViewById(R.id.suspend_notice_button);
        mSuspendMessageButton.setOnClickListener(this);
    }

    private void popCommonNoticeButton() {
        //获取系统消息服务
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //设置消息点击跳转
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_COMMON, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //添加消息渠道
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_COMMON_MESSAGE, CHANNEL_ID_COMMON_MESSAGE, NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(channel);
        //构建消息
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_COMMON_MESSAGE);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icon_app);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app));
        builder.setAutoCancel(true);
        builder.setContentTitle(getString(R.string.common_message));
        builder.setContentText(getString(R.string.common_message));
        builder.setWhen(System.currentTimeMillis());
        //显示消息
        manager.notify(REQUEST_CODE_COMMON, builder.build());
    }

    private void popFoldNoticeButton() {
        //自定义布局
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.fold_message_layout);
        //获取系统消息服务
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //添加消息渠道
        manager.createNotificationChannel(new NotificationChannel(CHANNEL_ID_FOLD_MESSAGE, CHANNEL_ID_FOLD_MESSAGE, NotificationManager.IMPORTANCE_LOW));
        //构建消息
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_FOLD_MESSAGE)
                .setContentIntent(PendingIntent.getActivity(this, REQUEST_CODE_FOLD, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon_app)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app))
                .setCustomContentView(remoteViews)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.fold_message))
                .setContentText(getText(R.string.fold_message))
                .build();
        //显示消息
        manager.notify(REQUEST_CODE_FOLD, notification);

    }

    private void popSuspendNoticeButton() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_SUSPEND_MESSAGE, CHANNEL_ID_SUSPEND_MESSAGE, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(CommonConstants.URL_MY_CSDN_BLOG));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_SUSPEND, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_SUSPEND_MESSAGE);
        builder.setAutoCancel(true)
                .setContentText(getString(R.string.click_to_my_CSDN_blog))
                .setContentTitle(getString(R.string.suspend_message))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app))
                .setSmallIcon(R.drawable.icon_app)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Notification notification = builder.build();
        manager.notify(REQUEST_CODE_SUSPEND, notification);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_notice_button) {
            popCommonNoticeButton();
        } else if (id == R.id.fold_notice_button) {
            popFoldNoticeButton();
        } else if (id == R.id.suspend_notice_button) {
            popSuspendNoticeButton();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                ToastUtil.toastLong(getResources().getString(R.string.common_message));
                break;
            case 1:
                ToastUtil.toastLong(getResources().getString(R.string.fold_message));
                break;
            case 2:
                ToastUtil.toastLong(getResources().getString(R.string.suspend_message));
                break;
            default:
                break;
        }
    }
}