package com.step.pda.ec.ui.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.step.pda.app.Pda;
import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.MultipleFields;
import com.step.pda.app.ui.recycler.MultipleItemEntity;
import com.step.pda.app.ui.recycler.MultipleRecyclerAdapter;
import com.step.pda.app.ui.refresh.PagingBean;
import com.step.pda.ec.R;
import com.step.pda.ec.contract.IMiniPackContract;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.database.PackageInfoDao;
import com.step.pda.ec.main.index.IndexDataConverter;
import com.step.pda.ec.services.PackageInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019-08-06.
 * 小包标签
 * 数据库请求刷新，加载更多
 */

public class MiniPackRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private  MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final IMiniPackContract.Presenter  PRESENTER;//小包标签presenter
    private final Context CONTEXT;
    private MiniPackRefreshHandler(
                           SwipeRefreshLayout swipeRefreshLayout,
                             RecyclerView recyclerView,
                             IMiniPackContract.Presenter presenter,
                             DataConverter converter, PagingBean bean,Context context) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        this.CONTEXT = context;
        this.PRESENTER = presenter;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static MiniPackRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                RecyclerView recyclerView, IMiniPackContract.Presenter presenter, DataConverter converter, Context context) {
        return new MiniPackRefreshHandler(swipeRefreshLayout, recyclerView,presenter, converter, new PagingBean(),context);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Pda.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    /***
     * 从数据库查询记录
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @param total 总记录数
     * @param packageInfos list
     */
    public void firstPage(int pageNo, int pageSize, int total, List<PackageInfo> packageInfos) {
        BEAN.setDelayed(1000);
        BEAN.setTotal(total).setPageSize(pageSize);
        mAdapter = MultipleRecyclerAdapter.create( CONVERTER.setItems(packageInfos));
        RECYCLERVIEW.setAdapter(mAdapter);
        mAdapter.onSlideListener(new MultipleRecyclerAdapter.onSlideListener(){
            @Override
            public void onDel(final int position) {

                MultipleItemEntity  entity = mAdapter.getData().get(position);
                String title =  entity.getField(MultipleFields.TEXT);
                final long id =  entity.getField(MultipleFields.ID);
                new MaterialDialog.Builder(CONTEXT)
                        .title("删除")
                        .content("确定要删除"+title+"吗?")
                        .positiveText("确认")
                        .negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PackageInfoService service = new PackageInfoService();
                        service.delete(id);
                        long count = service.count();
                        mAdapter.getData().remove(position);
                        mAdapter.refresh();
                        refresh();
                    }
                }).show();
            }
            @Override
            public void onModify(final int position) {
                final MultipleItemEntity  entity = mAdapter.getData().get(position);
                String title =  entity.getField(MultipleFields.TEXT);
                final long id =  entity.getField(MultipleFields.ID);
                new MaterialDialog.Builder(CONTEXT)
                        .title(title)
                        //限制输入的长度
                        .inputRangeRes(1, 20, R.color.colorAccent)
                        //限制输入类型
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("数量", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                PackageInfoService service = new PackageInfoService();
                                entity.setField(MultipleFields.QUANTITY,input.toString());
                                service.update(id,Integer.parseInt(input.toString()));
                                mAdapter.getData().set(position,entity);
                                mAdapter.refresh();
                                refresh();
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();


            }
        });
        mAdapter.setOnLoadMoreListener(MiniPackRefreshHandler.this, RECYCLERVIEW);
        BEAN.setCurrentCount(mAdapter.getData().size());
        BEAN.addIndex();
    }

    public void paging() {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();
        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            CONVERTER.clearData();
            DatabaseManager.getInstance().init(Pda.getApplicationContext());
            PackageInfoDao dao = DatabaseManager.getInstance().getmPackageInfoDao();
            List<PackageInfo> packageInfoList =null; //dao.queryBuilder().offset(index * pageSize).limit(BEAN.getPageSize()).orderAsc(PackageInfoDao.Properties.Id).list();
            //设置Adapter
            if(CONVERTER instanceof IndexDataConverter) {
                mAdapter.addData(CONVERTER.setItems(packageInfoList).convert());
            }
            BEAN.setCurrentCount(mAdapter.getData().size());
            mAdapter.loadMoreComplete();
            BEAN.addIndex();
        }


    }

    /***
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    /***
     * 下拉加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        PRESENTER.page(BEAN.getPageIndex());
    }

    /***
     * 根据数据库新增数据
     * @param packageInfo
     */
    public void addData(PackageInfo packageInfo) {
        List<PackageInfo> list= new ArrayList<PackageInfo>();
        list.add(packageInfo);
        CONVERTER.clearData();
        MultipleItemEntity itemEntity= (MultipleItemEntity) CONVERTER.setItems(list).convert().get(0);
        mAdapter.getData().add(itemEntity);

        mAdapter.refresh();
    }
}
