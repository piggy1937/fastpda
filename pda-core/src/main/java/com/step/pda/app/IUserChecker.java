package com.step.pda.app;

/**
 * Created by user on 2019-08-02.
 * 用户登录状态回调
 * 登录成功回调
 */

public interface IUserChecker {
    /***
     * 登录
     */
    void onSignIn();

    /***
     * 未登录
     */
    void onNotSignIn();
}
