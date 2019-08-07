package com.step.pda.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.joanzapata.iconify.widget.IconTextView;
import com.step.pda.app.Pda;
import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.app.ui.dialog.SelectDialog;
import com.step.pda.app.ui.slider.SlideRecyclerView;
import com.step.pda.app.util.DimenUtil;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.main.EcBottomDelegate;
import com.step.pda.ec.main.pack.PackingDelegate;
import com.step.pda.ec.ui.refresh.DbRefreshHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-06.
 */

public class IndexDelegate extends BottomItemDelegate {
    private static  final int ReqCode = 100;
    private static final int LOADER_SIZE_SCALE = 2;
    @BindView(R2.id.rv_index)
    SlideRecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;

    @BindView(R2.id.icon_index_ellipsis)
    IconTextView mIconEllipsis = null;
    @BindView(R2.id.fab_index_add)
    FloatingActionButton mfabIndexAdd;
    @OnClick(R2.id.fab_index_add)
    public void onFabIndexAdd(){
        //打开包装界面
        startForResult(new PackingDelegate(),ReqCode);
    }




    @OnClick(R2.id.icon_index_ellipsis)
    public void onIconEllipsisClick(){
        SelectDialog dialog= new SelectDialog(getContext(), com.step.pda.R.style.select_dialog);
        final Window dialogWindow = dialog.getWindow();
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        if(dialogWindow!=null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            int[] location = new int[2];
            mIconEllipsis.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            lp.x = x;
            lp.y = y;
            lp.width  =deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.gravity= Gravity.TOP;
            dialogWindow.setAttributes(lp);
        }
        dialog.show();
    }


    private DbRefreshHandler mRefreshHandler = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        mRefreshHandler = DbRefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("package_info");
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

}
