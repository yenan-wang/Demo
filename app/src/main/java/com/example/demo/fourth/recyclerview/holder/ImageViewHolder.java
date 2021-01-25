package com.example.demo.fourth.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.demo.R;
import com.example.demo.fourth.recyclerview.data.ImageVO;
import com.ngb.common.utils.ImageUtil;

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
        switch (v.getId()) {
            case R.id.iv_cover_image:
                showDialog();
                break;
        }
    }

    private void initView(View view) {
        mImageView = view.findViewById(R.id.iv_cover_image);
        mImageView.setOnClickListener(this);
    }
}
