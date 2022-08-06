package com.example.demo.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.demo.common.R;

public class DialogUtil {

    public static AlertDialog createDialog(
            Context context,
            String content,
            DialogInterface.OnClickListener negativeListener,
            DialogInterface.OnClickListener positiveListener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        if (negativeListener != null) {
            builder.setNegativeButton(context.getText(R.string.cancel), negativeListener);
        }
        if (positiveListener != null) {
            builder.setPositiveButton(context.getText(R.string.confirm), positiveListener);
        }
        builder.setMessage(content);
        return builder.create();
    }
}
