package com.example.demo.main.customview;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.main.R;
import com.google.android.material.tabs.TabLayout;
import com.example.demo.common.BaseCommonActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomViewActivity extends BaseCommonActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Handler mHandler;
    private List<String> mTabNameList;
    private List<Fragment> mFragmentsList;
    private CustomViewPagerAdapter mCustomViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        initData();
        initView();
    }

    private void initData() {
        mTabNameList = new ArrayList<>();
        mTabNameList.add("基础图形");
        mTabNameList.add("基础二维图形");
        mTabNameList.add("……");

        mFragmentsList = new ArrayList<>();
        mFragmentsList.add(new CustomViewFragment());
        mFragmentsList.add(new CustomViewFragment());
        mFragmentsList.add(new CustomViewMoreFragment());
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mCustomViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabNameList.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_title_layout, null);
            textView.setText(mTabNameList.get(i));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(textView));
        }
        mViewPager.setAdapter(mCustomViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class CustomViewPagerAdapter extends FragmentPagerAdapter {

        public CustomViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return mTabNameList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNameList.get(position);
        }
    }
}
