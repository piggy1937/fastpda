package com.step.pda.app;

import com.step.pda.app.util.storage.PreferenceUtils;

/**
 * Created by user on 2019-08-02.
 * 管理用户信息
 */

public class AccountManager {
    private enum  SignTag{
        SIGN_TAG
    }

    /***
     * 保存用户登录状态，登录后调用
     * @param state
     */
    public static void setSignState(boolean state){
        PreferenceUtils.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    /***
     * 判断用户是否已登录
     * @return
     */
    public static boolean isSignIn(){
        return PreferenceUtils.getAppFlag(SignTag.SIGN_TAG.name());
    }

   public static void checkAccount(IUserChecker checker){
        if(isSignIn()){
            checker.onSignIn();
        }else{
            checker.onNotSignIn();
        }
   }

}
