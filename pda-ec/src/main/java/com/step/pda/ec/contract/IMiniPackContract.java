package com.step.pda.ec.contract;

import android.content.Context;

import com.step.pda.ec.database.PackageInfo;
import com.step.pda.presenter.BasePresenter;
import com.step.pda.view.IView;

import java.util.List;

/**
 * @author zhushubin
 * @date 2019-08-15.
 * GitHub：
 * email： 604580436@qq.com
 * description： 大包标签
 */
public interface IMiniPackContract {
    interface View extends IView<Presenter>{
        /***
         * 刷新成功
         * @param pageNo 页码
         * @param pageSize 每页记录数
         * @param total 总记录数
         * @param packageInfoList list
         */
        void  onFirstPageSuccess(int pageNo, int pageSize, int total, List<PackageInfo> packageInfoList);
        void  onPageSuccess();
    }
    abstract class Presenter extends BasePresenter<IMiniPackContract.View>{
        public Presenter(View view, Context context) {
            super(view, context);
        }
        /***
         * 首页加载
         */
        public abstract  void firstPage();

        /***
         * 分页加载
         * @param pageNo 请求页码
         */
        public abstract  void page(int pageNo);
    }

}
