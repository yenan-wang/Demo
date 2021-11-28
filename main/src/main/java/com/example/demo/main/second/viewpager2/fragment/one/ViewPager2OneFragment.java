package com.example.demo.main.second.viewpager2.fragment.one;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.main.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2OneFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private OneFragmentAdapter mAdapter;

    public static ViewPager2OneFragment newInstance() {
        return new ViewPager2OneFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager_one_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSearchView = view.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    mAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        mAdapter = new OneFragmentAdapter();
        mAdapter.addDataList(getListData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<TextData> getListData() {
        List<TextData> list = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            TextData data1 = new TextData();
            data1.mId = (i * 4) - 4;
            data1.mText = "https://www.cctv.com";
            data1.mTime = System.nanoTime();
            list.add(data1);

            TextData data2 = new TextData();
            data2.mId = (i * 4) - 3;
            data2.mText = "https://www.kuaidi100.com";
            data2.mTime = System.nanoTime();
            list.add(data2);

            TextData data3 = new TextData();
            data3.mId = (i * 4) - 2;
            data3.mText = "https://www.hfut.edu.cn";
            data3.mTime = System.nanoTime();
            list.add(data3);

            TextData data4 = new TextData();
            data4.mId = (i * 4) - 1;
            data4.mText = "https://www.oppo.com/cn";
            data4.mTime = System.nanoTime();
            list.add(data4);
        }

        return list;
    }
}