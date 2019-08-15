package com.step.pda.ec.main.bigpack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.step.pda.app.Pda;
import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.app.ui.slider.SlideRecyclerView;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.contract.IBigPackContract;
import com.step.pda.ec.main.EcBottomDelegate;
import com.step.pda.ec.main.index.IndexItemClickListener;
import com.step.pda.ec.presenter.BigPackPresenter;
import com.step.pda.ec.ui.refresh.BigPackRefreshHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-06.
 */

public class BigPackingDelegate extends BottomItemDelegate implements IBigPackContract.View {
    private static  final int ReqCode = 100;
    private static final int LOADER_SIZE_SCALE = 2;
    private volatile boolean  isAlreadyLoadData = false;
    private IBigPackContract.Presenter mPresenter;
    @BindView(R2.id.rv_index)
    SlideRecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;

    @BindView(R2.id.icon_big_pack_refresh)
    IconTextView mIconRefresh = null;
    @BindView(R2.id.fab_index_add)
    FloatingActionButton mfabIndexAdd;
    @OnClick(R2.id.fab_index_add)
    public void onFabIndexAdd(){
        //打开包装界面
        getSupportDelegate().startForResult(new BigPackingDelegateScan(),ReqCode);
    }




    @OnClick(R2.id.icon_big_pack_refresh)
    public void onIconRefreshClick(){
        mPresenter.refresh();
    }


    private BigPackRefreshHandler mRefreshHandler = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_packing_big;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        mRefreshHandler =BigPackRefreshHandler.create(mRefreshLayout, mRecyclerView, new BigPackingDataConverter(),getContext());
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BigPackPresenter(this,getContext());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        if(!isAlreadyLoadData) {
            mRefreshHandler.firstPage();
            isAlreadyLoadData = true;
        }


    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }
    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Pda.getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(Pda.getApplicationContext(), R.drawable.divider_inset));
        mRecyclerView.addItemDecoration(itemDecoration);


//                mRecyclerView.addItemDecoration
//                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    /***
     * Fragment 回调
     * @param requestCode 请求码
     * @param resultCode 响应码
     * @param data 数据
     */
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==101){
            mRefreshHandler.onRefresh();
            this.onResume();
        }
    }

    /***
     * 刷新成功回调函数
     */
    @Override
    public void onRefreshSuccess() {
        mRefreshHandler.onRefresh();
    }
}
