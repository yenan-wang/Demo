package com.example.demo.main.second.viewpager2.fragment.one;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.main.R;

public class OneFragmentViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public OneFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text_view);
    }

    public void bindData(TextData data, int pos) {
        mTextView.setText(data.mText);
    }
}
