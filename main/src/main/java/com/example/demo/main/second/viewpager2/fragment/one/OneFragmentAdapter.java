package com.example.demo.main.second.viewpager2.fragment.one;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.main.R;
import com.ngb.wyn.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class OneFragmentAdapter extends RecyclerView.Adapter<OneFragmentViewHolder> implements Filterable {

    private List<TextData> mTextDataList = new ArrayList<>();
    private List<TextData> mFilterDataList = new ArrayList<>();
    private boolean mIsSearch = false;

    @NonNull
    @Override
    public OneFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OneFragmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_text_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OneFragmentViewHolder holder, int position) {
        holder.bindData(mIsSearch ? mFilterDataList.get(position) : mTextDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mIsSearch ? mFilterDataList.size() : mTextDataList.size();
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
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mIsSearch = false;
                    filterResults.values = mTextDataList;
                    mFilterDataList.clear();
                    return filterResults;
                } else {
                    if (!mIsSearch) {
                        mFilterDataList = mTextDataList;
                        mIsSearch = true;
                    }
                }

                List<TextData> filterList = new ArrayList<>();
                ListIterator<TextData> iterator = mTextDataList.listIterator();
                while (iterator.hasNext()) {
                    TextData data = iterator.next();
                    if (data.mText.contains(charString)) {
                        filterList.add(data);
                    }
                }
                mFilterDataList = filterList;
                filterResults.values = mFilterDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<TextData> list = (List<TextData>) results.values;
                if (list.isEmpty()) {
                    ToastUtil.toastShort("无搜索结果");
                    notifyItemRangeRemoved(0, mTextDataList.size());
                } else {
                    notifyDataSetChanged();
                }

            }
        };
        //return null;
    }
}
