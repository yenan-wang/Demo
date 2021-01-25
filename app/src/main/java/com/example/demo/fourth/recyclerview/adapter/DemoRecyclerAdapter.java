package com.example.demo.fourth.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.fourth.recyclerview.RecyclerViewConstants;
import com.example.demo.fourth.recyclerview.RecyclerViewData;
import com.example.demo.fourth.recyclerview.data.DemoDataType;
import com.example.demo.fourth.recyclerview.holder.DemoBaseRecyclerViewHolder;
import com.example.demo.fourth.recyclerview.holder.ImageViewHolder;
import com.example.demo.fourth.recyclerview.holder.CommodityViewHolder;
import com.example.demo.fourth.recyclerview.holder.BuyersShowVOViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DemoRecyclerAdapter extends RecyclerView.Adapter<DemoBaseRecyclerViewHolder> {
    private List<RecyclerViewData> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public DemoBaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @DemoDataType int viewType) {
        DemoBaseRecyclerViewHolder viewHolder = null;
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case RecyclerViewConstants.DemoDataType.TYPE_BUYERS_SHOW:
                view = inflater.inflate(R.layout.recycler_view_type_buyers_show_layout, parent, false);
                viewHolder = new BuyersShowVOViewHolder(view);
                break;
            case RecyclerViewConstants.DemoDataType.TYPE_IMAGE:
                view = inflater.inflate(R.layout.recycler_view_type_image_layout, parent, false);
                viewHolder = new ImageViewHolder(view);
                break;
            case RecyclerViewConstants.DemoDataType.TYPE_COMMODITY:
                view = inflater.inflate(R.layout.recycler_view_type_commodity_layout, parent, false);
                viewHolder = new CommodityViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DemoBaseRecyclerViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull DemoBaseRecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.bindData(mDataList.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType();
    }

    public void addData(List<RecyclerViewData> dataList) {
        mDataList.addAll(dataList);
    }

    public void updateData(List<RecyclerViewData> dataList) {
        mDataList.clear();
        addData(dataList);
    }
}
