package com.step.pda.ec.login;

import android.util.Log;

import com.step.pda.app.AccountManager;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.UserProfile;

/**
 * Created by user on 2019-08-02.
 */

public class SignHandler {
    public static void onSignIn(String response,ISiginListener siginListener){
        final UserProfile userProfile = new UserProfile();
        userProfile.setName("admin");
        userProfile.setUserId(1L);

        try {
            DatabaseManager.getInstance().getDao().insert(userProfile);
        }catch (Exception e){
            Log.e("login",e.getLocalizedMessage());
        }
        ////保存用户状态
        AccountManager.setSignState(true);
        siginListener.onSignInSuccess();
    }
    public static void onSignUp(String response,ISiginListener siginListener){
        final UserProfile userProfile = new UserProfile();
        userProfile.setName("admin");
        userProfile.setUserId(1L);

        try {
            DatabaseManager.getInstance().getDao().insert(userProfile);
        }catch (Exception e){
            Log.e("login",e.getLocalizedMessage());
        }
        ////保存用户状态
        AccountManager.setSignState(true);
        siginListener.onSignUpSuccess();
    }
}
