package com.example.demo.main.fourth.recyclerview.holder;

import android.app.Dialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.main.R;
import com.ngb.common.utils.ToastUtil;

public abstract class DemoBaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public DemoBaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(T dataVO);

    public void showDialog() {
        ToastUtil.toastLong(itemView.getContext().getResources().getString(R.string.button_4_3));
        Dialog dialog = new Dialog(itemView.getContext());
        dialog.setContentView(itemView);
        dialog.show();
    }
}
