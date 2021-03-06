package com.step.pda.ec.main.index;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.joanzapata.iconify.widget.IconTextView;
import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.app.ui.recycler.BaseDecoration;
import com.step.pda.app.ui.slider.SlideRecyclerView;
import com.step.pda.app.util.DimenUtil;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.contract.IMiniPackContract;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.main.EcBottomDelegate;
import com.step.pda.ec.presenter.MiniPackPresenter;
import com.step.pda.ec.ui.refresh.MiniPackRefreshHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-06.
 */

public class IndexDelegate extends BottomItemDelegate implements IMiniPackContract.View {
    private static  final int ReqCode = 100;
    private static final int LOADER_SIZE_SCALE = 2;
    private PopupWindow mPopupWindow;

    //小包标签
    private IMiniPackContract.Presenter mPresenter;
    @BindView(R2.id.rv_index)
    SlideRecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.lay_fragment_empty)
    LinearLayoutCompat layFragmentEmpty;




    @BindView(R2.id.icon_index_ellipsis)
    IconTextView mIconEllipsis = null;
    @BindView(R2.id.fab_index_add)
    FloatingActionButton mfabIndexAdd;
    @OnClick(R2.id.fab_index_add)
    public void onFabIndexAdd(){
        //打开包装界面
        getSupportDelegate().startForResult( MiniPackingDelegateScan.newInstance(),ReqCode);
    }




    @OnClick(R2.id.icon_index_ellipsis)
    public void onIconEllipsisClick(){
        mPopupWindow.showAsDropDown(mIconEllipsis, 0,
                0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPopupWindow();

    }

    private MiniPackRefreshHandler mRefreshHandler = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        initRecyclerView();
        mPresenter = new MiniPackPresenter(this,getContext());
        mRefreshHandler = MiniPackRefreshHandler.create(mRefreshLayout, mRecyclerView,mPresenter, new IndexDataConverter(),getContext());
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        mPresenter.firstPage();
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
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(Pda.getApplicationContext(), DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(Pda.getApplicationContext(), R.drawable.divider_inset));
//        mRecyclerView.addItemDecoration(itemDecoration);


                mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
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
            Fragment IndexDelegate  = getChildFragmentManager().findFragmentByTag(IndexDelegate.class.getName());
            getParentDelegate().showHideFragment(this);
            PackageInfo packageInfo = (PackageInfo) data.getSerializable("package_info");
            mRefreshHandler.addData(packageInfo);
            mRefreshHandler.onRefresh();
        }
        getParentDelegate().showHideFragment(this);
    }
    private void initPopupWindow() {
        View v = getActivity().getLayoutInflater().inflate(
                R.layout.popopwindow, null);
        int deviceWidth = DimenUtil.getScreenWidth();
        mPopupWindow = new PopupWindow(v, deviceWidth/2, WindowManager.LayoutParams.WRAP_CONTENT, true);


        mPopupWindow.setFocusable(true);
        //该属性设置为true则你在点击屏幕的空白位置也会退出
        mPopupWindow.setTouchable(true);

        mPopupWindow.setOutsideTouchable(true);
        Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.barcode_example_icon);
        Drawable drawable = new BitmapDrawable(getContext().getResources(), bmp);
        mPopupWindow.setBackgroundDrawable(drawable);
        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
    }
    //加载首页成功
    @Override
    public void onFirstPageSuccess(int pageNo, int pageSize, int total, List<PackageInfo> packageInfoList) {
        if(total==0){
            mRecyclerView.setVisibility(View.GONE);
            layFragmentEmpty.setVisibility(View.VISIBLE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
            layFragmentEmpty.setVisibility(View.GONE);
            mRefreshHandler.firstPage(pageNo, pageSize, total, packageInfoList);
        }
    }

    /***
     * 加载数据失败
     */
    @Override
    public void onPageError() {
        if(mRecyclerView!=null) {
            mRecyclerView.setVisibility(View.GONE);
        }
        if(layFragmentEmpty!=null) {
            layFragmentEmpty.setVisibility(View.VISIBLE);
        }
    }

    //分页成功
    @Override
    public void onPageSuccess() {
        mRefreshHandler.paging();
    }

    @Override
    public void onAddBarCodeSuccess() {

    }
}
