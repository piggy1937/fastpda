package com.step.pda.ec.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.step.pda.app.Pda;
import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.refresh.PagingBean;
import com.step.pda.ec.adapter.BigPackRecyclerAdapter;
import com.step.pda.ec.database.bean.BigPackItem;
import com.step.pda.ec.database.bean.ExpandableBigPack;

import java.util.ArrayList;
import java.util.List;

public class BigPackRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener  {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private BigPackRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final Context CONTEXT;

    private BigPackRefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                             RecyclerView recyclerView,
                             DataConverter converter, PagingBean bean,Context context) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        this.CONTEXT = context;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static BigPackRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                               RecyclerView recyclerView, DataConverter converter, Context context) {
        return new BigPackRefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean(),context);
    }


    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Pda.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    public void firstPage() {
        List<ExpandableBigPack> list= new ArrayList<ExpandableBigPack>();
        ExpandableBigPack expandEntity = null;
        BigPackItem bigPackItem = null;
       for(int i=1;i<20;i++){
           expandEntity = new ExpandableBigPack();
           expandEntity.setExpanded(false);
           expandEntity.setSn("2019726001"+i);
           expandEntity.setCustomerSn("C1001");
           expandEntity.setCustomerName("Kindey");
           expandEntity.setWorkOrderSn("510120110620004");
           expandEntity.setCustomerOrderSn("kideny-001");

           expandEntity.setSubItems(new ArrayList<BigPackItem>());
           for(int j=0;j<i;j++){
               bigPackItem = new BigPackItem();
               bigPackItem.setProductSn("1000"+j);
               expandEntity.getSubItems().add(bigPackItem);
           }

           list.add(expandEntity);
       }
        mAdapter = BigPackRecyclerAdapter.create(CONVERTER.setItems(list));
        RECYCLERVIEW.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, RECYCLERVIEW);
    }
}
