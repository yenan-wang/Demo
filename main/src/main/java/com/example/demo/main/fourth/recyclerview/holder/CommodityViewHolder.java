package com.example.demo.main.fourth.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo.main.R;
import com.example.demo.main.fourth.recyclerview.data.CommodityVO;
import com.ngb.wyn.common.utils.ToastUtil;

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
        int id = v.getId();
        if (id == R.id.iv_more) {
            ToastUtil.toastLong(itemView.getContext().getResources().getString(R.string.button_4_3));
        } else if (id == R.id.iv_cover_image) {
            showDialog();
        }
    }
}
