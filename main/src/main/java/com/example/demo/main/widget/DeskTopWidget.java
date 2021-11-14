package com.example.demo.main.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.demo.main.R;
import com.example.demo.common.utils.AppUtil;
import com.example.demo.common.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class DeskTopWidget extends AppWidgetProvider {

    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        LogUtil.d("onReceive.");
        RemoteViews rv = new RemoteViews(AppUtil.getAppPackageName(AppUtil.getContext()), R.layout.my_app_widget);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINESE);
        Date date = new Date(System.currentTimeMillis());
        rv.setTextViewText(R.id.time_text, "现在时间：" + dateFormat.format(date));
        //这里获得当前的包名，并且用AppWidgetManager来向DeskTopWidget.class发送广播。
        AppWidgetManager manager = AppWidgetManager.getInstance(AppUtil.getContext());
        ComponentName cn = new ComponentName(AppUtil.getContext(), DeskTopWidget.class);
        manager.updateAppWidget(cn, rv);
    }

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtil.d("onUpdate.");
        Intent intent = new Intent();
        intent.setClass(context, DeskTopWidget.class);
        intent.setData(Uri.parse("update://timer"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);
        RemoteViews rv = new RemoteViews(AppUtil.getAppPackageName(AppUtil.getContext()), R.layout.my_app_widget);
        rv.setOnClickPendingIntent(R.id.time_text, pendingIntent);
        rv.setTextViewText(R.id.time_text, "现在时间：" + Date.from(Instant.now()));
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        LogUtil.d("onEnabled.");

    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        LogUtil.d("onDisabled.");
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        LogUtil.d("onDeleted.");
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        LogUtil.d("onRestored.");
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        LogUtil.d("ngb_log", "onAppWidgetOptionsChanged.");
    }
}
