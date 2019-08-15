package com.step.pda.ec.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.step.pda.app.Pda;
import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.refresh.PagingBean;
import com.step.pda.ec.adapter.BigPackRecyclerAdapter;
import com.step.pda.ec.database.BigPack;
import com.step.pda.ec.database.bean.BigPackItem;
import com.step.pda.ec.database.bean.ExpandableBigPack;
import com.step.pda.ec.services.BigPackService;

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
    private BigPackService bigPackService;
    private boolean refreshFlag=false;
    private BigPackRefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                             RecyclerView recyclerView,
                             DataConverter converter, PagingBean bean,Context context) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        this.CONTEXT = context;
        bigPackService = new BigPackService();
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
                refreshFlag = true;
                BigPackService bigPackService = new BigPackService();
                mAdapter.getData().clear();
                BEAN.setTotal((int)bigPackService.total());
                BEAN.setPageIndex(0);
                BEAN.setCurrentCount(0);
                paging();
                refreshFlag = false;
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        paging();
    }

    public void firstPage() {
        BigPackService bigPackService = new BigPackService();
        long total=bigPackService.total();

        BEAN.setDelayed(1000);
        BEAN.setTotal((int)total).setPageSize(20);
        List<BigPack> results = bigPackService.findList(0,BEAN.getPageSize());
        List<ExpandableBigPack> list= new ArrayList<ExpandableBigPack>();
        ExpandableBigPack expandEntity = null;
         List<BigPackItem> bigPackItems =null;
       for(BigPack entity:results){
           bigPackItems = new ArrayList<>();
           expandEntity = new ExpandableBigPack();
           expandEntity.setExpanded(false);
           expandEntity.setSn(entity.getCustomerSn());
           expandEntity.setCustomerSn(entity.getCustomerSn());
           expandEntity.setCustomerName(entity.getCustomerName());
           expandEntity.setWorkOrderSn(entity.getWordOrderSn());
           expandEntity.setCustomerOrderSn(entity.getCustomerOrderSn());
           expandEntity.setTag(entity.getTag());
           List<com.step.pda.ec.database.BigPackItem> subItems= bigPackService.findItemByPid(entity.getId());
           for(com.step.pda.ec.database.BigPackItem item:subItems){
               bigPackItems.add(new BigPackItem(item.getId(),item.getProductSn(),item.getTag()) );
           }
           expandEntity.setSubItems(bigPackItems);


           list.add(expandEntity);
       }
        mAdapter = BigPackRecyclerAdapter.create(CONVERTER.setItems(list));
        RECYCLERVIEW.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, RECYCLERVIEW);
        BEAN.setCurrentCount(mAdapter.getData().size());
        BEAN.addIndex();
    }

    /**
     * 分页
     */
    private void paging() {
        BigPackService bigPackService = new BigPackService();

        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();
        if (!refreshFlag &&(mAdapter.getData().size() < pageSize || currentCount >= total)) {
            mAdapter.loadMoreEnd(true);
        } else {
            CONVERTER.clearData();
            List<BigPack> packageInfoList = bigPackService.findList(index * pageSize, BEAN.getPageSize());


            List<ExpandableBigPack> list= new ArrayList<ExpandableBigPack>();
            ExpandableBigPack expandEntity = null;
            List<BigPackItem> bigPackItems =null;
            for(BigPack entity:packageInfoList) {
                bigPackItems = new ArrayList<>();
                expandEntity = new ExpandableBigPack();
                expandEntity.setExpanded(false);
                expandEntity.setSn(entity.getCustomerSn());
                expandEntity.setCustomerSn(entity.getCustomerSn());
                expandEntity.setCustomerName(entity.getCustomerName());
                expandEntity.setWorkOrderSn(entity.getWordOrderSn());
                expandEntity.setCustomerOrderSn(entity.getCustomerOrderSn());
                expandEntity.setTag(entity.getTag());
                List<com.step.pda.ec.database.BigPackItem> subItems = bigPackService.findItemByPid(entity.getId());
                for (com.step.pda.ec.database.BigPackItem item : subItems) {
                    bigPackItems.add(new BigPackItem(item.getId(), item.getProductSn(), item.getTag()));
                }
                expandEntity.setSubItems(bigPackItems);


                list.add(expandEntity);
            }
            //设置Adapter
            mAdapter.addData(CONVERTER.setItems(list).convert());
            BEAN.setCurrentCount(mAdapter.getData().size());
            mAdapter.loadMoreComplete();
            BEAN.addIndex();
        }
    }
}
