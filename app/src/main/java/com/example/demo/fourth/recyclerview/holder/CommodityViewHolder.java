package com.example.demo.fourth.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo.R;
import com.example.demo.fourth.recyclerview.data.CommodityVO;
import com.ngb.common.utils.ImageUtil;
import com.ngb.common.utils.ToastUtil;

public class CommodityViewHolder extends DemoBaseRecyclerViewHolder<CommodityVO> {
    private ImageView mCoverImage;
    private TextView mLabelText;
    private TextView mTitleText;
    private TextView mPrice;
    private TextView mPayCount;
    private ImageView mMoreImage;

    public CommodityViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    @Override
    public void bindData(CommodityVO commodityVO) {
        //ImageUtil.getInstance().loadImageSimple(itemView.getContext(), commodityVO.getImageUrl(), mCoverImage);
        mCoverImage.setImageResource(R.drawable.demo_scenery);
        mLabelText.setText(commodityVO.getTitleLabelContent().get(0));
        mTitleText.setText(commodityVO.getCommodityTitle());
        String price = itemView.getContext().getResources().getString(R.string.money) + commodityVO.getPrice();
        mPrice.setText(price);
        mPayCount.setText(itemView.getContext().getResources().getQuantityString(R.plurals.pay_person_count, commodityVO.getPaymentCount(), commodityVO.getPaymentCount()));
        mMoreImage.setOnClickListener(this);
    }

    private void initView(View view) {
        mCoverImage = view.findViewById(R.id.iv_cover_image);
        mLabelText = view.findViewById(R.id.tv_title_label);
        mTitleText = view.findViewById(R.id.tv_commodity_title);
        mPrice = view.findViewById(R.id.tv_price);
        mPayCount = view.findViewById(R.id.tv_pay_count);
        mMoreImage = view.findViewById(R.id.iv_more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                ToastUtil.toastLong(itemView.getContext().getResources().getString(R.string.button_4_3));
                break;
            case R.id.iv_cover_image:
                showDialog();
                break;
        }
    }
}
