package com.example.demo.main.second.viewpager2.fragment.one;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.main.R;

import java.util.ArrayList;
import java.util.List;

public class OneFragmentAdapter extends RecyclerView.Adapter<OneFragmentViewHolder> implements Filterable {

    private List<TextData> mTextDataList = new ArrayList<>();
    private List<TextData> mFilterDataList = new ArrayList<>();

    @NonNull
    @Override
    public OneFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OneFragmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_text_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OneFragmentViewHolder holder, int position) {
        holder.bindData(mTextDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTextDataList.size();
    }

    public void addData(TextData data) {
        mTextDataList.add(data);
        notifyItemChanged(mTextDataList.size() - 1);
    }

    public void addDataList(List<TextData> list) {
        int size = mTextDataList.size();
        mTextDataList.addAll(list);
        notifyItemRangeChanged(size, list.size());
    }

    @Override
    public Filter getFilter() {
        /*return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };*/
        return null;
    }
}
