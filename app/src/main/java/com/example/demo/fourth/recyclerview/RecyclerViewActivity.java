package com.example.demo.fourth.recyclerview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demo.R;
import com.example.demo.fourth.recyclerview.adapter.DemoRecyclerAdapter;
import com.example.demo.fourth.recyclerview.data.CommodityVO;
import com.example.demo.fourth.recyclerview.data.ImageVO;
import com.example.demo.fourth.recyclerview.data.BuyersShowVO;
import com.ngb.common.BaseCommonActivity;
import com.ngb.common.ui.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseCommonActivity {
    private static final int DEFAULT_SPAN_COUNT = 2;
    private CommonToolBar mToolBar;
    private RecyclerView mRecyclerView;
    private DemoRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initData();
    }

    private void initView() {
        mToolBar = findViewById(R.id.tool_bar);
        mToolBar.setTitleText(R.string.button_4_3);
        mToolBar.setIconImageViewVisible(View.VISIBLE);
        mToolBar.setIconImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new DemoRecyclerAdapter();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, GridLayoutManager.DEFAULT_SPAN_COUNT);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(DEFAULT_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        List<RecyclerViewData> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BuyersShowVO buyersShowVO = new BuyersShowVO();
            buyersShowVO.setId("123456789");
            buyersShowVO.setStartTime(System.currentTimeMillis());
            buyersShowVO.setEndTime(System.currentTimeMillis() + 123456);
            buyersShowVO.setCoverUrl("http://5b0988e595225.cdn.sohucs.com/images/20180705/b6a1788c7afe485194d9b37a346751b6.jpeg");
            buyersShowVO.setAuthor("小南瓜");
            buyersShowVO.setPraiseNumber(1024);
            buyersShowVO.setTitle("我的买家秀");
            buyersShowVO.setAvatarUrl("https://scpic.chinaz.net/Files/pic/icons128/7967/g3.png");
            RecyclerViewData<BuyersShowVO> videoVORecyclerViewData = new RecyclerViewData<>();
            videoVORecyclerViewData.setType(RecyclerViewConstants.DemoDataType.TYPE_BUYERS_SHOW);
            videoVORecyclerViewData.setData(buyersShowVO);
            dataList.add(videoVORecyclerViewData);

            CommodityVO commodityVO = new CommodityVO();
            commodityVO.setId("987654321");
            commodityVO.setStartTime(System.currentTimeMillis());
            commodityVO.setEndTime(System.currentTimeMillis() + 123456);
            commodityVO.setCommodityTitle("海边独家圣地");
            commodityVO.setImageUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201304%2F25%2F195133e7a1l7b4f5117y4y.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614086536&t=79b9a4c5f0d12a69cda2d7c939fd0b5e");
            commodityVO.setPaymentCount(654);
            commodityVO.setPrice(1234.56);
            List<String> commodityLabelList = new ArrayList<>();
            commodityLabelList.add("hot");
            commodityLabelList.add("天猫");
            commodityVO.setTitleLabelContent(commodityLabelList);
            RecyclerViewData<CommodityVO> commodityVORecyclerViewData = new RecyclerViewData<>();
            commodityVORecyclerViewData.setType(RecyclerViewConstants.DemoDataType.TYPE_COMMODITY);
            commodityVORecyclerViewData.setData(commodityVO);
            dataList.add(commodityVORecyclerViewData);

            ImageVO imageVO = new ImageVO();
            imageVO.setId("789456123");
            imageVO.setStartTime(System.currentTimeMillis());
            imageVO.setEndTime(System.currentTimeMillis() + 123456);
            imageVO.setImageUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattimg.dospy.com%2Fimg%2Fday_130714%2F20130714_8e0364d59a6517df692d9ZfWsiII0w8W.jpg&refer=http%3A%2F%2Fattimg.dospy.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614088588&t=2db8c10e8b6287e101b516061127a1cf");
            RecyclerViewData<ImageVO> imageVORecyclerViewData = new RecyclerViewData<>();
            imageVORecyclerViewData.setType(RecyclerViewConstants.DemoDataType.TYPE_IMAGE);
            imageVORecyclerViewData.setData(imageVO);
            dataList.add(imageVORecyclerViewData);
        }
        mAdapter.updateData(dataList);
        mAdapter.notifyDataSetChanged();
    }
}
