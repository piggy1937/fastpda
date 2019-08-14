package com.step.pda.ec.contract;

import android.content.Context;

import com.step.pda.presenter.BasePresenter;
import com.step.pda.view.IView;

/**
 * Created by user on 2019-08-08.
 * 关联mvp v+p 登录
 */

public interface ISignContract {
     interface View extends IView<Presenter>{
        /***
         * 登录成功回调
         */
        void onSignInSuccess();

         /***
          * 登录失败
          * @param message 消息
          */
         void onSignInError(String message);
         /***
          * 注册成功回调
          */
         void onSignUpSuccess();

         /***
          * 退出登录
          */
         void onSignOutSuccess();
     }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view, Context context) {
            super(view, context);
        }

        /***
         * 用户名密码登录
         * @param username 用户名
         * @param password 密码
         */
         public abstract  void requestSignIn(String username, String password);

        /***
         * 退出当前系统
         */
        public abstract  void requestSignOut();
    }

}
