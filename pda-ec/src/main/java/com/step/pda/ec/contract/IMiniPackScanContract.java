package com.step.pda.ec.contract;

import android.content.Context;

import com.step.pda.ec.database.PackageInfo;
import com.step.pda.presenter.BasePresenter;
import com.step.pda.view.IView;

/**
 * @author zhushubin
 * @date 2019-10-16.
 * GitHub：
 * email： 604580436@qq.com
 * description：
 */
public interface IMiniPackScanContract {
    interface View extends IView<Presenter> {
        void onSuccess(PackageInfo packageInfo);
        void onError(PackageInfo packageInfo, String errmsg);//失败处理
    }
    abstract class Presenter extends BasePresenter<IMiniPackScanContract.View> {
        public Presenter(View view, Context context) {
            super(view, context);
        }

        /***
         * 新增小包标签打印任务
         * @param packageInfo
         */
        public abstract void addMiniPackPrintTask(PackageInfo packageInfo);

        /***
         * 判断编号是否存在
         * @param sn 编号
         */
        public abstract void existSn(String sn);
    }
}
