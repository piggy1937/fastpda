package com.step.pda.ec.login;

/**
 * Created by user on 2019-08-02.
 */

public interface ISiginListener {
    /***
     * 登录成功回调
     */
    void onSignInSuccess();
    /***
     * 注册成功回调
     */
    void onSignUpSuccess();
}
