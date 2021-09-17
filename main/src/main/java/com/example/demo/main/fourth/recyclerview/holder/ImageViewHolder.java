package com.example.demo.main.fourth.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.demo.main.R;
import com.example.demo.main.fourth.recyclerview.data.ImageVO;

public class ImageViewHolder extends DemoBaseRecyclerViewHolder<ImageVO> {
    private ImageView mImageView;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    @Override
    public void bindData(ImageVO imageVO) {
        //ImageUtil.getInstance().loadImageSimple(itemView.getContext(), imageVO.getImageUrl(), mImageView);
        mImageView.setImageResource(R.drawable.demo_gugong);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cover_image) {
            showDialog();
        }
    }

    private void initView(View view) {
        mImageView = view.findViewById(R.id.iv_cover_image);
        mImageView.setOnClickListener(this);
    }
}
