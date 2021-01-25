package com.example.demo.fourth.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo.R;
import com.example.demo.fourth.recyclerview.data.BuyersShowVO;
import com.ngb.common.utils.ImageUtil;

public class BuyersShowVOViewHolder extends DemoBaseRecyclerViewHolder<BuyersShowVO> {
    private boolean mIsLike;
    private ImageView mCoverImage;
    private ImageView mAvatarImage;
    private ImageView mLikeImage;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mPraiseText;

    public BuyersShowVOViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    @Override
    public void bindData(BuyersShowVO buyersShowVO) {
        /*ImageUtil.getInstance().loadImageSimple(itemView.getContext(), buyersShowVO.getCoverUrl(), mCoverImage);
        ImageUtil.getInstance().loadImageSimple(itemView.getContext(), buyersShowVO.getAvatarUrl(), mAvatarImage);*/
        mCoverImage.setImageResource(R.drawable.demo_person);
        mAvatarImage.setImageResource(R.drawable.icon_app);
        mTitleText.setText(buyersShowVO.getTitle());
        mAuthorText.setText(buyersShowVO.getAuthor());
        mPraiseText.setText(String.valueOf(buyersShowVO.getPraiseNumber()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_praise_count:
                if (!mIsLike) {
                    mIsLike = true;
                    mLikeImage.setImageResource(R.drawable.like_fill);
                } else {
                    mIsLike = false;
                    mLikeImage.setImageResource(R.drawable.like);
                }
                break;
            case R.id.iv_more:
                showDialog();
                break;
        }
    }

    private void initView(View view) {
        mCoverImage = view.findViewById(R.id.iv_cover_image);
        mAvatarImage = view.findViewById(R.id.iv_avatar);
        mLikeImage = view.findViewById(R.id.iv_like);
        mTitleText = view.findViewById(R.id.tv_buyers_title);
        mAuthorText = view.findViewById(R.id.tv_author);
        mPraiseText = view.findViewById(R.id.tv_praise_count);
    }
}
