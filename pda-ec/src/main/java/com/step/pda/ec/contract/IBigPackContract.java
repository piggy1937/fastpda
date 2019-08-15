package com.step.pda.ec.contract;

import android.content.Context;

import com.step.pda.presenter.BasePresenter;
import com.step.pda.view.IView;

/**
 * @author zhushubin
 * @date 2019-08-15.
 * GitHub：
 * email： 604580436@qq.com
 * description： 大包标签
 */
public interface IBigPackContract {
    interface View extends IView<Presenter>{
        /***
         * 刷新成功
         */
        void  onRefreshSuccess();
    }
    abstract class Presenter extends BasePresenter<IBigPackContract.View>{
        public Presenter(View view, Context context) {
            super(view, context);
        }
        /***
         * 刷新从服务端获取数据到本地
         */
        public abstract  void refresh();
    }

}
