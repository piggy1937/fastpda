package com.step.pda.app;

import android.content.Context;

import com.step.pda.app.util.storage.PreferenceUtils;

/**
 * Created by user on 2019-08-02.
 * 管理用户信息
 */

public class AccountManager {


    private enum  SignTag{
        SIGN_TAG,
        API_HOST, //url地址
        LOGIN_USER
    }

    /***
     * 保存用户登录状态，登录后调用
     * @param state
     */
    public static void setSignState(boolean state){
        PreferenceUtils.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }
    /***
     * 保存服务器地址
     * @param apiHost
     */
    public static void setApiHost(String apiHost){
        Context context=   Pda.getApplicationContext();
        PreferenceUtils.putString(context,SignTag.API_HOST.name(),apiHost);
    }
    /***
     * 获取服务器地址
     */
    public static String getApiHost(){
        Context context=   Pda.getApplicationContext();
       return PreferenceUtils.getString(context,SignTag.API_HOST.name(),"");
    }
    /***
     * 设置登录用户信息
     * @param tUsername
     */
    public static void setCreater(String tUsername) {
        Context context=   Pda.getApplicationContext();
        PreferenceUtils.putString(context,SignTag.LOGIN_USER.name(),tUsername);
    }

    /***
     * 获取登录信息
     */
    public static String getCreater() {
        Context context=   Pda.getApplicationContext();
        return PreferenceUtils.getString(context,SignTag.LOGIN_USER.name(),"demo");
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
