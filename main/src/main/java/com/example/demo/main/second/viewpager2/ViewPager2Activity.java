package com.example.demo.main.second.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.demo.common.BaseActivity;
import com.example.demo.main.R;

import com.example.demo.main.second.viewpager2.fragment.one.ViewPager2OneFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends BaseActivity {
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTabTitle = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager2_activity);
        initData();
        initView();
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewPager2Fragment.newInstance())
                    .commitNow();
        }*/
    }

    private void initData() {
        mTabTitle.add("tab1");
        mTabTitle.add("tab2");
        mTabTitle.add("tab3");

        mFragments.add(new ViewPager2OneFragment());
        mFragments.add(new ViewPager2OneFragment());
        mFragments.add(new ViewPager2OneFragment());
    }
    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager2);
        mViewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getItemCount() {
                return mTabTitle.size();
            }
        });
        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTabTitle.get(position));
            }
        }).attach();
    }
}