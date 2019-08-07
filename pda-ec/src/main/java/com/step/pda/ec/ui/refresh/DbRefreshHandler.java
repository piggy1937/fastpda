package com.step.pda.ec.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.step.pda.app.Pda;
import com.step.pda.app.ui.recycler.MultipleRecyclerAdapter;
import com.step.pda.app.ui.refresh.PagingBean;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.database.PackageInfoDao;
import com.step.pda.ec.main.index.IndexDataConverter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2019-08-06.
 * <p>
 * 数据库请求刷新，加载更多
 */

public class DbRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private static MultipleRecyclerAdapter mAdapter = null;
    private final IndexDataConverter CONVERTER;
    private DbRefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                             RecyclerView recyclerView,
                             IndexDataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static DbRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, IndexDataConverter converter) {
        return new DbRefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
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
     * @param tableName
     */
    public void firstPage(String tableName) {


        Observable.create(new ObservableOnSubscribe<List<PackageInfo>>() {

            @Override
            public void subscribe(ObservableEmitter<List<PackageInfo>> emitter) throws Exception {
                DatabaseManager.getInstance().init(Pda.getApplicationContext());
                PackageInfoDao dao =  DatabaseManager.getInstance().getmPackageInfoDao();
                long total = dao.queryBuilder().count();
                BEAN.setDelayed(1000);
                BEAN.setTotal((int)total).setPageSize(20);
                List<PackageInfo> packageInfoList= dao.queryBuilder().offset(0).limit(BEAN.getPageSize()).orderAsc(PackageInfoDao.Properties.Id).list();
                emitter.onNext(packageInfoList);
                emitter.onComplete();
            }
        }).unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer< List<PackageInfo> >() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(List<PackageInfo> packageInfos) {
                //设置Adapter
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setPackageInfoList(packageInfos));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
                mAdapter.setOnLoadMoreListener(DbRefreshHandler.this, RECYCLERVIEW);
                BEAN.addIndex();
            }
        });

    }

    private void paging(final String url) {
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
            List<PackageInfo> packageInfoList = dao.queryBuilder().offset(index * pageSize).limit(BEAN.getPageSize()).orderAsc(PackageInfoDao.Properties.Id).list();
            //设置Adapter
            mAdapter.addData(CONVERTER.setPackageInfoList(packageInfoList).convert());
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

        paging("package_info");
    }
}
